package com.example.demo.src.myPage;


import com.example.demo.src.inquiry.model.GetMyInquiryRes;
import com.example.demo.src.myPage.model.MyPage;
import com.example.demo.src.myPage.model.MyPageFollowing;
import com.example.demo.src.myPage.model.PostFollow;
import com.example.demo.src.myPage.model.PostMyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

@Repository
public class MyPageDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public MyPage getMyPageZzimNum(int userIdx) {
        String getInquiryQuery = "select COUNT(*) as zzim_num FROM Zzim WHERE userIdx = ?;"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        int getInquiryParam = userIdx; // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getInquiryQuery,
                (rs, rowNum) -> new MyPage(
                        rs.getInt("zzim_num")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getInquiryParam
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public int getMyPageFollowerNum(int userIdx) {
        String getInquiryQuery = "select COUNT(*)  as follower_num From Follow WHERE followeeUserIdx = ?";
        int getInquiryParam = userIdx;

        return this.jdbcTemplate.queryForObject(getInquiryQuery,
                (rs, rowNum) ->
                        rs.getInt("follower_num"),
                getInquiryParam
        );
    }
    public int getMyPageFollowingNum(int userIdx) {
        String getInquiryQuery = "select COUNT(*)  as following_num From Follow WHERE followerUserIdx = ?";
        int getInquiryParam = userIdx;

        return this.jdbcTemplate.queryForObject(getInquiryQuery,
                (rs, rowNum) ->
                        rs.getInt("following_num"),
                getInquiryParam
        );
    }

    public int getMyPagePostReviewNum(int userIdx) {
        String getInquiryQuery = "select COUNT(*)  as PostReviewNum FROM Post P, PostReview  PR " +
                "WHERE P.postIdx = PR.postIdx and P.userIdx = ?;";
        int getInquiryParam = userIdx;

        return this.jdbcTemplate.queryForObject(getInquiryQuery,
                (rs, rowNum) ->
                        rs.getInt("PostReviewNum"),
                getInquiryParam
        );
    }

    public MyPage getMyPageNameImg(int userIdx) {
        String getInquiryQuery = "select userName, profileImg_url FROM User WHERE userIdx = ?;"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        int getInquiryParam = userIdx; // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getInquiryQuery,
                (rs, rowNum) -> new MyPage(
                        rs.getString("userName"),
                        rs.getString("profileImg_url")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getInquiryParam
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public List<PostMyPage> getMyPostPages(int userIdx, String sellingStatus) {
        String getInquiryQuery = "select  PI.postImg_url as postImg_url, P.price as price, P.postTitle " +
                "as postTitle, P.postIdx as postIdx, U.userName as postUserName FROM Post P, PostImg PI, User U " +
                "WHERE P.postIdx = PI.postIdx and P.userIdx  = ? and U.userIdx = P.userIdx and sellingStatus = ? GROUP BY P.postIdx;"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        int getInquiryParam = userIdx;

        return this.jdbcTemplate.query(getInquiryQuery,
                (rs, rowNum) -> new PostMyPage(
                        rs.getString("postImg_url"),
                        rs.getInt("postIdx"),
                        rs.getString("postTitle"),
                        rs.getInt("price"),
                        rs.getString("postUserName")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getInquiryParam,sellingStatus
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public List<MyPageFollowing> getMyPageFollowerNameImg(int userIdx) {
        String getInquiryQuery = "select U.userName as postUserName, U.userIdx as postUseridx, U.profileImg_url as profileImg_url from Follow F, User U " +
                "WHERE followerUserIdx = ? and U.userIdx = F.followeeUserIdx;"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        int getInquiryParam = userIdx;

        return this.jdbcTemplate.query(getInquiryQuery,
                (rs, rowNum) -> new MyPageFollowing(
                        rs.getInt("postUserIdx"),
                        rs.getString("postUserName"),
                        rs.getString("profileImg_url")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getInquiryParam
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }
    public int getMyPagePostNum(int userIdx) {
        String getInquiryQuery = "select COUNT(*) as postNum From Post WHERE userIdx = ?";
        int getInquiryParam = userIdx;

        return this.jdbcTemplate.queryForObject(getInquiryQuery,
                (rs, rowNum) ->
                        rs.getInt("postNum"),
                getInquiryParam
        );
    }


    public List<PostFollow> getMyPagePosts(int userIdx) {
        String getInquiryQuery = "select P.postIdx, PI.postImg_url, P.price from Post P , PostImg PI " +
                "WHERE userIdx = ?  Group By P.postIdx;"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        int getInquiryParam = userIdx;

        return this.jdbcTemplate.query(getInquiryQuery,
                (rs, rowNum) -> new PostFollow(
                        rs.getInt("postIdx"),
                        rs.getString("postImg_url"),
                        rs.getInt("price")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getInquiryParam
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }
}
