package com.example.demo.login.domain.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

//@DataでLombokでgetterやsetterを自動で作る
@Data
public class User {
	private String userId;
	private String password;
	private String userName;
	private int master;
	private String role;
	
	private Integer ipAddress;
	private Time loginTime;
	
	private int punch;
	private Date attendanceDate;
	private Timestamp startTime;
	private Timestamp endTime;
	private LocalDateTime nowDate = LocalDateTime.now();
	
	private Date yearmonth;
}



/*DBから取得した値を、コントローラークラスやサービスクラスなどの間でやり取りするためのクラス
 * 
 * */
 