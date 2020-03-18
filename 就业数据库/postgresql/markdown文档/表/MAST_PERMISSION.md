# 機密設定マスタ(MAST_PERMISSION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MP_ID|numeric||否|IDカラム|
|MP_CCUSTOMERID|varchar||是|顧客コード|
|MP_CSYSTEMID|varchar||是|システムコード|
|MP_CDOMAINID|varchar||是|ドメインコード|
|MP_CGROUPID|varchar||是|グループコード|
|MP_CRELATIONSHIPID|varchar||是|リレーションシップID|
|MP_CLEVELID|varchar||是|機密レベルコード|
|MP_DSTARTDATE|date||是|開始日|
|MP_DENDDATE|date||否|終了日|
|MP_CMODIFIERUSERID|varchar||是|最終更新者|
|MP_DMODIFIEDDATE|date||是|最終更新日|
|MP_NPERMISSION|numeric||否|参照許可フラグ|
|MP_NREFUSAL|numeric||否|参照拒否フラグ|
|VERSIONNO|numeric||否|バージョンNo|
