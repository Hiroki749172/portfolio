package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;

@Repository("UserDaoJdbcImpl3")
public class UserDaoJdbcImpl3 extends UserDaoJdbcImpl {

	@Autowired
	private JdbcTemplate jdbc;
	
	//ユーザ１件取得
	@Override
	public User selectOne(String userId) {
		//1件取得用SQL
		String  sql = "SELECT * FROM user_master WHERE user_id = ?";
		
		/* BeanPropertyRowMapperではDBから取得してきたカラム名と同一のフィールド名がクラスにあれば自動でマッピングしてくれます
		 * RowMapperのようにどのカラムとどのフィールドを一致させるか用意（インスタンスを生成）する必要がない*/
		
		//RowMapperの生成
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		
		//SQL実行
		return jdbc.queryForObject(sql, rowMapper, userId);
	}
	
	//ユーザー全件取得
	@Override
	public List<User> selectMany(){
		//user_masterテーブルのデータを全件取得するSQL
		String sql = "SELECT * FROM user_master";
		
		//RowMapperの生成
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		
		//SQL実行
		return jdbc.query(sql, rowMapper);
	}
}
