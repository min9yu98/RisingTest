package com.example.demo.src.post;

import com.example.demo.src.post.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetPostsRes> getPosts(long userIdx) {
        String getPostsQuery = "select P.postIdx, (select PI.postImg_url LIMIT 1) as postImg_url, P.price, P.postTitle, P.tradeRegion, " +
                "(select " +
                "(case when timestampdiff(second, P.createAt, current_timestamp) < 60 " +
                "then concat(timestampdiff(second, P.createAt, current_timestamp), '초 전') " +
                "when timestampdiff(minute, P.createAt, current_timestamp) < 60 " +
                "then concat(timestampdiff(minute, P.createAt, current_timestamp), '분 전') " +
                "when timestampdiff(hour, P.createAt, current_timestamp) < 24 " +
                "then concat(timestampdiff(hour, P.createAt, current_timestamp), '시간 전') " +
                "else concat(datediff(current_timestamp, P.createAt), '일 전') " +
                "end)) as postingTime, P.payStatus, COUNT(DISTINCT Z.zzimIdx) as likeNum " +
                "from Post P " +
                "LEFT OUTER JOIN PostImg PI on P.postIdx = PI.postIdx " +
                "LEFT OUTER JOIN Zzim Z on P.postIdx = Z.postIdx " +
                "where P.status = 'A' " +
                "group by PI.postIdx, P.postIdx";

        return this.jdbcTemplate.query(getPostsQuery,
                (rs, rowNum) -> new GetPostsRes(
                        rs.getLong("postIdx"),
                        rs.getString("postImg_url"),
                        rs.getInt("price"),
                        rs.getString("postTitle"),
                        rs.getString("tradeRegion"),
                        rs.getString("postingTime"),
                        rs.getString("payStatus"),
                        rs.getLong("likeNum")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }


    public long registerPost(PostPostReq postPostReq, long userIdx) {
        String registerPostQuery = "insert into " +
                "Post(userIdx, tradeRegion, postTitle, postContent, categoryIdx, price, deliveryFee, quantity, prodStatus, exchange, payStatus) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?);"; // 실행될 동적 쿼리문
        Object[] registerPostParams = new Object[]{
                userIdx,
                postPostReq.getTradeRegion(),
                postPostReq.getPostTitle(),
                postPostReq.getPostContent(),
                postPostReq.getCategoryIdx(),
                postPostReq.getPrice(),
                postPostReq.getDeliveryFee(),
                postPostReq.getQuantity(),
                postPostReq.getProdStatus(),
                postPostReq.getExchange(),
                postPostReq.getPayStatus()
        };
        this.jdbcTemplate.update(registerPostQuery, registerPostParams);

        String lastInsertIdQuery = "select last_insert_id();";

        long postIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);

        // 게시글 사진 추가
        List<String> postImg = postPostReq.getPostImg_url();
        for (String str : postImg){
            String registerPostImgQuery = "insert into PostImg(postIdx, postImg_url) values(?, ?);";
            Object[] registerPostImgParams = new Object[]{
                    postIdx,
                    str
            };
            this.jdbcTemplate.update(registerPostImgQuery, registerPostImgParams);
        }

        // 해시태그 추가
        List<String> hashTag = postPostReq.getHashTagName();
        for (String str : hashTag){
            String registerPostTagQuery = "insert into HashTag(postIdx, hashTagName) values(?, ?);";
            Object[] registerPostTagParams = new Object[]{
                    postIdx,
                    str
            };
            this.jdbcTemplate.update(registerPostTagQuery, registerPostTagParams);
        }
        return postIdx;
    }

    public List<GetPostSearchRes> getQueryPosts(String query) {
        String getQueryPostsQuery = "select P.postIdx, (select PI.postImg_url LIMIT 1) as postImg_url, P.price, P.postTitle, " +
                "P.payStatus " +
                "from Post P " +
                "LEFT OUTER JOIN PostImg PI on P.postIdx = PI.postIdx " +
                "LEFT OUTER JOIN Zzim Z on P.postIdx = Z.postIdx " +
                "LEFT OUTER JOIN HashTag HT on P.postIdx = HT.postIdx " +
                "where ((LOCATE(" + query + ", P.postTitle) > 0) or (LOCATE(" + query + ", P.postContent) > 0) or " +
                "(LOCATE(" + query + ", HT.hashTagName) > 0) " +
                "and P.status = 'A') " +
                "group by PI.postIdx, P.postIdx";
        return this.jdbcTemplate.query(getQueryPostsQuery,
                (rs, rowNum) -> new GetPostSearchRes(
                        rs.getLong("postIdx"),
                        rs.getString("postImg_url"),
                        rs.getInt("price"),
                        rs.getString("postTitle"),
                        rs.getBoolean("payStatus")
                        ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }

    // 상점 검색어 목록 (검색어를 검색했을 때 나오는 상품들 X, 상점 부분에서 상점이름들의 목록)
    public List<GetPostStoreSearchQueryRes> getQueryStoreList(String query) {
        String getQueryStoreQuery = "select U.profileImg_url, U.storeName, COUNT(DISTINCT F.followeeUserIdx) as followerNum, " +
                "(select (case when P.status='A' THEN COUNT(DISTINCT P.postIdx) " +
                "when P.status='D' THEN COUNT(DISTINCT P.postIdx) - 1 " +
                "else (select 0) end)) as postNum " +
                "from User U " +
                "LEFT OUTER JOIN Post P on U.userIdx = P.userIdx " +
                "LEFT OUTER JOIN Follow F on U.userIdx = F.followerUserIdx " +
                "where (LOCATE(" + query + ", U.storeName) > 0) and U.status = 'A' " +
                "group by P.userIdx, F.followIdx";
        return this.jdbcTemplate.query(getQueryStoreQuery,
                (rs, rowNum) -> new GetPostStoreSearchQueryRes(
                        rs.getString("profileImg_url"),
                        rs.getString("storeName"),
                        rs.getLong("followerNum"),
                        rs.getLong("postNum")
                ));
    }

    // 검색어 검색시 출력되는 단어들 리스트
    public List<GetPostSearchQueryRes> getQueryPostList(String query) {
        String getQueryPostQuery =
                "select DISTINCT SUBSTRING_INDEX(P.postTitle, ' ', LOCATE("+ query +", P.postTitle)) as searchWord " +
                "from Post P " +
                "where P.status='A' and (LOCATE("+ query +", P.postTitle) > 0) UNION ALL " +
                "select DISTINCT SUBSTRING_INDEX(P.postTitle, ' ', LOCATE("+ query +", P.postTitle) + 1) as Num " +
                "from Post P " +
                "where P.status='A' and (LOCATE("+ query +", P.postTitle) > 0) UNION ALL " +
                "select DISTINCT SUBSTRING_INDEX(P.postTitle, ' ', LOCATE("+ query +", P.postTitle) + 2) as Num " +
                "from Post P " +
                "where P.status='A' and (LOCATE("+ query +", P.postTitle) > 0) UNION ALL " +
                "select DISTINCT SUBSTRING_INDEX(HT.hashTagName, ' ', LOCATE("+ query +", HT.hashTagName)) as searchWord " +
                "from HashTag HT " +
                "LEFT OUTER JOIN Post P on HT.postIdx = P.postIdx " +
                "where HT.status='A' and P.status = 'A' and (LOCATE("+ query +", HT.hashTagName) > 0)";
        return this.jdbcTemplate.query(getQueryPostQuery,
                (rs, rowNum) -> new GetPostSearchQueryRes(
                        rs.getString("searchWord")
                ));
    }

    public GetPostRes getPost(long userIdx) {
        String getPostQuery = "";
        return this.jdbcTemplate.queryForObject(getPostQuery,
                (rs, rowNum) -> new GetPostRes());
    }
}

