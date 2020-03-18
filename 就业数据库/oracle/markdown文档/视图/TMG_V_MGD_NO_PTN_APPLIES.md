# [勤怠/名称マスタ]勤務パターンを適用しない文言マスタ(TMG_V_MGD_NO_PTN_APPLIES)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CNOPTNAPPLIESID|VARCHAR2||是|勤務パターンを適用しない文言コード|
|MGD_CNOPTNAPPLIESNM|NVARCHAR2||是|勤務パターンを適用しない文言名称|
