package com.example.demo.login.domain.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.demo.login.domain.model.User;

/*ResultSetExtractor
 * やっていることはRowMapperと似ている。ResultSetExtractor<List<T>>をimplementsする。extractData()メソッドをOverrideする
 * そのメソッドの中でResultSetとオブジェクトのマッピングを行い、複数件取得する前提なのでwhile文でループ処理を行う*/
public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {
	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException{
		//User格納用List
		List<User> userList = new ArrayList<>();
		
		//取得件数分のloop
		while(rs.next()) {
			//Listに追加するインスタンスを生成する
			User user = new User();
			
			
			//取得したレコードをUserインスタンスにセット
			user.setUserId(rs.getString("user_id"));
			user.setPassword(rs.getNString("password"));
			user.setUserName(rs.getString("user_name"));
			user.setMaster(rs.getString("master"));
			//user.setRole(rs.getString("role"));
			user.setAttendanceDate(rs.getDate("attendance_date"));
			
			//ListにUserを追加
			userList.add(user);
		}
		//1件もなかった場合は例外を投げる
		if(userList.size() == 0) {
			throw new EmptyResultDataAccessException(1);
		}
		return userList;
	}
}
