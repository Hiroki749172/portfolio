/* ユーザーマスタのデータ（ADMIN権限） */
INSERT INTO user_master (user_id, password, user_name, master)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田太郎', 'ROLE_ADMIN');

 /*ユーザーマスタのデータ（一般権限） */
INSERT INTO user_master (user_id, password, user_name, master)
VALUES('tamura@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '田村', 'ROLE_GENERAL');

INSERT INTO attendance_information (user_id, punch, attendance_date)
VALUES ('yamada@xxx.co.jp', true, '1990-01-01');