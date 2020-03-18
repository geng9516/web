# [勤怠/名称マスタ]決裁レベルマスタ(TMG_V_MGD_APPROVAL_LEVEL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_APPROVAL_LEVEL|VARCHAR2||是|決裁レベルコード|
|MGD_APPROVAL_LEVEL_NAME|NVARCHAR2||是|決裁レベル名称|
|MGD_NWEIGHTAGE|NUMBER||是|レベル順位|
