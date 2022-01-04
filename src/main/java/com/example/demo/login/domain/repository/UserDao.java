package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface UserDao {
	//DataAccessExceptionとはSpringではDB操作で例外が発生した場合、Springが提供しているDataAccessExceptionを投げる
	
	//user_masterテーブルの件数を取得
	public int count() throws DataAccessException;
	
	//user_masterテーブルにデータを1件insert
	public int insertOne(User user) throws DataAccessException;
	
	//user_masterテーブルのデータを1件取得
	public User selectOne(String userId) throws DataAccessException;
	
	//user_masterテーブルのパスワードを1件取得
	public User selectPass(String password) throws DataAccessException;
	
	//user_masterテーブルの全データを取得
	public List<User> selectMany() throws DataAccessException;
	
	//user_masterテーブルを1件更新
	public int updateOne(User user) throws DataAccessException;
	
	//user_masterテーブルを１件削除
	public int deleteOne(String userId) throws DataAccessException;
	
	//SQL取得結果をサーバーにCSVで保存する
	public void userCsvOut() throws DataAccessException;
	
	//access_historyテーブルにデータを1件insert
	public int insertTwo(User user) throws DataAccessException;
	
	//access_historyテーブルの全データを取得
	
	
	//access_manageテーブルにデータを1件insert
	public int insertThree(User user) throws DataAccessException;
	
	//attendance_informationテーブルの件数を取得
	public int count4() throws DataAccessException;
	
	//attendance_informationテーブルにデータを1件insert
	public int insertFor(User user)throws DataAccessException;
	
	//attendance_informationテーブルのデータを１件取得
	public User selectFor(String userId) throws DataAccessException;
	
	//attendance_informationテーブルの全データを取得
	public List<User> selectManyFor() throws DataAccessException;

}

/*リポジトリクラスのインターフェースを作る。
 * 理由は後で中身の実装クラスをJdbcTemplateとNamedParameterJdbcTemplateで
 * 簡単に切り替えられるようにするため
 */