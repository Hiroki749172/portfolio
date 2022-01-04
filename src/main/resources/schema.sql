/* 利用者マスタテーブル */
CREATE TABLE IF NOT EXISTS user_master (
    user_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100),
    user_name VARCHAR(50),
    master VARCHAR(50)
);
/* アクセス履歴テーブル */
CREATE TABLE IF NOT EXISTS access_history (
    user_id VARCHAR(50),
    ip_address INT UNSIGNED,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

/* アクセス履歴管理者テーブル */
CREATE TABLE IF NOT EXISTS access_manege (
    user_id VARCHAR(50),
    ip_address INTEGER UNSIGNED,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

/* 勤怠情報テーブル */
CREATE TABLE IF NOT EXISTS attendance_information (
    user_id VARCHAR(50),
    punch BOOLEAN,
    attendance_date DATE,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    PRIMARY KEY(user_id, punch, attendance_date)
);