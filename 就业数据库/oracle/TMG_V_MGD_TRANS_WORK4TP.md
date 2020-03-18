# [勤怠/名称マスタ]休日打刻就業区分変換マスタ(TMG_V_MGD_TRANS_WORK4TP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CWORKINGID_P|NVARCHAR2||是|変換前就業区分|
|MGD_CWORKINGID_R|NVARCHAR2||是|変換後就業区分|
