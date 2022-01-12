/* ユーザーマスタのデータ（ADMIN権限） */
INSERT INTO user_master (user_id, password, user_name, master, role)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田太郎', 1, 'ROLE_ADMIN');

 /*ユーザーマスタのデータ（一般権限） */
INSERT INTO user_master (user_id, password, user_name, master, role)
VALUES('tamura@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '田村', 0, 'ROLE_GENERAL');

INSERT INTO attendance_information (user_id, punch, attendance_date)
VALUES ('yamada@xxx.co.jp', 1, TO_DATE('19-01-01 10-00-00', 'YY-MM-DD HH24:MI:SS'));

INSERT INTO attendance_information (user_id, punch, attendance_date)
VALUES ('tamura@xxx.co.jp', 0, '2022-01-07 10:00:00');