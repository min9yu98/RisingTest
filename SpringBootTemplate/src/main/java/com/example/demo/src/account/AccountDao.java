package com.example.demo.src.account;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.account.model.GetAccountRes;
import com.example.demo.src.account.model.PatchDeleteAccountReq;
import com.example.demo.src.account.model.PatchEditAccountReq;
import com.example.demo.src.account.model.PostAccountReq;
import com.example.demo.src.post.model.get.GetPostSearchQueryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AccountDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long registerAccount(PostAccountReq postAccountReq, long userIdx) {
        String registerAccountQuery;
        if(checkAccountNum(postAccountReq.getAccountNum(), userIdx) == 1){
            registerAccountQuery = "update Account set status = 'A' where accountNum = ?";
            this.jdbcTemplate.update(registerAccountQuery, postAccountReq.getAccountNum());
        } else {
            registerAccountQuery = "insert into Account(accountUserIdx, accountHolder, bank, accountNum, defaultAccount) values (?, ?, ?, ?, ?)";
            Object[] registerAccountParams = new Object[]{
                    userIdx,
                    postAccountReq.getAccountHolder(),
                    postAccountReq.getBank(),
                    postAccountReq.getAccountNum(),
                    postAccountReq.isDefaultAccount()
            };
            this.jdbcTemplate.update(registerAccountQuery, registerAccountParams);
        }

        String lastInsertIdQuery = "select last_insert_id();";
        long accountIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);

        return accountIdx;
    }

    public int checkAccountNum(long userIdx){
        String check = "select COUNT(A.accountIdx) from Account A where A.accountUserIdx = ?";
        return this.jdbcTemplate.queryForObject(check,
                int.class, userIdx);
    }

    public int checkAccountNum(String accountNum, long userIdx){
        String check = "select exists(select accountIdx from Account where accountUserIdx = ? and accountNum = ?)";
        Object[] checkParams = new Object[]{userIdx, accountNum};
        return this.jdbcTemplate.queryForObject(check,
                int.class, checkParams);
    }

    public int deleteAccount(PatchDeleteAccountReq patchDeleteAccountReq) {
        String deleteAccountQuery = "update Account A set A.status = 'D' where A.accountIdx = ?";
        return this.jdbcTemplate.update(deleteAccountQuery, patchDeleteAccountReq.getAccountIdx());
    }

    public List<GetAccountRes> getAccounts(long userIdx) {
        String getAccountsQuery = "select accountIdx, IF(defaultAccount = 1, (select \"정산계좌 / 환불계좌\"), null) as roll, " +
                "bank, accountNum, accountHolder " +
                "from Account where accountUserIdx = ? and status = 'A'";
        return this.jdbcTemplate.query(getAccountsQuery,
                (rs, rowNum) -> new GetAccountRes(
                        rs.getLong("accountIdx"),
                        rs.getString("roll"),
                        rs.getString("bank"),
                        rs.getString("accountNum"),
                        rs.getString("accountHolder")
                ), userIdx);
    }

    public int editAccount(PatchEditAccountReq patchEditAccountReq, long userIdx, long accountIdx) {
        if (patchEditAccountReq.getAccountHolder() != null) {
            String accountHolderQuery = "update Account " +
                    "set accountHolder = ? " +
                    "where accountUserIdx = ? and accountIdx = ?";
            Object[] accountHolderParams = new Object[]{
                    patchEditAccountReq.getAccountHolder(),
                    userIdx,
                    accountIdx
            };
            this.jdbcTemplate.update(accountHolderQuery, accountHolderParams);
        }

        // 게시글 거래 지역 수정
        if (patchEditAccountReq.getBank() != null) {
            String bankQuery = "update Account " +
                    "set bank = ? " +
                    "where accountUserIdx = ? and accountIdx = ?";
            Object[] bankParams = new Object[]{
                    patchEditAccountReq.getBank(),
                    userIdx,
                    accountIdx
            };
            this.jdbcTemplate.update(bankQuery, bankParams);
        }

        // 게시글 제목 수정
        if (patchEditAccountReq.getAccountNum() != null) {
            String accountNumQuery = "update Account " +
                    "set accountNum = ? " +
                    "where accountUserIdx = ? and accountIdx = ?";
            Object[] accountNumParams = new Object[]{
                    patchEditAccountReq.getAccountNum(),
                    userIdx,
                    accountIdx
            };
            this.jdbcTemplate.update(accountNumQuery, accountNumParams);
        }

        if (!patchEditAccountReq.isDefaultAccount()){
            String defaultAccountQuery = "update Account " +
                    "set defaultAccount = ? " +
                    "where accountUserIdx = ? and accountIdx = ?";
            Object[] defaultAccountParams = new Object[]{
                    patchEditAccountReq.isDefaultAccount(),
                    userIdx,
                    accountIdx
            };
            this.jdbcTemplate.update(defaultAccountQuery, defaultAccountParams);
        }

        return 1;
    }
}
