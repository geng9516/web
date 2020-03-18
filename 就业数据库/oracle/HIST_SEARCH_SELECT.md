# 自由条件検索SELECT句テーブル(HIST_SEARCH_SELECT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HSS_ID|NUMBER||否|IDカラム|
|HSS_NSETTINGID|NUMBER||否|設定ID|
|HSS_NSEQ|NUMBER||否|行番号|
|HSS_CCOLUMN|VARCHAR2||否|カラムID|
|HSS_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|HSS_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
