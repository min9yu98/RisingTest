package com.example.demo.src.Follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class FollowDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void createFollow(int userIdx, int postUserIdx) {
        String Query = "insert into Follow(followerUserIdx, followeeUserIdx) values(?,?)"; //
        Object[] Params = new Object[]{userIdx,postUserIdx};
        this.jdbcTemplate.update(Query, Params);
    }

    public void deleteFollow(int userIdx, int postUserIdx) {
        String Query = "delete from Follow WHERE followerUserIdx = ? and followeeUserIdx = ?"; //
        Object[] Params = new Object[]{userIdx,postUserIdx};
        this.jdbcTemplate.update(Query, Params);
    }
}
