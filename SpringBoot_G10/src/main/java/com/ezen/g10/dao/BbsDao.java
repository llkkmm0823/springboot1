package com.ezen.g10.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ezen.g10.dto.BbsDto;

@Repository
public class BbsDao implements IBbsDao {

	@Autowired
	private JdbcTemplate template;

	@Override
	public List<BbsDto> getList() {
		String sql = "select *from bbs order by id desc";
		/*
		 * List<BbsDto> list = template.query(sql, new BeanPropertyRowMapper<BbsDto>(BbsDto.class)
		 * 										, 물음표가 sql에 있다면 들어갈 항목들을 순서대로 나열);
		 */

		List<BbsDto> list = template.query(sql, new RowMapper<BbsDto>() {

			@Override
			public BbsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				// select의 결과는 rs에 있으니 BbsDto와의 연결은 직접 해결해야함
				BbsDto bdto = new BbsDto();
				bdto.setId(rs.getInt("id"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setContent(rs.getString("content"));
				bdto.setTitle(rs.getString("title"));

				return bdto; // 리턴된 bdto는 list에 차곡차곡 쌓임
			}
		}
		// , 물음표에 들어갈 항목들을 순서대로 나열
	);
		return list;
	}

	@Override
	public int write(BbsDto bdto) {
		String sql = "insert into bbs values(bbs_seq.nextVal, ? , ? , ?)";
		int result = template.update(sql , bdto.getWriter(), bdto.getTitle(), bdto.getContent());		
		return result;
	}

	@Override
	public int update(BbsDto bdto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BbsDto view(int id) {
		String sql = "select *from bbs where id=?";		
		BbsDto bdto = template.queryForObject(sql, new BeanPropertyRowMapper<BbsDto>(BbsDto.class),id);
		
		return bdto;
	}

}
