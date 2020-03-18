# [勤怠/名称マスタ]遡及CSVレイアウトマスタ(TMG_V_MGD_UPDS_WORKER2COLS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CWORKERTYPEID|NVARCHAR2||是|勤怠種別コード|
|MGD_CDAILYRATETYPE|NVARCHAR2||是|日割種別（"BEFORE"or"AFTER"）|
|MGD_CCOLUMNID|NVARCHAR2||是|カラムID|
