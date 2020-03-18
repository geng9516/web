# グループ定義条件マスタ(MAST_GROUPDEFINITIONS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGP_ID|numeric||否|IDカラム|
|MGP_CCUSTOMERID_CK_FK|varchar||是|顧客コード|
|MGP_CSYSTEMID_CK|varchar||是|システムコード|
|MGP_CGROUPID_CK_FK|varchar||是|グループコード|
|MGP_DSTARTDATE|date||是|開始日|
|MGP_DENDDATE|date||是|終了日|
|MGP_BASEFLAG|varchar||否|グループ定義種別|
|MGP_CQUERY|text||是|グループ判定SQL|
|MGP_CMODIFIERUSERID|varchar||是|最終更新者|
|MGP_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
