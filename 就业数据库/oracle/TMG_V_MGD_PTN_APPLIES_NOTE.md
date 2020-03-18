# [勤怠/名称マスタ]勤務パターン設定適用注釈文言マスタ(TMG_V_MGD_PTN_APPLIES_NOTE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CPTNAPPLIESNOTEID|VARCHAR2||是|勤務パターン設定適用注釈文言コード|
|MGD_CPTNAPPLIESNOTENM|NVARCHAR2||是|勤務パターン設定適用注釈文言名称|
