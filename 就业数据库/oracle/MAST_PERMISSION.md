# 機密設定マスタ(MAST_PERMISSION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MP_ID|NUMBER||否|IDカラム|
|MP_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MP_CSYSTEMID|VARCHAR2||是|システムコード|
|MP_CDOMAINID|VARCHAR2||是|ドメインコード|
|MP_CGROUPID|VARCHAR2||是|グループコード|
|MP_CRELATIONSHIPID|VARCHAR2||是|リレーションシップID|
|MP_CLEVELID|VARCHAR2||是|機密レベルコード|
|MP_DSTARTDATE|DATE||是|開始日|
|MP_DENDDATE|DATE||否|終了日|
|MP_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MP_DMODIFIEDDATE|DATE||是|最終更新日|
|MP_NPERMISSION|NUMBER||否|参照許可フラグ|
|MP_NREFUSAL|NUMBER||否|参照拒否フラグ|
|VERSIONNO|NUMBER||否|バージョンNo|
