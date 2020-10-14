# 管理ツール実行権限テーブル(HIST_ADMIN_FUNC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HAU_ID|numeric||否|IDカラム|
|HAU_CCUSTOMERID|varchar||是|顧客コード|
|HAU_CCOMPANYID|varchar||是|法人コード|
|HAU_CEMPLOYEEID|varchar||是|職員番号|
|HAU_CSYSTEMID|varchar||是|システム識別|
|HAU_CPERMISSION|varchar||是|実行権限|
|HAU_CMODIFIERUSERID|varchar||是|最終更新者|
|HAU_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
