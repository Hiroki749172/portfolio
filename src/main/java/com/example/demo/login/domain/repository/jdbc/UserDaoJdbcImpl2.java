package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;

@Repository("UserDaoJdbcImpl2")
public class UserDaoJdbcImpl2 extends UserDaoJdbcImpl {

	@Autowired
	private JdbcTemplate jdbc;
	
	//ユーザー1件取得
	@Override
	public User selectOne(String userId) {
		
		//１件取得SQL
		String sql ="SELECT * FROM user_master WHERE user_id = ?";
		
		//RowMapperの生成
		UserRowMapper rowMapper = new UserRowMapper();
		
		//SQL実行
		return jdbc.queryForObject(sql, rowMapper, userId);
	}
	
	
	//ユーザ全件取得
	@Override
	public List<User> selectMany(){
		//user_masterテーブルのデータを全件取得するSQL
		String sql ="SELECT * FROM user_master";
		
		//RowMapperの生成
		RowMapper<User> rowMapper = new UserRowMapper();
		
		//SQL実行
		return jdbc.query(sql, rowMapper);
		
		//JdbcTemplateのメソッドの引数に、RowMapperをセットします。
		//そうすることでselect結果が入ったUserクラスやList<User>の結果が返ってきます
	}
}
