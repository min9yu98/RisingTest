package com.example.demo.src.zzim;

import com.example.demo.src.inquiry.model.CreateInquiryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ZzimDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createZzim(int userIdx, int postIdx) {
        String createZzimQuery = "insert into Zzim(userIdx, postIdx) values(?,?)"; //
        Object[] createZzimParams = new Object[]{userIdx,postIdx};
        this.jdbcTemplate.update(createZzimQuery, createZzimParams);
    }

    public void deleteZzim(int userIdx, int postIdx) {
        String deleteZzimQuery = "delete from Zzim WHERE postIdx = ? and userIdx = ?;"; //
        Object[] deleteZzimParams = new Object[]{userIdx,postIdx};
        this.jdbcTemplate.update(deleteZzimQuery, deleteZzimParams);
    }

}
