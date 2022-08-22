package com.example.demo.src.post;

import com.example.demo.src.post.model.GetPostsRes;
import com.example.demo.src.post.model.PostPostReq;
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
    public List<GetPostsRes> getPosts() {
        String getPostsQuery = "";
        return this.jdbcTemplate.query(getPostsQuery,
                (rs, rowNum) -> new GetPostsRes(
                        rs.getString("likeStatus"),
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
        List<String> postImg = postPostReq.getPostImg_url();
        for (String str : postImg){
            String registerPostImgQuery = "insert into PostImg(postIdx, postImg_url) values(?, ?)";
            Object[] registerPostImgParams = new Object[]{
                    postIdx,
                    str
            };
            this.jdbcTemplate.update(registerPostImgQuery, registerPostImgParams);
        }
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

