package com.Extract.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.Extract.util.ExtractPojo;

public class ClaimsRowMapper implements RowMapper<ExtractPojo>{

	public ExtractPojo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExtractPojo extractPojo = new ExtractPojo();
		extractPojo.setClientID(rs.getString("clientID"));
		extractPojo.setBatchSeq(rs.getString("batchSeq"));
		extractPojo.setSystdate(rs.getString("Systdate"));
		return extractPojo;
	}

}
