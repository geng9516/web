# [勤怠/名称マスタ]出勤簿：休暇休職区分（略称）(TMG_V_MGD_YEARLYMSG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CTABLEID|NVARCHAR2||是|元テーブルID|
|MGD_CITEMTYPE|NVARCHAR2||是|休暇休職区分|
|MGD_CNICKNAME|NVARCHAR2||是|休暇休職名称（表示略称）|
|MGD_CITEMNAME|NVARCHAR2||是|休暇休職名称（正式）|
