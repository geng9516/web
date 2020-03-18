# 顔写真データ(MAST_EMPLOYEES_PHOTO)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MEP_ID|numeric||否|IDカラム|
|MEP_CCUSTOMERID|varchar||是|顧客コード|
|MEP_CCOMPANYID|varchar||是|法人コード|
|MEP_CEMPLOYEEID|varchar||是|社員番号|
|MEP_CUSERID|varchar||是|ユーザID|
|MEP_CMODIFIERUSERID|varchar||是|最終更新者|
|MEP_DMODIFIEDDATE|date||是|最終更新日|
|MEP_BATTACH|bytea||是|顔写真データ|
|VERSIONNO|numeric||否|バージョンNo|
