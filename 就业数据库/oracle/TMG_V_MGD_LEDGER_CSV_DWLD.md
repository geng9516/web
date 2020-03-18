# [勤怠/名称マスタ]帳票CSVダウンロードマスタ(TMG_V_MGD_LEDGER_CSV_DWLD)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_LEDGER_SHEET_ID|VARCHAR2||否|帳票種別識別ID|
|MGD_CSV_DOWNLOAD_CODE|VARCHAR2||是|帳票CSVダウンロード設定コード|
|MGD_CSV_DOWNLOAD_NAME|NVARCHAR2||是|帳票CSVダウンロード設定名称|
|MGD_CLASS_FILE_NAME|NVARCHAR2||是|CSVダウンロードCLASSファイル名|
|MGD_DOWNLOAD_FILE_NAME|NVARCHAR2||是|ダウンロード時ファイル名|
