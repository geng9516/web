# グループ別アプリケーション検索対象範囲設定テーブル(HIST_GROUPDATAPERMISSION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HGP_ID|NUMBER||否|IDカラム|
|HGP_CCUSTOMERID|VARCHAR2||否|顧客コード|
|HGP_COBJECTID|VARCHAR2||否|オブジェクトID|
|HGP_CSITEID|VARCHAR2||否|サイトID|
|HGP_CAPPID|VARCHAR2||是|アプリケーションID|
|HGP_CSYSTEMID|VARCHAR2||否|システムコード|
|HGP_CGROUPID|VARCHAR2||否|グループID|
|HGP_DSTARTDATE|DATE||否|データ開始日|
|HGP_DENDDATE|DATE||否|データ終了日|
|HGP_CPERMNECESSITY|VARCHAR2||是|必要条件定義ID|
|HGP_CPERMMUST|VARCHAR2||是|必須条件定義ID|
|HGP_CBASESECTION_FLAG_NEED|VARCHAR2||是|必要条件基点組織使用フラグ|
|HGP_CBASESECTION_FLAG_MUST|VARCHAR2||是|必須条件基点組織使用フラグ|
|HGP_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|HGP_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
|HGP_CPERM_RETIRED|VARCHAR2||是|退職者検索対象範囲|
