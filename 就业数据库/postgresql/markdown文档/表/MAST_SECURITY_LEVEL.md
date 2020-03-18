# 機密レベルマスタ(MAST_SECURITY_LEVEL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MSL_ID|numeric||否|IDカラム|
|MSL_CCUSTOMERID|varchar||否|顧客コード|
|MSL_CSYSTEMID|varchar||否|システムコード|
|MSL_CLEVELID|varchar||否|機密レベルコード|
|MSL_DSTARTDATE|date||否|開始日|
|MSL_DENDDATE|date||否|終了日|
|MSL_CLEVELNAME|varchar||否|機密レベル名称|
|MSL_CMODIFIERUSERID|varchar||是|最終更新者|
|MSL_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
