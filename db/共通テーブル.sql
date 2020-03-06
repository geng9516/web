DROP TABLE IF EXISTS dev.t_access_audit;
CREATE TABLE dev.t_access_audit (
    audit_id bigserial,
    url varchar(100),
    method varchar(10),
    status int4,
    time int8,
    ip varchar(64),
    request_time timestamptz,
    response_time timestamptz,
    create_time timestamptz,
    update_time timestamptz,
    PRIMARY KEY (audit_id)
);
COMMENT ON TABLE dev.t_access_audit IS 'アクセスログ';
COMMENT ON COLUMN dev.t_access_audit.audit_id IS 'IDカラム';
COMMENT ON COLUMN dev.t_access_audit.url IS 'リクエストurl';
COMMENT ON COLUMN dev.t_access_audit.method IS 'リクエストタイプ';
COMMENT ON COLUMN dev.t_access_audit.status IS '状態コード';
COMMENT ON COLUMN dev.t_access_audit.time IS '実行時間（ミリ秒）';
COMMENT ON COLUMN dev.t_access_audit.ip IS 'IP地址';
COMMENT ON COLUMN dev.t_access_audit.request_time IS 'リクエスト開始時間';
COMMENT ON COLUMN dev.t_access_audit.response_time IS 'リクエスト終了時間';
COMMENT ON COLUMN dev.t_access_audit.create_time IS '作成時間';
COMMENT ON COLUMN dev.t_access_audit.update_time IS '更新時間';

DROP TABLE IF EXISTS dev.t_error_audit;
CREATE TABLE dev.t_error_audit (
   audit_id bigserial,
   url varchar(100),
   username varchar(16),
   called_method varchar(500),
   method varchar(10),
   params varchar(3000),
   ip varchar(64),
   user_agent varchar(500),
   message varchar(5000),
   create_time timestamptz,
   update_time timestamptz,
   primary key(audit_id)
);
COMMENT ON TABLE dev.t_error_audit IS 'エラーログマスタ';
COMMENT ON COLUMN dev.t_error_audit.audit_id IS 'IDカラム';
COMMENT ON COLUMN dev.t_error_audit.url IS 'リクエストurl';
COMMENT ON COLUMN dev.t_error_audit.username IS 'ユーザー名前';
COMMENT ON COLUMN dev.t_error_audit.called_method IS '呼び出されたメソッド';
COMMENT ON COLUMN dev.t_error_audit.method IS 'リクエスト方法';
COMMENT ON COLUMN dev.t_error_audit.params IS 'リクエストパラメータ';
COMMENT ON COLUMN dev.t_error_audit.ip IS 'ipアドレス';
COMMENT ON COLUMN dev.t_error_audit.user_agent IS 'ユーザーのブラウザ情報';
COMMENT ON COLUMN dev.t_error_audit.message IS 'エラー情報';
COMMENT ON COLUMN dev.t_error_audit.create_time IS '作成時間';
COMMENT ON COLUMN dev.t_error_audit.update_time IS '更新時間';

DROP TABLE IF EXISTS dev.t_login_audit;
CREATE TABLE dev.t_login_audit (
   audit_id bigserial,
   username varchar(16),
   operation varchar(50),
   status bool,
   ip varchar(64),
   user_agent varchar(500),
   create_time timestamptz,
   update_time timestamptz,
   primary key(audit_id)
);

COMMENT ON TABLE dev.t_login_audit IS 'ログインマスタ';
COMMENT ON COLUMN dev.t_login_audit.audit_id IS 'IDカラム';
COMMENT ON COLUMN dev.t_login_audit.username IS 'ユーザー名前';
COMMENT ON COLUMN dev.t_login_audit.operation IS 'ユーザー操作';
COMMENT ON COLUMN dev.t_login_audit.status IS '成功フラグ';
COMMENT ON COLUMN dev.t_login_audit.ip IS 'ipアドレス';
COMMENT ON COLUMN dev.t_login_audit.user_agent IS 'ユーザーのブラウザ情報';
COMMENT ON COLUMN dev.t_login_audit.create_time IS '作成時刻';
COMMENT ON COLUMN dev.t_login_audit.update_time IS '更新時刻';

DROP TABLE IF EXISTS dev.t_operation_audit;
CREATE TABLE dev.t_operation_audit (
   audit_id bigserial,
   username varchar(16),
   operation varchar(50),
   url varchar(100),
   method varchar(500),
   params varchar(3000),
   ip varchar(20),
   time int8,
   create_time timestamptz,
   update_time timestamptz,
   primary key(audit_id)
);

COMMENT ON TABLE dev.t_operation_audit IS '操作マスター';
COMMENT ON COLUMN dev.t_operation_audit.audit_id IS 'IDカラム';
COMMENT ON COLUMN dev.t_operation_audit.username IS 'ユーザー名前';
COMMENT ON COLUMN dev.t_operation_audit.operation IS 'ユーザー操作';
COMMENT ON COLUMN dev.t_operation_audit.url IS 'リクエストurl';
COMMENT ON COLUMN dev.t_operation_audit.method IS 'リクエスト方法';
COMMENT ON COLUMN dev.t_operation_audit.params IS 'リクエストパラメータ';
COMMENT ON COLUMN dev.t_operation_audit.ip IS 'ipアドレス';
COMMENT ON COLUMN dev.t_operation_audit.time IS '実行時間（ミリ秒）';
COMMENT ON COLUMN dev.t_login_audit.create_time IS '作成時刻';
COMMENT ON COLUMN dev.t_login_audit.update_time IS '更新時刻';

DROP TABLE IF EXISTS dev.t_session;
CREATE TABLE dev.t_session (
   session_id varchar(200),
   session_value varchar(3000),
   PRIMARY KEY (session_id)
);
COMMENT ON TABLE dev.t_session IS 'ユーザ会話';
COMMENT ON COLUMN dev.t_session.session_id IS 'sessionキー';
COMMENT ON COLUMN dev.t_session.session_value IS 'session値';
