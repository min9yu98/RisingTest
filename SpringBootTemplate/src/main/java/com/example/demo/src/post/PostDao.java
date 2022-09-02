package com.example.demo.src.post;

import com.example.demo.src.post.model.get.*;
import com.example.demo.src.post.model.patch.PatchDeletePostReq;
import com.example.demo.src.post.model.patch.PatchEditPostReq;
import com.example.demo.src.post.model.patch.PatchEditPostRes;
import com.example.demo.src.post.model.post.PostPostReq;
import com.example.demo.src.post.model.post.PostReviewReq;
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


    // 게시글 전체 조회
    public List<GetPostsRes> getPosts(long userIdx) {
        String getPostsQuery = "select P.postIdx, (select PI.postImg_url LIMIT 1) as postImg_url, " +
                "(select exists(select zzimIdx from Zzim where userIdx=" + userIdx + " and postIdx = P.postIdx)) as zzimStatus," +
                "P.price, P.postTitle, P.tradeRegion, " +
                "(select " +
                "(case when timestampdiff(second, P.createAt, current_timestamp) < 60 " +
                "then concat(timestampdiff(second, P.createAt, current_timestamp), '초 전') " +
                "when timestampdiff(minute, P.createAt, current_timestamp) < 60 " +
                "then concat(timestampdiff(minute, P.createAt, current_timestamp), '분 전') " +
                "when timestampdiff(hour, P.createAt, current_timestamp) < 24 " +
                "then concat(timestampdiff(hour, P.createAt, current_timestamp), '시간 전') " +
                "else concat(datediff(current_timestamp, P.createAt), '일 전') " +
                "end)) as postingTime, IF(P.payStatus='N', false, true) as payStatus, COUNT(DISTINCT Z.zzimIdx) as likeNum, P.sellingStatus " +
                "from Post P " +
                "LEFT OUTER JOIN PostImg PI on P.postIdx = PI.postIdx " +
                "LEFT OUTER JOIN Zzim Z on P.postIdx = Z.postIdx " +
                "where P.status = 'A' and IF(P.sellingStatus = \"판매중\", true, false) " +
                "group by PI.postIdx, P.postIdx";

        return this.jdbcTemplate.query(getPostsQuery,
                (rs, rowNum) -> new GetPostsRes(
                        rs.getLong("postIdx"),
                        rs.getString("postImg_url"),
                        rs.getBoolean("zzimStatus"),
                        rs.getInt("price"),
                        rs.getString("postTitle"),
                        rs.getString("tradeRegion"),
                        rs.getString("postingTime"),
                        rs.getBoolean("payStatus"),
                        rs.getLong("likeNum"),
                        rs.getString("sellingStatus")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }

    // 리뷰 등록
    public long registerReview(PostReviewReq postReviewReq, long postIdx, long userIdx) {
        String registerReviewQuery = "insert into PostReview(postIdx, userIdx, starNum, review) values (?, ?, ?, ?)";
        Object[] registerReviewParams = new Object[]{
                postIdx,
                userIdx,
                postReviewReq.getStarNum(),
                postReviewReq.getReview()
        };
        this.jdbcTemplate.update(registerReviewQuery, registerReviewParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);
    }

    // 게시글 등록
    public long registerPost(PostPostReq postPostReq, long userIdx) {
        String registerPostQuery = "insert into " +
                "Post(userIdx, tradeRegion, postTitle, postContent, categoryIdx, price, deliveryFee, quantity, prodStatus, exchange, payStatus) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?);"; // 실행될 동적 쿼리문
        int categoryIdx = postPostReq.getCategoryIdx();
        categoryIdx += 12;
        Object[] registerPostParams = new Object[]{
                userIdx,
                postPostReq.getTradeRegion(),
                postPostReq.getPostTitle(),
                postPostReq.getPostContent(),
                categoryIdx,
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

    // 게시글 검색 결과
    public List<GetPostSearchRes> getQueryPosts(String query, long userIdx) {
        String getQueryPostsQuery = "select P.postIdx, (select PI.postImg_url LIMIT 1) as postImg_url, " +
                "(select exists(select zzimIdx from Zzim where userIdx=? and postIdx = P.postIdx)) as zzimStatus, " +
                "P.price, P.postTitle, " +
                "IF(P.payStatus='N', false, true) as payStatus, P.sellingStatus " +
                "from Post P " +
                "LEFT OUTER JOIN PostImg PI on P.postIdx = PI.postIdx " +
                "LEFT OUTER JOIN Zzim Z on P.postIdx = Z.postIdx " +
                "LEFT OUTER JOIN HashTag HT on P.postIdx = HT.postIdx " +
                "where ((LOCATE(" + query + ", P.postTitle) > 0) or (LOCATE(" + query + ", P.postContent) > 0) or " +
                "(LOCATE(" + query + ", HT.hashTagName) > 0) " +
                "and P.status = 'A' and IF(P.sellingStatus = \"판매중\", true, false)) " +
                "group by PI.postIdx, P.postIdx";
        return this.jdbcTemplate.query(getQueryPostsQuery,
                (rs, rowNum) -> new GetPostSearchRes(
                        rs.getLong("postIdx"),
                        rs.getString("postImg_url"),
                        rs.getBoolean("zzimStatus"),
                        rs.getInt("price"),
                        rs.getString("postTitle"),
                        rs.getBoolean("payStatus"),
                        rs.getString("sellingStatus")
                        ), userIdx // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }

    // 상점 검색시 조회되는 상점 정보
    public GetPostStoreRes getQueryStore(long userIdx) {
        String getQueryStoreQuery = "select U.userName, U.userRegion, U.description " +
                "from User U " +
                "where U.userIdx = ?";
        return this.jdbcTemplate.queryForObject(getQueryStoreQuery,
                (rs, rowNum) -> new GetPostStoreRes(
                        rs.getString("userName"),
                        rs.getString("userRegion"),
                        rs.getString("description")
                ), userIdx);
    }

    // 상점 검색시 조회되는 상점 게시글들
    public List<GetPostStorePostRes> getQueryStorePost(long userIdx, long storeUserIdx) {
        String getQueryStorePostQuery = "select P.postIdx, (select DISTINCT PI.postImg_url LIMIT 1) as postImg_url, " +
                "(select exists(select zzimIdx from Zzim where userIdx=" + userIdx + " and postIdx = P.postIdx)) as zzimStatus, " +
                "P.price, P.postTitle, IF(P.payStatus='N', false, true) as payStatus " +
                "from Post P " +
                "LEFT OUTER JOIN PostImg PI on P.postIdx = PI.postIdx " +
                "LEFT OUTER JOIN User U on P.userIdx = U.userIdx " +
                "where U.status='A' and P.status = 'A' and U.userIdx = ? and IF(P.sellingStatus = \"판매중\", true, false) " +
                "group by P.postIdx";
        return this.jdbcTemplate.query(getQueryStorePostQuery,
                (rs, rowNum) -> new GetPostStorePostRes(
                        rs.getLong("postIdx"),
                        rs.getString("postImg_url"),
                        rs.getBoolean("zzimStatus"),
                        rs.getInt("price"),
                        rs.getString("postTitle"),
                        rs.getBoolean("payStatus")
                ), storeUserIdx);
    }

    // 상점 검색어 목록 (검색어를 검색했을 때 나오는 상품들 X, 상점 부분에서 상점이름들의 목록)
    public List<GetPostStoreSearchQueryRes> getQueryStoreList(String query) {
        String getQueryStoreQuery = "select U.userIdx, U.profileImg_url, U.userName, COUNT(DISTINCT F.followeeUserIdx) as followerNum, " +
                "(select (case when P.status='A' THEN COUNT(DISTINCT P.postIdx) " +
                "when P.status='D' THEN COUNT(DISTINCT P.postIdx) - 1 " +
                "else (select 0) end)) as postNum " +
                "from User U " +
                "LEFT OUTER JOIN Post P on U.userIdx = P.userIdx " +
                "LEFT OUTER JOIN Follow F on U.userIdx = F.followerUserIdx " +
                "where (LOCATE(" + query + ", U.userName) > 0) and U.status = 'A' " +
                "group by P.userIdx, F.followIdx LIMIT 20";
        return this.jdbcTemplate.query(getQueryStoreQuery,
                (rs, rowNum) -> new GetPostStoreSearchQueryRes(
                        rs.getLong("userIdx"),
                        rs.getString("profileImg_url"),
                        rs.getString("userName"),
                        rs.getLong("followerNum"),
                        rs.getLong("postNum")
                ));
    }

    // 검색어 검색시 출력되는 단어들 리스트
    public List<GetPostSearchQueryRes> getQueryPostList(String query) {
        String getQueryPostQuery =
                "select DISTINCT SUBSTRING_INDEX(P.postTitle, ' ', LOCATE("+ query +", P.postTitle)) as searchWord " +
                "from Post P " +
                "where P.status='A' and (LOCATE("+ query +", P.postTitle) > 0) UNION  " +
                "select DISTINCT SUBSTRING_INDEX(P.postTitle, ' ', LOCATE("+ query +", P.postTitle) + 1) as Num " +
                "from Post P " +
                "where P.status='A' and (LOCATE("+ query +", P.postTitle) > 0) UNION  " +
                "select DISTINCT SUBSTRING_INDEX(P.postTitle, ' ', LOCATE("+ query +", P.postTitle) + 2) as Num " +
                "from Post P " +
                "where P.status='A' and (LOCATE("+ query +", P.postTitle) > 0) UNION  " +
                "select DISTINCT SUBSTRING_INDEX(HT.hashTagName, ' ', LOCATE("+ query +", HT.hashTagName)) as searchWord " +
                "from HashTag HT " +
                "LEFT OUTER JOIN Post P on HT.postIdx = P.postIdx " +
                "where HT.status='A' and P.status = 'A' and (LOCATE("+ query +", HT.hashTagName) > 0) LIMIT 20";
        return this.jdbcTemplate.query(getQueryPostQuery,
                (rs, rowNum) -> new GetPostSearchQueryRes(
                        rs.getString("searchWord")
                ));
    }

    // 게시글 상세정보 조회
    public GetPostRes getPost(long userIdx, long postIdx) {
        String registerPostImgQuery = "update Post " +
                "set view = Post.view + 1 " +
                "where postIdx = ?";
        this.jdbcTemplate.update(registerPostImgQuery, postIdx);

        String getPostQuery = "select P.postIdx, P.price, IF(P.payStatus='N', false, true) as payStatus, P.postTitle, P.tradeRegion, " +
                "(select (case when timestampdiff(second, P.createAt, current_timestamp) < 60 " +
                "then concat(timestampdiff(second, P.createAt, current_timestamp), '초 전') " +
                "when timestampdiff(minute, P.createAt, current_timestamp) < 60 " +
                "then concat(timestampdiff(minute, P.createAt, current_timestamp), '분 전') " +
                "when timestampdiff(hour, P.createAt, current_timestamp) < 24 " +
                "then concat(timestampdiff(hour, P.createAt, current_timestamp), '시간 전') " +
                "else concat(datediff(current_timestamp, P.createAt), '일 전') " +
                "end)) as postingTime, P.view as viewNum, COUNT(DISTINCT Z.zzimIdx) as likeNum, " +
                "COUNT(DISTINCT TR.talkRoomIdx) as chatNum, P.prodStatus, P.quantity, P.deliveryFee, P.exchange, P.postContent, P.sellingStatus, " +
                "(select exists(select zzimIdx from Zzim where userIdx=" + userIdx + " and postIdx = P.postIdx)) as zzimStatus " +
                "from Post P " +
                "LEFT OUTER JOIN Zzim Z on P.postIdx = Z.postIdx " +
                "LEFT OUTER JOIN TalkRoom TR on P.postIdx = TR.postIdx " +
                "where P.postIdx = ? and P.status = 'A' and IF(P.sellingStatus = \"판매중\", true, false)";
        return this.jdbcTemplate.queryForObject(getPostQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getLong("postIdx"),
                        rs.getInt("price"),
                        rs.getBoolean("payStatus"),
                        rs.getString("postTitle"),
                        rs.getString("tradeRegion"),
                        rs.getString("postingTime"),
                        rs.getLong("viewNum"),
                        rs.getLong("likeNum"),
                        rs.getLong("chatNum"),
                        rs.getString("prodStatus"),
                        rs.getLong("quantity"),
                        rs.getString("deliveryFee"),
                        rs.getString("exchange"),
                        rs.getString("postContent"),
                        rs.getString("sellingStatus"),
                        rs.getBoolean("zzimStatus")
                ), postIdx);
    }

    // 게시글의 사진들 불러오기
    public List<GetPostImgRes> getImg(long userIdx, long postIdx) {
        String getImgQuery = "select P.postIdx, PI.postImg_url " +
                "from Post P " +
                "LEFT OUTER JOIN PostImg PI on P.postIdx = PI.postIdx " +
                "where P.postIdx = ? and P.status = 'A'";
        return this.jdbcTemplate.query(getImgQuery,
                (rs, rowNum) -> new GetPostImgRes(
                        rs.getLong("postIdx"),
                        rs.getString("postImg_url")
                ), postIdx);
    }

    // 게시글의 해시태그 불러오기
    public List<GetPostTagRes> getTag(long userIdx, long postIdx) {
        String getTagQuery = "select P.postIdx, HT.hashTagName " +
                "from Post P " +
                "LEFT OUTER JOIN HashTag HT on P.postIdx = HT.postIdx " +
                "where P.postIdx = ? and P.status = 'A'";
        return this.jdbcTemplate.query(getTagQuery,
                (rs, rowNum) -> new GetPostTagRes(
                        rs.getLong("postIdx"),
                        rs.getString("hashTagName")
                ), postIdx);
    }

    // 카테고리에 맞는 게시물들 불러오기
    public List<GetCategoryPostRes> getCategoryPost(long userIdx, int idx) {
        String getCategoryPostQuery = "select P.postIdx, (select DISTINCT PI.postImg_url LIMIT 1) as postImg_url, " +
                "(select exists(select zzimIdx from Zzim where userIdx=? and postIdx = P.postIdx)) as zzimStatus, " +
                "P.price, P.postTitle, IF(P.payStatus = 'N', false, true) as payStatus " +
                "from Post P " +
                "LEFt OUTER JOIN PostImg PI on P.postIdx = PI.postIdx " +
                "where IF(P.sellingStatus = \"판매중\", true, false) and P.status='A' and P.categoryIdx = ? " +
                "group by P.postIdx, PI.postIdx";
        Object[] getCategoryPostParams = new Object[]{userIdx, idx};
        return this.jdbcTemplate.query(getCategoryPostQuery,
                (rs, rowNum) -> new GetCategoryPostRes(
                        rs.getLong("postIdx"),
                        rs.getString("postImg_url"),
                        rs.getBoolean("zzimStatus"),
                        rs.getInt("price"),
                        rs.getString("postTitle"),
                        rs.getBoolean("payStatus")
                ), getCategoryPostParams);
    }

    // 게시글 삭제
    public int deletePost(PatchDeletePostReq patchDeletePostReq) {
        String deletePostQuery = "update Post P " +
                "LEFT OUTER JOIN HashTag HT on P.postIdx = HT.postIdx " +
                "LEFT OUTER JOIN PostImg PI on P.postIdx = PI.postIdx " +
                "LEFT OUTER JOIN PostReview PR on P.postIdx = PR.postIdx " +
                "LEFT OUTER JOIN Zzim Z on P.postIdx = Z.postIdx " +
                "set P.status = 'D', HT.status = 'D', PI.status = 'D', PR.status = 'D', Z.status = 'D' " +
                "where P.postIdx = ? and P.userIdx = ?";
        Object[] deletePostParams = new Object[]{patchDeletePostReq.getPostIdx(), patchDeletePostReq.getUserIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(deletePostQuery, deletePostParams);
    }

    public int checkPostDelete(long postIdx){
        String check = "select IF(status='D', true, false) from Post where postIdx = ?";
        return this.jdbcTemplate.queryForObject(check, int.class, postIdx);
    }

    // 게시글 편집
    public long editPost(PatchEditPostReq patchEditPostReq, long userIdx, long postIdx) {
        // 게시글 사진 수정
        List<String> postImg = patchEditPostReq.getPostImg_url();
        if (postImg != null) {
            String initialization = "update PostImg PI " +
                    "LEFT OUTER JOIN Post P on PI.postIdx = P.postIdx " +
                    "set PI.status = 'D' " +
                    "where P.postIdx = ? and P.userIdx = ?";
            Object[] registerPostImgParams1 = new Object[]{
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(initialization, registerPostImgParams1);
            for (String str : postImg) {
                String registerImg = "insert into PostImg(postIdx, postImg_url) " +
                        "values((select postIdx from Post where userIdx = ? and postIdx = ?), ?)";
                Object[] registerPostImgParams2 = new Object[]{
                        userIdx,
                        postIdx,
                        str
                };
                this.jdbcTemplate.update(registerImg, registerPostImgParams2);
            }
        }

        // 게시글 거래 지역 수정
        if (patchEditPostReq.getTradeRegion() != null) {
            String registerRegion = "update Post P " +
                    "set P.tradeRegion = ? " +
                    "where P.postIdx = ? and P.status = 'A' and P.userIdx = ?";
            Object[] registerPostTRParams = new Object[]{
                    patchEditPostReq.getTradeRegion(),
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(registerRegion, registerPostTRParams);
        }

        // 게시글 제목 수정
        if (patchEditPostReq.getPostTitle() != null) {
            String registerTitle = "update Post P " +
                    "set P.postTitle= ? " +
                    "where P.postIdx = ? and P.status = 'A' and P.userIdx = ?";
            Object[] registerPostTitleParams = new Object[]{
                    patchEditPostReq.getPostTitle(),
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(registerTitle, registerPostTitleParams);
        }

        if (patchEditPostReq.getCategoryIdx() != 0){
            String registerCategory = "update Post P " +
                    "set P.categoryIdx = ? " +
                    "where P.postIdx = ? and P.status = 'A' and P.userIdx = ?";
            int categoryIdx = patchEditPostReq.getCategoryIdx();
            categoryIdx += 12;
            Object[] registerPostCategoryParams = new Object[]{
                    categoryIdx,
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(registerCategory, registerPostCategoryParams);
        }

        List<String> hashTag = patchEditPostReq.getHashTagName();
        if (hashTag != null) {
            String initialization2 = "update HashTag HT " +
                    "LEFT OUTER JOIN Post P on HT.postIdx = P.postIdx " +
                    "set HT.status = 'D' " +
                    "where P.postIdx = ?  and P.userIdx = ?";
            Object[] registerPostTagParams1 = new Object[]{
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(initialization2, registerPostTagParams1);

            for (String str : hashTag) {
                String registerTag = "insert into HashTag(postIdx, hashTagName) " +
                        "values((select postIdx from Post where userIdx = ? and postIdx = ?), ?)";
                Object[] registerPostTagParams2 = new Object[]{
                        userIdx,
                        postIdx,
                        str
                };
                this.jdbcTemplate.update(registerTag, registerPostTagParams2);
            }
        }

        if (patchEditPostReq.getPrice() != 0){
            String registerPrice = "update Post P set P.price= ? where P.postIdx = ? and P.status = 'A' and P.userIdx = ?";
            Object[] registerPostPriceParams = new Object[]{
                    patchEditPostReq.getPrice(),
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(registerPrice, registerPostPriceParams);
        }

        if (patchEditPostReq.getDeliveryFee() != null){
            String registerDeliveryFee = "update Post P set P.deliveryFee= ? where P.postIdx = ? and P.status = 'A' and P.userIdx = ?";
            Object[] registerPostDFParams = new Object[]{
                    patchEditPostReq.getDeliveryFee(),
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(registerDeliveryFee, registerPostDFParams);
        }

        if (patchEditPostReq.getQuantity() != 0){
            String registerQuantity = "update Post P set P.quantity= ? where P.postIdx = ? and P.status = 'A' and P.userIdx = ?";
            Object[] registerPostQuantityParams = new Object[]{
                    patchEditPostReq.getQuantity(),
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(registerQuantity, registerPostQuantityParams);
        }

        if (patchEditPostReq.getProdStatus() != null) {
            String registerProdStatus = "update Post P set P.prodStatus= ? where P.postIdx = ? and P.status = 'A' and P.userIdx = ?";
            Object[] registerPostPSParams = new Object[]{
                    patchEditPostReq.getProdStatus(),
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(registerProdStatus, registerPostPSParams);
        }

        if (patchEditPostReq.getExchange() != null) {
            String registerExchange = "update Post P set P.exchange= ? where P.postIdx = ? and P.status = 'A' and P.userIdx = ?";
            Object[] registerPostExchangeParams = new Object[]{
                    patchEditPostReq.getExchange(),
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(registerExchange, registerPostExchangeParams);
        }

        if (patchEditPostReq.getPostContent() != null) {
            String registerPostContent = "update Post P set P.postContent= ? where P.postIdx = ? and P.status = 'A' and P.userIdx = ?";
            Object[] registerPostContentParams = new Object[]{
                    patchEditPostReq.getPostContent(),
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(registerPostContent, registerPostContentParams);
        }

        if (patchEditPostReq.getPayStatus() != null){
            String registerPayStatus = "update Post P set P.payStatus= ? where P.postIdx = ? and P.status = 'A' and P.userIdx = ?";
            Object[] registerPostPayParams = new Object[]{
                    patchEditPostReq.getPayStatus(),
                    postIdx,
                    userIdx
            };
            this.jdbcTemplate.update(registerPayStatus, registerPostPayParams);
        }

        return postIdx;
    }
}

