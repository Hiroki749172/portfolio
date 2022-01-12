package com.example.demo.login.domain.repository.jdbc;

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
//				+ " role)"
				+ " VALUES(?, ?, ?, ?)"; //, ?
				
				//1件登録
				int rowNumber =jdbc.update(sql
				, user.getUserId()
				, password
				, user.getUserName()
				, user.getMaster());
//				, user.getRole());
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
		user.setMaster((int) map.get("master"));
		return user;
	}
	//user_masterテーブルのパスワードを1件取得
	@Override
	public User selectPass(String password) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT password FROM user_master WHERE password = ?", password);
		User user = new User();
		
		user.setPassword((String) map.get("password"));
		
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
			user.setMaster((int)map.get("master"));
			
			//結果返却用のListに追加
			userList.add(user);
		}
		return userList;
	}
	
	@Override
	public int updateRole(User user) throws DataAccessException {
		String sql = "UPDATE USER_MASTER SET"
				+ " role = ?"
				+ "WHERE role = ?";
		int rowNumber = jdbc.update(sql
				, user.getRole()
				, user.getRole());
		return rowNumber;
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
		if(user.getMaster() == 1) {
			updateRole(user);
		}
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

	//general_historyテーブルにデータを1件insert
	@Override
	public int insertUser(User user) throws DataAccessException {
		String sql = "INSERT INTO general_history(user_id,"
				+ " ip_address,"
				+ " login_time)"
				+ " VALUES(?, ?, CURTIME())";
		int rowNumber =jdbc.update(sql
				, user.getUserId()
				, user.getIpAddress());
//				, user.getLoginTime());
		return rowNumber;
	}

	//admin_historyテーブルにデータを1件insert
	@Override
	public int insertAdmin(User user) throws DataAccessException {
		String sql = "INSERT INTO a(user_id,"
				+ " ip_address,"
				+ " login_time)"
				+ " VALUES(?, ?, CURTIME())";
		int rowNumber =jdbc.update(sql
				, user.getUserId()
				, user.getIpAddress());
//				, user.getLoginTime());
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
		String sql = "INSERT INTO attendance_information("
				+ " user_id,"
				+ " punch,"
				+ " attendance_date)"
				+ " VALUES(?, ?, CURTIME())";
		
		int rowNumber = jdbc.update(sql
				, user.getUserId()
				, user.getPunch());
//				, attendanceDate;
//				, user.getStartTime()
//				, user.getEndTime());
		
		return rowNumber;
	}
	
	
	
	//attendance_informationテーブルのデータを１件取得
	@Override
	public User selectFor(String userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT user_id, punch, attendance_date FROM attendance_information"
				+ " WHERE user_id = ?", userId);
		User user = new User();
		
		user.setUserId((String) map.get("user_id"));
		user.setPunch((int) map.get("punch"));
		user.setAttendanceDate((Date) map.get("attendance_date"));
//		user.setStartTime((Timestamp) map.get("start_time"));
//		user.setEndTime((Timestamp) map.get("end_time"));
		
		return user;
	}

	//勤怠画面に表示（氏名、勤怠区分、勤怠情報時間）するための取得処理
		@Override
		public User selectHome(String userId) throws DataAccessException{
			Map<String, Object> map = jdbc.queryForMap(
					"SELECT U.user_name, A.punch, A.attendance_date "
					+ "FROM user_master U LEFT OUTER JOIN attendance_information A"
					+ "ON U.user_id = A.user_id");
			User user = new User();
			user.setUserName((String) map.get("user_name"));
			user.setPunch((int) map.get("punch"));
			user.setAttendanceDate((Date) map.get("attendance_date"));
			return user;
		}
	//attendance_informationテーブルの全データを取得
	@Override
	public List<User> selectManyFor() throws DataAccessException {
		
		List<Map<String, Object>> getList = jdbc.queryForList(
				"SELECT U.user_id, U.user_name, U.master,"
				+ " A.punch, A.attendance_date FROM"
				+ " user_master U LEFT OUTER JOIN attendance_information A"
				+ " ON U.user_id = A.user_id");
		List<User> userList = new ArrayList<>();
		for(Map<String, Object> map : getList) {
			User user = new User();
			user.setUserId((String) map.get("user_id"));
			user.setUserName((String) map.get("user_name"));
			user.setMaster((int) map.get("master"));
			user.setPunch((int) map.get("punch"));
			user.setAttendanceDate((Date) map.get("attendance_date"));
//			user.setStartTime((Timestamp) map.get("start_time"));
//			user.setEndTime((Timestamp) map.get("end_time"));
			userList.add(user);
		}
		return userList;
	}

	@Override
	public List<User> selectManyYear() throws DataAccessException {
		List<Map<String, Object>> getList = jdbc.queryForList(
				"SELECT U.user_id, U.user_name, U.master,"
						+ " A.punch, A.attendance_date FROM"
						+ " user_master U LEFT OUTER JOIN attendance_information A"
						+ " ON U.user_id = A.user_id"
						+ " WHERE attendance < yearmonth");
				List<User> userList = new ArrayList<>();
				for(Map<String, Object> map : getList) {
					User user = new User();
					user.setUserId((String) map.get("user_id"));
					user.setUserName((String) map.get("user_name"));
					user.setMaster((int) map.get("master"));
					user.setPunch((int) map.get("punch"));
					user.setAttendanceDate((Date) map.get("attendance_date"));
					userList.add(user);
				}
		return userList;
	}

}

//UserDaoインターフェースを実装したクラス