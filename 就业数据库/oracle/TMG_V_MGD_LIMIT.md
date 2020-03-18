# [勤怠/名称マスタ]閾値マスタ(TMG_V_MGD_LIMIT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード(連番)|
|MGD_CITEMNAME|NVARCHAR2||是|項目名|
|MGD_CERRMSG|NVARCHAR2||是|エラーメッセージ|
|MGD_CENABLED|NVARCHAR2||是|使用可否フラグ|
|MGD_NTHRESHOLD1|NUMBER||是|閾値1|
|MGD_NTHRESHOLD2|NUMBER||是|閾値2|
|MGD_NTHRESHOLD3|NUMBER||是|閾値3|
