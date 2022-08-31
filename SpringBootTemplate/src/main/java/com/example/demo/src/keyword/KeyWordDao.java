package com.example.demo.src.keyword;

import com.example.demo.src.inquiry.model.CreateInquiryReq;
import com.example.demo.src.keyword.model.KeyWordAlarmRes;
import com.example.demo.src.keyword.model.KeyWordCreateReq;
import com.example.demo.src.myPage.model.MyPageZzim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class KeyWordDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createKeyWord(KeyWordCreateReq keyWordCreateReq, int userIdx) {
        String Query = "insert into KeyWord(userIdx, keyWord) values(?,?);"; //
        Object[] Params = new Object[]{userIdx,keyWordCreateReq.getKeyWord()};
        this.jdbcTemplate.update(Query, Params);
    }

    public void deleteKeyWord(int userIdx, String keyWord) {
        String Query = "delete from KeyWord WHERE userIdx = ? and keyWord = ?;"; //
        Object[] Params = new Object[]{userIdx,keyWord};
        this.jdbcTemplate.update(Query, Params);
    }

    public List<String> getKeyWords(int userIdx) {
        String Query = "select keyWord From KeyWord WHERE userIdx = ?";
        int Param = userIdx;
        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> rs.getString("keyWord"),
                Param
        );
    }

    public List<KeyWordAlarmRes> getKeyWordAlarm(String keyWord) {
        String Query = "select P.postTitle as postTitle, PI.postImg_url  as postImg_url , " +
                "(select(case when timestampdiff(second, P.createAt, current_timestamp) < 60 then concat(timestampdiff(second, P.createAt, current_timestamp), '초 전')when timestampdiff(minute, P.createAt, current_timestamp) < 60 then concat(timestampdiff(minute, P.createAt, current_timestamp), '분 전')when timestampdiff(hour, P.createAt, current_timestamp) < 24 then concat(timestampdiff(hour, P.createAt, current_timestamp), '시간 전')else concat(datediff(current_timestamp, P.createAt), '일 전')end)) as createdAt " +
                ", K.keyWord as keyWord from Post P, PostImg PI, KeyWord K "+
                "WHERE  K.keyWord = ? and (P.postTitle LIKE ?) GROUP BY P.postIdx;\n";
        String Param = "%" + keyWord + "%";
        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new KeyWordAlarmRes(
                        rs.getString("keyWord"),
                        rs.getString("postTitle"),
                        rs.getString("postImg_url"),
                        rs.getString("createdAt")
                ),keyWord,Param
        );
    }
}
