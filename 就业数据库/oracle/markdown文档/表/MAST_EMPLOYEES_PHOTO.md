# 顔写真データ(MAST_EMPLOYEES_PHOTO)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MEP_ID|NUMBER||否|IDカラム|
|MEP_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MEP_CCOMPANYID|VARCHAR2||是|法人コード|
|MEP_CEMPLOYEEID|VARCHAR2||是|職員番号|
|MEP_CUSERID|VARCHAR2||是|ユーザID|
|MEP_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MEP_DMODIFIEDDATE|DATE||是|最終更新日|
|MEP_BATTACH|BLOB||是|顔写真データ|
|VERSIONNO|NUMBER||否|バージョンNo|
