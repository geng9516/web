# GUID管理テーブル(MAST_GUID)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MG_ID|numeric||否|IDカラム|
|MG_CUSERID|varchar||是|ユーザID|
|MG_CGUID|varchar||否|GUID|
|MG_DLASTUPDDATE|date||否|最終アクセス日時|
|MG_CCLIENTIP|varchar||否|クライアントIPアドレス|
|MG_CMODIFIERUSERID|varchar||是|最終更新者|
|MG_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
