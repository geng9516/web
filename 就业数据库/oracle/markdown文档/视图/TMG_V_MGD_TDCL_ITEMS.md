# ロギング(項目別変更チェック仕様)マスタ(TMG_V_MGD_TDCL_ITEMS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_CITEMNM|NVARCHAR2||是|項目名称|
|MGD_CTABLEID|NVARCHAR2||是|テーブルID|
|MGD_CITEMID|NVARCHAR2||是|項目ID|
|MGD_CDIFFTYPE|NVARCHAR2||是|前後比較区分|
|MGD_CFORMAT|NVARCHAR2||是|表示形式区分|
|MGD_NORDER|NUMBER||是|表示順|
