# 汎用インターフェース　処理結果ログテーブル(TEMP_PSIF_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPIL_ID|numeric||否|IDカラム|
|TPIL_DEXEC|date||是|処理日付|
|TPIL_NSEQ|numeric||是|表示順|
|TPIL_DSTSMP|date||否|タイムスタンプ|
|TPIL_CPGMID|varchar||否|プログラムID|
|TPIL_CSTATUS|varchar||否|ステータス|
|TPIL_CCOMMENTS|varchar||是|コメント|
|TPIL_CMODIFIERUSERID|varchar||是|最終更新者|
|TPIL_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
