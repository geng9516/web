# 行動メモテーブル(HIST_MEMO)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HM_ID|NUMBER||否|IDカラム|
|HM_CCUSTOMERID|VARCHAR2||否|顧客コード|
|HM_CUSERID|VARCHAR2||否|ユーザID|
|HM_CCATEGORYID|VARCHAR2||是|カテゴリ区分|
|HM_CTITLE|NVARCHAR2||否|件名|
|HM_CMEMO|NVARCHAR2||是|内容|
|HM_DCREATEDDATE|DATE||是|作成日時|
|HM_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|HM_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
