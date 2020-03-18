# [勤怠/名称マスタ]非番(TMG_V_MGD_OFF_DUTY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_COFFDUTYNAME|NVARCHAR2||是|非番名称|
|MGD_COFFDUTYWORKID|NVARCHAR2||是|非番就業区分|
