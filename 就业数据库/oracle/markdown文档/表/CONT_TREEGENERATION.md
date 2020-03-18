# 静的組織ツリー生成状態データ(CONT_TREEGENERATION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CT_ID|NUMBER||否|IDカラム|
|CT_CSTATUS|VARCHAR2||是|ツリー生成状態|
|CT_DDATE|DATE||是|ツリー作成日時|
|CT_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|CT_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
