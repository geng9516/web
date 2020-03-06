DROP TABLE IF EXISTS dev.t_schedule_job;
CREATE TABLE dev.t_schedule_job (
    job_id bigserial,
    bean_name varchar(200),
    params varchar(2000),
    cron_expression varchar(100),
    status int4,
    remark varchar(100),
    create_time timestamptz,
    update_time timestamptz,
    PRIMARY KEY (job_id)
);

COMMENT ON TABLE dev.t_schedule_job IS '任務マスター';
COMMENT ON COLUMN dev.t_schedule_job.job_id IS '任務id';
COMMENT ON COLUMN dev.t_schedule_job.bean_name IS 'spring bean名前';
COMMENT ON COLUMN dev.t_schedule_job.params IS 'パラメータ';
COMMENT ON COLUMN dev.t_schedule_job.cron_expression IS 'cron式';
COMMENT ON COLUMN dev.t_schedule_job.status IS '任務状態　１　ノーマル  0：一時停止';
COMMENT ON COLUMN dev.t_schedule_job.remark IS 'リマーク';
COMMENT ON COLUMN dev.t_schedule_job.create_time IS '作成時刻';
COMMENT ON COLUMN dev.t_schedule_job.update_time IS '更新時刻';

DROP TABLE IF EXISTS dev.t_schedule_job_log;
CREATE TABLE dev.t_schedule_job_log (
    log_id bigserial,
    job_id int8,
    bean_name varchar(200),
    params varchar(3000),
    status bool,
    error varchar(4000),
    time int8,
    create_time timestamptz,
    update_time timestamptz,
    PRIMARY KEY (log_id)
);

COMMENT ON TABLE dev.t_schedule_job_log IS '任務ログ';
COMMENT ON COLUMN dev.t_schedule_job_log.log_id IS '任務ログID';
COMMENT ON COLUMN dev.t_schedule_job_log.job_id IS '任務ID';
COMMENT ON COLUMN dev.t_schedule_job_log.bean_name IS 'spring bean名前';
COMMENT ON COLUMN dev.t_schedule_job_log.params IS 'リクエストパラメータ';
COMMENT ON COLUMN dev.t_schedule_job_log.status IS '任務状態 true：成功 false：失敗';
COMMENT ON COLUMN dev.t_schedule_job_log.error IS '失敗情報';
COMMENT ON COLUMN dev.t_schedule_job_log.time IS '実行時間（ミリ秒）';
COMMENT ON COLUMN dev.t_schedule_job_log.create_time IS '作成時刻';
COMMENT ON COLUMN dev.t_schedule_job_log.update_time IS '更新時刻';

DROP TABLE IF EXISTS dev.qrtz_job_details;
CREATE TABLE dev.qrtz_job_details (
    SCHED_NAME VARCHAR(120) NOT NULL,
    JOB_NAME  VARCHAR(200) NOT NULL,
    JOB_GROUP VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(250) NULL,
    JOB_CLASS_NAME   VARCHAR(250) NOT NULL,
    IS_DURABLE BOOL NOT NULL,
    IS_NONCONCURRENT BOOL NOT NULL,
    IS_UPDATE_DATA BOOL NOT NULL,
    REQUESTS_RECOVERY BOOL NOT NULL,
    JOB_DATA BYTEA NULL,
    PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);

DROP TABLE IF EXISTS dev.qrtz_triggers;
CREATE TABLE dev.qrtz_triggers (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    JOB_NAME  VARCHAR(200) NOT NULL,
    JOB_GROUP VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(250) NULL,
    NEXT_FIRE_TIME BIGINT NULL,
    PREV_FIRE_TIME BIGINT NULL,
    PRIORITY INTEGER NULL,
    TRIGGER_STATE VARCHAR(16) NOT NULL,
    TRIGGER_TYPE VARCHAR(8) NOT NULL,
    START_TIME BIGINT NOT NULL,
    END_TIME BIGINT NULL,
    CALENDAR_NAME VARCHAR(200) NULL,
    MISFIRE_INSTR SMALLINT NULL,
    JOB_DATA BYTEA NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

DROP TABLE IF EXISTS dev.qrtz_simple_triggers;
CREATE TABLE dev.qrtz_simple_triggers (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    REPEAT_COUNT BIGINT NOT NULL,
    REPEAT_INTERVAL BIGINT NOT NULL,
    TIMES_TRIGGERED BIGINT NOT NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

DROP TABLE IF EXISTS dev.qrtz_cron_triggers;
CREATE TABLE dev.qrtz_cron_triggers (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    CRON_EXPRESSION VARCHAR(120) NOT NULL,
    TIME_ZONE_ID VARCHAR(80),
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

DROP TABLE IF EXISTS dev.qrtz_simprop_triggers;
CREATE TABLE dev.qrtz_simprop_triggers (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT NULL,
    INT_PROP_2 INT NULL,
    LONG_PROP_1 BIGINT NULL,
    LONG_PROP_2 BIGINT NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 BOOL NULL,
    BOOL_PROP_2 BOOL NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

DROP TABLE IF EXISTS dev.qrtz_blob_triggers;
CREATE TABLE dev.qrtz_blob_triggers (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    BLOB_DATA BYTEA NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

DROP TABLE IF EXISTS dev.qrtz_calendars;
CREATE TABLE dev.qrtz_calendars (
    SCHED_NAME VARCHAR(120) NOT NULL,
    CALENDAR_NAME  VARCHAR(200) NOT NULL,
    CALENDAR BYTEA NOT NULL,
    PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);

DROP TABLE IF EXISTS dev.qrtz_paused_trigger_grps;
CREATE TABLE dev.qrtz_paused_trigger_grps (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_GROUP  VARCHAR(200) NOT NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);

DROP TABLE IF EXISTS dev.qrtz_fired_triggers;
CREATE TABLE dev.qrtz_fired_triggers (
    SCHED_NAME VARCHAR(120) NOT NULL,
    ENTRY_ID VARCHAR(95) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    INSTANCE_NAME VARCHAR(200) NOT NULL,
    FIRED_TIME BIGINT NOT NULL,
    SCHED_TIME BIGINT NOT NULL,
    PRIORITY INTEGER NOT NULL,
    STATE VARCHAR(16) NOT NULL,
    JOB_NAME VARCHAR(200) NULL,
    JOB_GROUP VARCHAR(200) NULL,
    IS_NONCONCURRENT BOOL NULL,
    REQUESTS_RECOVERY BOOL NULL,
    PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);

DROP TABLE IF EXISTS dev.qrtz_scheduler_state;
CREATE TABLE dev.qrtz_scheduler_state (
    SCHED_NAME VARCHAR(120) NOT NULL,
    INSTANCE_NAME VARCHAR(200) NOT NULL,
    LAST_CHECKIN_TIME BIGINT NOT NULL,
    CHECKIN_INTERVAL BIGINT NOT NULL,
    PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);

DROP TABLE IF EXISTS dev.qrtz_locks;
CREATE TABLE dev.qrtz_locks (
    SCHED_NAME VARCHAR(120) NOT NULL,
    LOCK_NAME  VARCHAR(40) NOT NULL,
    PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on dev.qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on dev.qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on dev.qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on dev.qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on dev.qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on dev.qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on dev.qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on dev.qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on dev.qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on dev.qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on dev.qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on dev.qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on dev.qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on dev.qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on dev.qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on dev.qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on dev.qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on dev.qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on dev.qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on dev.qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);
