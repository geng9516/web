# 自由条件検索WHERE句テーブル(HIST_SEARCH_WHERE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HSW_ID|NUMBER||否|IDカラム|
|HSW_NSETTINGID|NUMBER||否|設定ID|
|HSW_CTABLE|VARCHAR2||否|テーブルID|
|HSW_CCOLUMN|VARCHAR2||否|カラムID|
|HSW_CVALUE|NVARCHAR2||否|値|
|HSW_CUSE|VARCHAR2||是|条件使用有無|
|HSW_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|HSW_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
