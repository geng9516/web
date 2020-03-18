# 静的組織ツリー生成状態データ(CONT_TREEGENERATION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CT_ID|numeric||否|IDカラム|
|CT_CSTATUS|varchar||是|ツリー生成状態|
|CT_DDATE|date||是|ツリー作成日時|
|CT_CMODIFIERUSERID|varchar||是|最終更新者|
|CT_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
