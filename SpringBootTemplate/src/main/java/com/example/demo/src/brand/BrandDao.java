package com.example.demo.src.brand;

import com.example.demo.src.brand.model.GetBrandRes;
import com.example.demo.src.brand.model.PatchDeleteBrandReq;
import com.example.demo.src.brand.model.PostBrandReq;
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
}
