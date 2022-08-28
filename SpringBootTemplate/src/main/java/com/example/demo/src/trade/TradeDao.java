package com.example.demo.src.trade;

import com.example.demo.src.post.model.get.GetPostSearchRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TradeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public long trade(long userIdx, long postIdx, boolean flag) {
        String tradeQuery;
        Object[] tradeParams;
        if (flag){
            tradeQuery = "insert into Trade(postIdx, buyerIdx) VALUES (?, ?);";
            tradeParams = new Object[]{
                    postIdx,
                    userIdx
            };
        } else{
            tradeQuery = "update Trade set status = 'A' where postIdx = ?";
            tradeParams = new Object[]{
                    postIdx
            };
        }

        this.jdbcTemplate.update(tradeQuery, tradeParams);

        String tradeQuery2 = "update Post P set P.sellingStatus = \"판매완료\" where P.postIdx = ?";
        this.jdbcTemplate.update(tradeQuery2, postIdx);
        return postIdx;
    }

    public int checkPresence(long postIdx) {
        String check = "select exists(select T.postIdx from Trade T where T.postIdx = ?)";
        return this.jdbcTemplate.queryForObject(check, int.class, postIdx);
    }
}
