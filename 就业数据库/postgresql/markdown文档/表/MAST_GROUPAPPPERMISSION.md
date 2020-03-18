# グループ別アプリケーション権限マスタ(MAST_GROUPAPPPERMISSION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGP_ID|numeric||否|IDカラム|
|MGP_CCOMPANYID|varchar||是|法人コード|
|MGP_CSYSTEMID|varchar||否|システムID|
|MGP_CGROUPID|varchar||否|グループコード|
|MGP_COBJECTID|varchar||否|オブジェクトID|
|MGP_CSITE|varchar||是|サイトID|
|MGP_CAPP|varchar||是|アプリケーションID|
|MGP_CSUBAPP|varchar||是|サブアプリケーションID|
|MGP_CBUTTON|varchar||是|ボタンID|
|MGP_CSCREEN|varchar||是|画面ID|
|MGP_CPERMISSION|varchar||否|実行権限|
|MGP_CREJECT|varchar||否|実行拒否設定|
|MGP_DSTARTDATE|date||否|開始日|
|MGP_DENDDATE|date||否|終了日|
|MGP_CMODIFIERUSERID|varchar||是|最終更新者|
|MGP_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
