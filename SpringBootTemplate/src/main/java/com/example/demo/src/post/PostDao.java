package com.example.demo.src.post;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.model.GetPostLikeRes;
import com.example.demo.src.post.model.GetPostsRes;
import com.example.demo.src.post.model.PostPostReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.parameters.P;
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
    public List<GetPostsRes> getPosts(int userIdx) {
        String getPostsQuery = "select (select PI.postImg_url LIMIT 1) as postImg_url, P.price, P.postTitle, P.tradeRegion, " +
                "(select " +
                "(case when timestampdiff(second, P.createAt, current_timestamp) < 60 " +
                "then concat(timestampdiff(second, P.createAt, current_timestamp), '초 전') " +
                "when timestampdiff(minute, P.createAt, current_timestamp) < 60\n" +
                "then concat(timestampdiff(minute, P.createAt, current_timestamp), '분 전') " +
                "when timestampdiff(hour, P.createAt, current_timestamp) < 24\n" +
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
                        rs.getString("postImg_url"),
                        rs.getInt("price"),
                        rs.getString("postTitle"),
                        rs.getString("tradeRegion"),
                        rs.getString("postingTime"),
                        rs.getString("payStatus"),
                        rs.getInt("likeNum")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }


    public int registerPost(PostPostReq postPostReq, int userIdx) {
        String registerPostQuery = "insert into " +
                "Post(userIdx, tradeRegion, postTitle, postContent, categoryIdx, price, deliveryFee, quantity, prodStatus," +
                "exchange, payStatus) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)"; // 실행될 동적 쿼리문
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

        String lastInsertIdQuery = "select last_insert_id()";

        int postIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

        // 게시글 사진 추가
        List<String> postImg = postPostReq.getPostImg_url();
        for (String str : postImg){
            String registerPostImgQuery = "insert into PostImg(postIdx, postImg_url) values(?, ?)";
            Object[] registerPostImgParams = new Object[]{
                    postIdx,
                    str
            };
            this.jdbcTemplate.update(registerPostImgQuery, registerPostImgParams);
        }

        // 해시태그 추가
        List<String> hashTag = postPostReq.getHashTagName();
        for (String str : hashTag){
            String registerPostTagQuery = "insert into HashTag(postIdx, hashTagName) values(?, ?)";
            Object[] registerPostTagParams = new Object[]{
                    postIdx,
                    str
            };
            this.jdbcTemplate.update(registerPostTagQuery, registerPostTagParams);
        }
        return postIdx;
    }
}

