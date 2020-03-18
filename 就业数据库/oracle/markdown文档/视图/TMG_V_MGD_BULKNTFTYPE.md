# [勤怠/名称マスタ]一括登録種類(TMG_V_MGD_BULKNTFTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CBULKNTFTYPE|VARCHAR2||是|一括登録種類コード|
|MGD_CBULKNTFTYPENAME|NVARCHAR2||是|一括登録種類名称|
|MGD_CBULKNTFTYPENICKNM|NVARCHAR2||是|一括登録種類略称|
|MGD_CNTFTYPE|NVARCHAR2||是|作成する申請区分|
|MGD_CNTFTYPE_SPECIAL|NVARCHAR2||是|作成する申請区分(残日数不足時)|
|MGD_NSORT|NUMBER||是|ソート順|
