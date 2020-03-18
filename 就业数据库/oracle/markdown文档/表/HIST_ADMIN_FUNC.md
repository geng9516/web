# 管理ツール実行権限テーブル(HIST_ADMIN_FUNC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HAU_ID|NUMBER||否|IDカラム|
|HAU_CCUSTOMERID|VARCHAR2||是|顧客コード|
|HAU_CCOMPANYID|VARCHAR2||是|法人コード|
|HAU_CEMPLOYEEID|VARCHAR2||是|社員番号|
|HAU_CSYSTEMID|VARCHAR2||是|システム識別|
|HAU_CPERMISSION|VARCHAR2||是|実行権限|
|HAU_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|HAU_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
