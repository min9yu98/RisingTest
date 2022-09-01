package com.example.demo.src.talk;

import com.example.demo.src.inquiry.model.Inquiry;
import com.example.demo.src.talk.model.GetSpecificTalkList;
import com.example.demo.src.talk.model.GetTalkList;
import com.example.demo.src.talk.model.MessageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TalkDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void createTalk(MessageReq messageReq,int userIdx, int postUserIdx,int roomIdx) {
        String Query = "insert into Talk(senderIdx,receiverIdx,talkRoomIdx,talking) values(?,?,?,?)"; //
        Object[] Params = new Object[]{userIdx,postUserIdx,roomIdx,messageReq.getMessage()};
        this.jdbcTemplate.update(Query, Params);
    }

    public List<GetTalkList> getTalkListBySender(int userIdx) {
        String getInquiryQuery = "select S.lastMessage as lastMessage, S.updatedAt as updatedAt, S.talkRoomIdx as talkRoomIdx,U.userName as " +
                "postUserName, U.userIdx as userIdx ,U.profileImg_url as postUserImg_url from (select T.talking as lastMessage , T.updateAt as updatedAt," +
                " T.talkRoomIdx, senderIdx, receiverIdx From Talk T WHERE  senderIdx = ? or receiverIdx = ? GROUP BY T.talkRoomIdx) as S," +
                " User U WHERE S.receiverIdx = U.userIdx and S.senderIdx = ?;"; // 해당 email을 만족하는 User의 정보들을 조회한다.

        return this.jdbcTemplate.query(getInquiryQuery,
                (rs, rowNum) -> new GetTalkList(
                        rs.getString("lastMessage"),
                        rs.getString("updatedAt"),
                        rs.getInt("talkRoomIdx"),
                        rs.getString("postUserName"),
                        rs.getInt("userIdx"),
                        rs.getString("postUserImg_url")
                ),userIdx, userIdx, userIdx // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public List<GetTalkList> getTalkListByReceiver(int userIdx) {
        String getInquiryQuery = "select S.lastMessage as lastMessage, S.updatedAt as updatedAt, S.talkRoomIdx as talkRoomIdx,U.userName as " +
                "postUserName, U.userIdx as userIdx ,U.profileImg_url as postUserImg_url from (select T.talking as lastMessage , T.updateAt as updatedAt," +
                " T.talkRoomIdx, senderIdx, receiverIdx From Talk T WHERE  senderIdx = ? or receiverIdx = ? GROUP BY T.talkRoomIdx) as S," +
                " User U WHERE S.senderIdx = U.userIdx and S.receiverIdx = ?;"; // 해당 email을 만족하는 User의 정보들을 조회한다.

        return this.jdbcTemplate.query(getInquiryQuery,
                (rs, rowNum) -> new GetTalkList(
                        rs.getString("lastMessage"),
                        rs.getString("updatedAt"),
                        rs.getInt("talkRoomIdx"),
                        rs.getString("postUserName"),
                        rs.getInt("userIdx"),
                        rs.getString("postUserImg_url")
                ),userIdx, userIdx, userIdx // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public List<GetSpecificTalkList> getSpecificTalkList(int talkRoomIdx) {
        String getInquiryQuery = "select talking as message, updateAt as updatedAt from Talk WHERE talkRoomIdx = ?"; // 해당 email을 만족하는 User의 정보들을 조회한다.

        return this.jdbcTemplate.query(getInquiryQuery,
                (rs, rowNum) -> new GetSpecificTalkList(
                        rs.getString("message"),
                        rs.getString("updatedAt")
                ),talkRoomIdx // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }


    public int isRoom(int userIdx,int postUserIdx) {
        String getInquiryQuery = "select COUNT(*) as roomNum from Talk WHERE senderIdx = ? and receiverIdx = ?;";
        int getInquiryParam = userIdx;

        return this.jdbcTemplate.queryForObject(getInquiryQuery,
                (rs, rowNum) ->
                        rs.getInt("roomNum"),
                getInquiryParam,postUserIdx
        );
    }

    public int getRoomIdx(int userIdx,int postUserIdx) {
        String getInquiryQuery = "select talkRoomIdx as roomNum from Talk WHERE senderIdx = ? and receiverIdx = ?;";
        int getInquiryParam = userIdx;

        return this.jdbcTemplate.queryForObject(getInquiryQuery,
                (rs, rowNum) ->
                        rs.getInt("roomNum"),
                getInquiryParam,postUserIdx
        );
    }

    public int createRoom(int userIdx) {
        String Query = "insert into TalkRoom() values()"; //
        Object[] Params = new Object[]{userIdx};
        this.jdbcTemplate.update(Query);
        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

}
