package com.example.demo.src.ban;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class BanDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    public long checkStore(long bannedUserIdx){
        String checkStoreQuery = "select IF(U.status = 'D', true, false) from User U where userIdx = ?";
        return this.jdbcTemplate.queryForObject(checkStoreQuery, long.class, bannedUserIdx);
    }

    public int checkBan(long banUserIdx, long bannedUserIdx){
        String checkBanQuery = "select exists(select storeBanIdx from StoreBan where banUserIdx=? and bannedUserIdx=?)";
        Object[] checkBanParams = new Object[] {banUserIdx, bannedUserIdx};
        return this.jdbcTemplate.queryForObject(checkBanQuery, int.class, checkBanParams);
    }

    public long banUser(long banUserIdx, long bannedUserIdx) {
        String banQuery = "insert into StoreBan(banUserIdx, bannedUserIdx) " +
                "values(?, (select userIdx from User where status = 'A' and userIdx = ?))";
        Object[] banParams = new Object[]{
                banUserIdx,
                bannedUserIdx
        };
        this.jdbcTemplate.update(banQuery, banParams);

        String lastInsertQuery = "select last_insert_id();";
        return this.jdbcTemplate.queryForObject(lastInsertQuery, long.class);
    }

    public long banExist(long banUserIdx, long bannedUserIdx) {
        String banQuery = "update StoreBan set status='A' where banUserIdx = ? and bannedUserIdx = ?";
        Object[] banParams = new Object[]{
                banUserIdx,
                bannedUserIdx
        };
        this.jdbcTemplate.update(banQuery, banParams);

        String banIdxQuery = "select storeBanIdx from StoreBan where banUserIdx = ? and bannedUserIdx = ?";
        Object[] banIdxParams = new Object[]{
                banUserIdx,
                bannedUserIdx
        };
        return this.jdbcTemplate.queryForObject(banIdxQuery, long.class, banIdxParams);
    }

    public int cancelBan(long banUserIdx, long bannedUserIdx) {
        String cancelBanQuery = "update StoreBan set status='D' where banUserIdx = ? and bannedUserIdx = ?";
        Object[] cancelBanParams = new Object[]{
                banUserIdx,
                bannedUserIdx
        };
        return this.jdbcTemplate.update(cancelBanQuery, cancelBanParams);
    }
}
