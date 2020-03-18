# グループ別アプリケーション検索対象範囲設定テーブル(HIST_GROUPDATAPERMISSION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HGP_ID|numeric||否|IDカラム|
|HGP_CCUSTOMERID|varchar||否|顧客コード|
|HGP_COBJECTID|varchar||否|オブジェクトID|
|HGP_CSITEID|varchar||否|サイトID|
|HGP_CAPPID|varchar||是|アプリケーションID|
|HGP_CSYSTEMID|varchar||否|システムコード|
|HGP_CGROUPID|varchar||否|グループID|
|HGP_DSTARTDATE|date||否|データ開始日|
|HGP_DENDDATE|date||否|データ終了日|
|HGP_CPERMNECESSITY|varchar||是|必要条件定義ID|
|HGP_CPERMMUST|varchar||是|必須条件定義ID|
|HGP_CBASESECTION_FLAG_NEED|varchar||是|必要条件基点組織使用フラグ|
|HGP_CBASESECTION_FLAG_MUST|varchar||是|必須条件基点組織使用フラグ|
|HGP_CMODIFIERUSERID|varchar||是|最終更新者|
|HGP_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
|HGP_CPERM_RETIRED|varchar||是|退職者検索対象範囲|
