# [勤怠/名称マスタ]年休繰越マスタ(TMG_V_MGD_CARRY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CARRY|VARCHAR2||是|年休繰越コード|
|MGD_CARRY_NAME|NVARCHAR2||是|年休繰越名称|
|MGD_CARRY_MODE|NUMBER||是|年休繰越モード|
|MGD_UNDER1YEAR_PAID_CHECK|NVARCHAR2||是|過去1年未満の付与情報判定フラグ|
