# グループ別基点組織マスタ(MAST_GROUPBASESECTION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGBS_ID|numeric||否|ＩＤカラム|
|MGBS_CCUSTOMERID|varchar||否|顧客コード|
|MGBS_CSYSTEMID|varchar||否|システムコード|
|MGBS_CGROUPID|varchar||否|グループID|
|MGBS_DSTARTDATE|date||否|開始日|
|MGBS_DENDDATE|date||否|終了日|
|MGBS_CCOMPANYID|varchar||否|法人コード|
|MGBS_CSECTIONID|varchar||否|組織コード|
|MGBS_CLAYEREDSECTIONID|varchar||否|組織階層コード|
|MGBS_CMODIFIERUSERID|varchar||是|最終更新者|
|MGBS_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
|MGBS_CBELOWORSINGLE|varchar||是|以下・のみフラグ|
