# グループ定義条件マスタ(MAST_GROUPDEFINITIONS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGP_ID|NUMBER||否|IDカラム|
|MGP_CCUSTOMERID_CK_FK|VARCHAR2||是|顧客コード|
|MGP_CSYSTEMID_CK|VARCHAR2||是|システムコード|
|MGP_CGROUPID_CK_FK|VARCHAR2||是|グループコード|
|MGP_DSTARTDATE|DATE||是|開始日|
|MGP_DENDDATE|DATE||是|終了日|
|MGP_BASEFLAG|VARCHAR2||否|グループ定義種別|
|MGP_CQUERY|CLOB||是|グループ判定SQL|
|MGP_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MGP_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
