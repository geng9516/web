# 行動メモテーブル(HIST_MEMO)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HM_ID|numeric||否|IDカラム|
|HM_CCUSTOMERID|varchar||否|顧客コード|
|HM_CUSERID|varchar||否|ユーザID|
|HM_CCATEGORYID|varchar||是|カテゴリ区分|
|HM_CTITLE|varchar||否|件名|
|HM_CMEMO|varchar||是|内容|
|HM_DCREATEDDATE|date||是|作成日時|
|HM_CMODIFIERUSERID|varchar||是|最終更新者|
|HM_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
