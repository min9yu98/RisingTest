package com.example.demo.src.notice;

import com.example.demo.src.notice.model.GetNoticeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NoticeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetNoticeRes> getNotice(String category, long pageNum) {
        String getNoticeQuery = "select noticeTitle, DATE(createAt) as postingTime, noticeContent, noticeImg_url " +
                "from Notice where noticeCategory = ?";
        return this.jdbcTemplate.query(getNoticeQuery,
                (rs, rowNum) -> new GetNoticeRes(
                        rs.getString("noticeTitle"),
                        rs.getString("postingTime"),
                        rs.getString("noticeContent"),
                        rs.getString("noticeImg_url")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        , category);
    }

    public List<GetNoticeRes> getNotices(long pageNum) {
        String getNoticeQuery = "select noticeTitle, DATE(createAt) as postingTime, noticeContent, noticeImg_url from Notice";
        return this.jdbcTemplate.query(getNoticeQuery,
                (rs, rowNum) -> new GetNoticeRes(
                        rs.getString("noticeTitle"),
                        rs.getString("postingTime"),
                        rs.getString("noticeContent"),
                        rs.getString("noticeImg_url")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }
}
