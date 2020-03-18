# [勤怠/名称マスタ]勤怠種別(TMG_V_MGD_TIME_TRUNC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_CMASTERCODENAME|NVARCHAR2||是|項目名|
|MGD_NTIME_TRUNC|NUMBER||是|打刻切捨て時間(分)|
