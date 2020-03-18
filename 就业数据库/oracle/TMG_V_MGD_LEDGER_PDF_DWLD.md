# [勤怠/名称マスタ]帳票PDFダウンロードマスタ(TMG_V_MGD_LEDGER_PDF_DWLD)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_LEDGER_SHEET_ID|VARCHAR2||否|帳票種別識別ID|
|MGD_PDF_DOWNLOAD_CODE|VARCHAR2||是|帳票PDFダウンロード設定コード|
|MGD_PDF_DOWNLOAD_NAME|NVARCHAR2||是|帳票PDFダウンロード設定名称|
|MGD_JASPER_FILE_PATH|NVARCHAR2||是|JASPERファイルパス|
|MGD_DOWNLOAD_FILE_NAME|NVARCHAR2||是|ダウンロード時ファイル名|
