# 静的組織ツリーファイルマスタ(HIST_ORGANISATION_TREEFILES)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HOT_ID|NUMBER||否|IDカラム|
|HOT_CCUSTOMERID_CK|VARCHAR2||是|顧客コード|
|HOT_CCOMPANYID_CK|VARCHAR2||是|法人コード|
|HOT_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|HOT_DSTARTDATE_CK|DATE||是|開始日|
|HOT_DENDDATE|DATE||否|終了日|
|HOT_CFILENAME|VARCHAR2||否|ファイル名称|
|HOT_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|HOT_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
