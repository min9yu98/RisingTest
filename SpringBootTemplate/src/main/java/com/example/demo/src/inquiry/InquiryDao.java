package com.example.demo.src.inquiry;

import com.example.demo.src.inquiry.model.*;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class InquiryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createInquiry(CreateInquiryReq createInquiryReq, int userIdx, int orderIdx) {
        String createInquiryQuery = "insert into Inquiry(userIdx, category, orderIdx, description, inquiryUrl) values(?,?,?,?,?)"; //
        Object[] createInquiryParams = new Object[]{userIdx,createInquiryReq.getCategory(),orderIdx, createInquiryReq.getDescription(), createInquiryReq.getInquiryUrl()
        };
        this.jdbcTemplate.update(createInquiryQuery, createInquiryParams);
    }

    public List<GetMyInquiryRes> getMyInquiry(int userIdx) {
        String getInquiryQuery = "select category,inquiryIdx from Inquiry WHERE userIdx = ?;"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        int getInquiryParam = userIdx; // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.query(getInquiryQuery,
                (rs, rowNum) -> new GetMyInquiryRes(
                        rs.getInt("inquiryIdx"),
                        rs.getString("category")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getInquiryParam
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public InquirySpecificRes getInquirySpecific(int inquiryIdx) {
        String getInquiryQuery = "select Inquiry.createdAt as createdAt,Inquiry.description as inquiryDescription,\n" +
                "                InquiryAnswer.description as answerDescription\n" +
                "                from Inquiry, InquiryAnswer\n" +
                "                WHERE Inquiry.inquiryidx = InquiryAnswer.inquiryIdx\n" +
                "                and Inquiry.inquiryIdx = ?;"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        int getInquiryParam = inquiryIdx; // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getInquiryQuery,
                (rs, rowNum) -> new InquirySpecificRes(
                        rs.getString("createdAt"),
                        rs.getString("inquiryDescription"),
                        rs.getString("answerDescription")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getInquiryParam
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public List<Inquiry> getInquiry() {
        String getInquiryQuery = "select inquiryIdx, userIdx, category, description, inquiryUrl, answerStatus, createdAt from Inquiry;"; // 해당 email을 만족하는 User의 정보들을 조회한다.

        return this.jdbcTemplate.query(getInquiryQuery,
                (rs, rowNum) -> new Inquiry(
                        rs.getInt("inquiryIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("inquiryUrl"),
                        rs.getString("answerStatus"),
                        rs.getString("createdAt")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public void createAnswer(CreateAnswerReq createAnswerReq, int inquiryIdx) {
        String createInquiryQuery = "insert into InquiryAnswer(description, inquiryIdx) values(?,?)"; //
        Object[] createInquiryParams = new Object[]{createAnswerReq.getDescription(), inquiryIdx}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createInquiryQuery, createInquiryParams);
    }

    public int patchInquiryStatus(int inquiryIdx) {
        String modifyUserNameQuery = "update Inquiry set answerStatus = '답변함' where inquiryIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyUserNameParams = new Object[]{inquiryIdx}; // 주입될 값들(nickname, userIdx) 순
        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }
}
