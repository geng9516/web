# 機密レベルマスタ(MAST_SECURITY_LEVEL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MSL_ID|NUMBER||否|IDカラム|
|MSL_CCUSTOMERID|VARCHAR2||否|顧客コード|
|MSL_CSYSTEMID|VARCHAR2||否|システムコード|
|MSL_CLEVELID|VARCHAR2||否|機密レベルコード|
|MSL_DSTARTDATE|DATE||否|開始日|
|MSL_DENDDATE|DATE||否|終了日|
|MSL_CLEVELNAME|NVARCHAR2||否|機密レベル名称|
|MSL_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MSL_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
