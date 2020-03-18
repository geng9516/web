# 静的組織ツリーファイルマスタ(HIST_ORGANISATION_TREEFILES)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HOT_ID|numeric||否|IDカラム|
|HOT_CCUSTOMERID_CK|varchar||是|顧客コード|
|HOT_CCOMPANYID_CK|varchar||是|法人コード|
|HOT_CLANGUAGE_CK|varchar||是|言語区分|
|HOT_DSTARTDATE_CK|date||是|開始日|
|HOT_DENDDATE|date||否|終了日|
|HOT_CFILENAME|varchar||否|ファイル名称|
|HOT_CMODIFIERUSERID|varchar||是|最終更新者|
|HOT_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
