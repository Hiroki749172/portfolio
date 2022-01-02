package com.example.demo.login.domain.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.login.domain.model.User;

//RowMapperを使用。<>にはJavaオブジェクトのクラスを指定。RowMapperを継承してmapRowメソッドをOverrideする
//引数のResultSetにはSELECT結果が入っています。そのためResultSetの値をUserクラスにセットします
public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		//戻り値用のUserインスタンスの生成
		User user = new User();
		
		//ResultSetの取得結果をUserインスタンスにセット
		user.setUserId(rs.getString("user_id"));
		user.setPassword(rs.getNString("password"));
		user.setUserName(rs.getString("user_name"));
		user.setMaster(rs.getString("master"));
		user.setAttendanceDate(rs.getDate("attendance_date"));
		//user.setRole(rs.getString("role"));
		
		return user;
	}
}
