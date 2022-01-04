package com.example.demo.login.domain.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

//@DataでLombokでgetterやsetterを自動で作る
@Data
public class User {
	private String userId;
	private String password;
	private String userName;
	private String master;
	
	private Integer ipAddress;
	private Time loginTime;
	
	private boolean punch;
	private Date attendanceDate;
	private Timestamp startTime;
	private Timestamp endTime;
}



/*DBから取得した値を、コントローラークラスやサービスクラスなどの間でやり取りするためのクラス
 * 
 * */
 