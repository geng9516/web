# GUID管理テーブル(MAST_GUID)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MG_ID|NUMBER||否|IDカラム|
|MG_CUSERID|VARCHAR2||是|ユーザID|
|MG_CGUID|VARCHAR2||否|GUID|
|MG_DLASTUPDDATE|DATE||否|最終アクセス日時|
|MG_CCLIENTIP|VARCHAR2||否|クライアントIPアドレス|
|MG_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MG_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
