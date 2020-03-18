# 汎用インターフェース　処理結果ログテーブル(TEMP_PSIF_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPIL_ID|NUMBER||否|IDカラム|
|TPIL_DEXEC|DATE||是|処理日付|
|TPIL_NSEQ|NUMBER||是|表示順|
|TPIL_DSTSMP|DATE||否|タイムスタンプ|
|TPIL_CPGMID|VARCHAR2||否|プログラムID|
|TPIL_CSTATUS|VARCHAR2||否|ステータス|
|TPIL_CCOMMENTS|NVARCHAR2||是|コメント|
|TPIL_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|TPIL_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
