package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoNamedJdbcImpl")
public class UserDaoNamedJdbcImpl implements UserDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	//件数を取得
	@Override
	public int count() throws DataAccessException {
		String sql = "SELECT COUNT(*) FROM user_master";
		//パラメータ生成
		SqlParameterSource params = new MapSqlParameterSource();
		//全件取得してカウント
		return jdbc.queryForObject(sql, params, Integer.class);
	}

	//データを1件insert
	@Override
	public int insertOne(User user) throws DataAccessException {
		//SQL文にキーを指定
		String sql = "INSERT INTO user_master("
				+ "user_id,"
				+ " password,"
				+ " user_name"
				+ " master"
				+ " role)"
				+ " VALUES("
				+ ":userId"
				+ " :password"
				+ " :userName"
				+ " :master"
				+ " :role)";
		
		//パラメータの設定するためSqlParameterSource
		//
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", user.getUserId())
				.addValue("password", user.getPassword())
				.addValue("userName", user.getUserName())
				.addValue("master", user.getMaster());
//				.addValue("role", user.getRole());
		
		return jdbc.update(sql, params);
	}

	//データを１件取得
	@Override
	public User selectOne(String userId) throws DataAccessException {
		String sql ="SELECT * FROM user_master WHERE user_id = :userId";
		//パラメータ
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", userId);
		
		Map<String, Object> map = jdbc.queryForMap(sql, params);
		
		//結果返却用のインスタンスを生成
		User user = new User();
		
		//取得データをインスタンスにセットしていく
		user.setUserId((String)map.get("user_id"));
		user.setPassword((String)map.get("password"));
		user.setUserName((String)map.get("user_name"));
		user.setMaster((String)map.get("master"));
//		user.setRole((String)map.get("role"));
		
		return user;
	}

	//全データを取得
	@Override
	public List<User> selectMany() throws DataAccessException {
		String sql = "SELECT * FROM user_master";
		SqlParameterSource params = new MapSqlParameterSource();
		List<Map<String, Object>> getList = jdbc.queryForList(sql, params);
		//結果返却用のList
		List<User> userList = new ArrayList<>();
		
		//取得データ分ループ
		for(Map<String, Object> map:getList) {
			User user = new User();
			user.setUserId((String)map.get("user_id"));
			user.setPassword((String)map.get("password"));
			user.setUserName((String)map.get("user_name"));
			user.setMaster((String)map.get("master"));
//			user.setRole((String)map.get("role"));
			userList.add(user);			
		}
		return userList;
	}

	@Override
	public int updateOne(User user) throws DataAccessException {
		String sql = "UPDATE user_master"
				+ "SET"
				+ " password = :password,"
				+ " user_name = :userName,"
				+ " master = :master"
				+ " WHERE user_id = :userId";
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", user.getUserId())
				.addValue("password", user.getPassword())
				.addValue("userName", user.getUserName())
				.addValue("master", user.getMaster());
//				.addValue("role", user.getRole());
		return jdbc.update(sql, params);
	}

	@Override
	public int deleteOne(String userId) throws DataAccessException {

		String sql = "DELETE FROM user_master WHERE user_id = :userId";
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", userId);
		int rowNumber = jdbc.update(sql, params);
		return rowNumber;
	}

	@Override
	public void userCsvOut() throws DataAccessException {
		String sql = "SELECT * FROM user_master";
		
		//ResultSetExtractorの生成
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		
		//クエリ実行＆CSV出力
		jdbc.query(sql, handler);
		
	}

	@Override
	public int insertTwo(User user) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int insertThree(User user) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int insertFor(User user) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public List<User> selectManyFor() throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public User selectFor(String userId) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int count4() throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public User selectPass(String userId) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
