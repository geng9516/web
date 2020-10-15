# グループ定義条件マスタ（組織、役職）(MAST_GROUPSECTIONPOSTMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MAG_ID|numeric||否|IDカラム|
|MAG_CCUSTOMERID_CK_FK|varchar||否|顧客コード|
|MAG_CSYSTEMID_CK|varchar||否|システムコード|
|MAG_CGROUPID_FK|varchar||否|グループコード|
|MAG_DSTARTDATE_CK|date||是|開始日|
|MAG_DENDDATE|date||是|終了日|
|MAG_CTYPEID|varchar||否|定義区分|
|MAG_CCOMPANYID|varchar||否|法人コード|
|MAG_CSECTIONID|varchar||是|組織コード|
|MAG_CPOSTID|varchar||是|役職コード|
|MAG_CEMPLOYEEID|varchar||是|職員番号|
|MAG_CMODIFIERUSERID|varchar||是|最終更新者|
|MAG_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
