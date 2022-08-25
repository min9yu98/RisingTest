package com.example.demo.src.brand;

import com.example.demo.src.brand.model.GetBrandRes;
import com.example.demo.src.brand.model.PatchDeleteBrandReq;
import com.example.demo.src.brand.model.PostBrandReq;
import com.example.demo.src.brand.model.PostFollowReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BrandDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long registerBrand(PostBrandReq postBrandReq) {
        String registerPostQuery = "insert into Brand(brandImg_url, brandName, brandEngName) values(?, ?, ?)"; // 실행될 동적 쿼리문
        Object[] registerPostParams = new Object[]{
                postBrandReq.getBrandImg_url(),
                postBrandReq.getBrandName(),
                postBrandReq.getBrandEngName()
        };
        this.jdbcTemplate.update(registerPostQuery, registerPostParams);

        String lastInsertIdQuery = "select last_insert_id();";
        long brandIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, long.class);

        return brandIdx;
    }

    public List<GetBrandRes> getBrandKor() {
        String getBrandKorQuery = "select B.brandIdx, B.brandImg_url, B.brandName, B.brandEngName, (select postIdx from Post) from Brand B ";
        return this.jdbcTemplate.query(getBrandKorQuery,
                (rs, rowNum) -> new GetBrandRes(
                        rs.getLong("brandIdx"),
                        rs.getString("brandImg_url"),
                        rs.getString("brandName"),
                        rs.getString("brandEngName"),
                        rs.getLong("postNum")
                ));
    }

    public int deleteBrand(PatchDeleteBrandReq patchDeleteBrandReq) {
        String deleteBrandQuery = "update Brand B set B.status = 'D' where B.brandName = ?";
        return this.jdbcTemplate.update(deleteBrandQuery, patchDeleteBrandReq.getBrandName());
    }

    public long followBrand(long userIdx, long brandIdx) {
        String followBrandQuery = "insert into BrandFollow(brandIdx, userIdx) values(?, ?)";
        Object[] followBrandParams = new Object[]{
                brandIdx,
                userIdx
        };
        return this.jdbcTemplate.update(followBrandQuery, followBrandParams);
    }

    public int checkFollowBrand(long userIdx, long brandIdx) {
        String checkEmailQuery = "select exists(select brandFollowIdx from BrandFollow where brandIdx = ? and userIdx = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        Object[] checkEmailParams = new Object[] {brandIdx, userIdx}; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams); // checkEmailQuery, checkEmailParams를 통해 가져온 값(intgud)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    public void cancelFollow(long userIdx, long brandIdx) {
        String cancelFollowQuery = "update BrandFollow set status='D' where brandIdx = ? and userIdx = ?";
        Object[] cancelFollowParams = new Object[]{
                brandIdx,
                userIdx
        };
        this.jdbcTemplate.update(cancelFollowQuery, cancelFollowParams);
    }

    public long cancelFollow2(long userIdx, long brandIdx) {
        String followBrandQuery = "update BrandFollow set status='A' where brandIdx = ? and userIdx = ?";
        Object[] followBrandParams = new Object[]{
                brandIdx,
                userIdx
        };
        this.jdbcTemplate.update(followBrandQuery, followBrandParams);
        return brandIdx;
    }
}
