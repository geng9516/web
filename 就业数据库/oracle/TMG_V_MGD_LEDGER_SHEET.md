# [勤怠/名称マスタ]帳票種別マスタ(TMG_V_MGD_LEDGER_SHEET)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|帳票種別マスタコード|
|MGD_LEDGER_SHEET_NAME|NVARCHAR2||是|帳票種別マスタ名称|
|MGD_LEDGER_SHEET_ID|NVARCHAR2||是|帳票種別識別ID|
|MGD_OUTPUT_TARGET_TYPE|NVARCHAR2||是|出力対象タイプ（個人系：EMP、組織系：ORG）|
|MGD_OUTPUT_TERM_TYPE|NVARCHAR2||是|出力期間タイプ（年：YEAR、年月：MONTH、期間：TERM）|
|MGD_TITLE|NVARCHAR2||是|タイトル|
|MGD_SORT|NUMBER||是|並び順|
|MGD_START_MONTH|NUMBER||是|年度開始月|
|MGD_PDF_DLFLG|VARCHAR2||是|PDFダウンロード表示制御フラグ|
|MGD_CSV_DLFLG|VARCHAR2||是|CSVダウンロード表示制御フラグ|
