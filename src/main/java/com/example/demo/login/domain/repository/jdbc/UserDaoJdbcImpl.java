package com.example.demo.login.domain.repository.jdbc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {
	//JdbcTemplateはBean定義されているため@Autowiredで使えるようになる
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	
	
	
	//user_masterテーブルの件数を取得
	@Override
	public int count() throws DataAccessException {
		/*Objectの取得
		 * カウントの結果やカラムを1つだけ取得してくるような場合にはqueryForObjectメソッドを使う
		 * 第1引数にSQL文、第2引数に戻り値のオブジェクトのclassを指定*/
		//全件取得してカウント
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM user_master", Integer.class);
		return count;
	}
	
	//user_masterテーブルにデータを1件insert
	@Override
	public int insertOne(User user) throws DataAccessException{
		
		//パスワード暗号化
		String password = passwordEncoder.encode(user.getPassword());
		
		/*JdbcTemplateクラスを使って登録（insert）するにはupdateメソッドを使う
		 * 第1引数にSQL、第2引数以降にはPreparedStatementを使う。
		 * PreparedStatementには、SQL文の？の部分に入れる変数を引数にセットしていく。セットした順番にSQL文に代入
		 * updateメソッドの戻り値には、登録したレコード数が返ってくる
		 * */
		//1件登録
		String sql = "INSERT INTO user_master("
				+ " user_id,"
				+ " password,"
				+ " user_name,"
				+ " master)"
				+ " VALUES(?, ?, ?, ?)";
				
				//1件登録
				int rowNumber =jdbc.update(sql
				, user.getUserId()
				, password
				, user.getUserName()
				, user.getMaster());
		return rowNumber;
	}
	
	//user_masterテーブルのデータを1件取得
	@Override
	public User selectOne(String userId) throws DataAccessException {
		//1件取得するためqueryForMapをつかう第1引数にSQL文、第2引数にPreparedStatement
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM user_master"
				+ " WHERE user_id = ?", userId);
		
		//結果返却用の変数
		User user = new User();
		
		//取得したデータを結果返却用の変数にセットしていく
		user.setUserId((String) map.get("user_id"));
		user.setPassword((String) map.get("password"));
		user.setUserName((String) map.get("user_name"));
		user.setMaster((String) map.get("master"));
		//user.setRole((String)map.get("role"));
		return user;
	}
	
	
	//user_masterテーブルの全データを取得
	@Override
	public List<User> selectMany() throws DataAccessException {
		/*複数件のSELECT
		 * queryForListメソッドを使う。戻り値の型にはList<Map<String, Object>>を指定する
		 * Listが行を指し、Mapが列。Mapのgetメソッドでテーブルのカラム名を指定することで値を取得できます
		 * このソースでは拡張forでList<Map<String, Object>>をList<User>に変換
		 * */
		//user_masterテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user_master");
		
		//結果返却用の変数
		List<User> userList = new ArrayList<>();
		
		//取得したデータを結果返却用のListに格納していく
		for(Map<String, Object> map:getList) {
			//Userインスタンスの生成
			User user = new User();
			
			//Userインスタンスに取得したデータをセットする
			user.setUserId((String)map.get("user_id"));
			user.setPassword((String)map.get("password"));
			user.setUserName((String)map.get("user_name"));
			user.setMaster((String)map.get("master"));
			//user.setRole((String)map.get("role"));
			
			//結果返却用のListに追加
			userList.add(user);
		}
		return userList;
	}
	
	//user_masterテーブルを1件更新
	@Override
	public int updateOne(User user) throws DataAccessException {
		//パスワード暗号化
				String password = passwordEncoder.encode(user.getPassword());
		
		//insertの時と同様にupdate文を使用
		//1件更新
		String sql = "UPDATE USER_MASTER SET"
				+ " password = ?,"
				+ " user_name = ?,"
				+ " master = ?"
				+ " WHERE user_id = ?";
		
		//1件更新
		int rowNumber = jdbc.update(sql
				, password
				, user.getUserName()
				, user.getMaster()
				, user.getUserId());
		
//		//トランザクション確認のためわざと例外をthrowする
//		if(rowNumber > 0) {
//			throw new DataAccessException("トランザクションテスト") {};
//		}
		return rowNumber;
	}
	
	//user_masterテーブルを1件削除
	@Override
	public int deleteOne(String userId) throws DataAccessException {
		//１件削除
		int rowNumber = jdbc.update("DELETE FROM user_master WHERE user_id = ?", userId);
		
		return rowNumber;
	}
	
	//SQL取得結果をサーバーにCSVで保存する
	@Override
	public void userCsvOut() throws DataAccessException {
		//user_masterテーブルのデータを全件取得するSQL
		String sql ="SELECT * FROM user_master";
		
		//ResultSetExtractorの生成	
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		
		//SQL実行＆CSV出力
		jdbc.query(sql, handler);
	}

	//access_historyテーブルにデータを1件insert
	@Override
	public int insertTwo(User user) throws DataAccessException {
		String sql = "INSERT INTO access_history(user_id,"
				+ " ip_address,"
				+ " login_time)"
				+ " VALUES(?, ?, ?)";
		int rowNumber =jdbc.update(sql
				, user.getUserId()
				, user.getIpAddress()
				, user.getLoginTime());
		return rowNumber;
	}

	//access_manegeテーブルにデータを1件insert
	@Override
	public int insertThree(User user) throws DataAccessException {
		String sql = "INSERT INTO access_manege(user_id,"
				+ " ip_address,"
				+ " login_time)"
				+ " VALUES(?, ?, ?)";
		int rowNumber =jdbc.update(sql
				, user.getUserId()
				, user.getIpAddress()
				, user.getLoginTime());
		return rowNumber;
	}
	
	//attendance_informationテーブルの件数を取得
	@Override
	public int count4() throws DataAccessException {
		int count4 = jdbc.queryForObject("SELECT COUNT(*) FROM attendance_information", Integer.class);
		
		return count4;
	}

	//attendance_informationテーブルにデータを1件insert
	@Override
	public int insertFor(User user) throws DataAccessException {
		String sql = "INSERT INTO attendance_information(user_id,"
				+ " punch,"
				+ " attendance_date,"
				+ " start_time,"
				+ " end_time)"
				+ " VALUES(?, ?, CURRENT_TIMESTAMP, ?, ?)";
		
		int rowNumber = jdbc.update(sql
				, user.getUserId()
				, user.isPunch()
//				, user.getAttendanceDate()
				, user.getStartTime()
				, user.getEndTime());
		
		return rowNumber;
	}
	
	//attendance_informationテーブルのデータを１件取得
	@Override
	public User selectFor(String userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM attendance_information"
				+ " WHERE user_id = ?", userId);
		User user = new User();
		
		user.setUserId((String) map.get("user_id"));
		user.setPunch((Boolean) map.get("punch"));
		user.setAttendanceDate((Timestamp) map.get("attendance_date"));
		user.setStartTime((Timestamp) map.get("start_time"));
		user.setEndTime((Timestamp) map.get("end_time"));
		
		return user;
	}
	
	//attendance_informationテーブルの全データを取得
	@Override
	public List<User> selectManyFor() throws DataAccessException {
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM attendance_information");
		List<User> userList = new ArrayList<>();
		for(Map<String, Object> map : getList) {
			User user = new User();
			
			user.setUserId((String) map.get("user_id"));
			user.setPunch((Boolean) map.get("punch"));
			user.setAttendanceDate((Date) map.get("attendance_date"));
			user.setStartTime((Timestamp) map.get("start_time"));
			user.setEndTime((Timestamp) map.get("end_time"));
			
			userList.add(user);
		}
		return userList;
	}

	//user_masterテーブルのパスワードを1件取得
	@Override
	public User selectPass(String userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT user_id, password FROM user_master WHERE user_id = ?", userId);
		User user = new User();
		
		user.setUserId((String) map.get("user_id"));
		user.setPassword((String) map.get("password"));
		
		return user;
	}

	
}

//UserDaoインターフェースを実装したクラス