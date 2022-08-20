package com.example.demo.src.post;

import com.example.demo.src.post.model.GetPostsRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

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
                        rs.getString("zzim"),
                        rs.getString("postImg"),
                        rs.getInt("price"),
                        rs.getString("postTitle"),
                        rs.getString("userRegion"),
                        rs.getString("postingTime"),
                        rs.getString("payStatus"),
                        rs.getInt("interestNum")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }
}

