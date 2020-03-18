# 役割関係マスタ(MAST_RELATIONSHIPDEFINITIONS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MRD_ID|numeric||否|IDカラム|
|MRD_CCUSTOMERID|varchar||是|顧客コード|
|MRD_CSYSTEMID|varchar||是|システムコード|
|MRD_CCONTENTTYPE|varchar||是|コンテンツ区分|
|MRD_DSTARTDATE|date||是|開始日|
|MRD_DENDDATE|date||是|終了日|
|MRD_CMODIFIERUSERID|varchar||是|最終更新者|
|MRD_DMODIFIEDDATE|date||是|最終更新日|
|MRD_CUSERID_FROM|varchar||是|検索者ユーザID|
|MRD_CUSERID_TO|varchar||是|被検索者ユーザID|
|MRD_CRELATIONSHIPID|varchar||是|リレーションID|
|MRD_CFIXED|varchar||是|確定フラグ|
|VERSIONNO|numeric||否|バージョンNo|
