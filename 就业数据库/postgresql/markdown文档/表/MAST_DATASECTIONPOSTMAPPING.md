# 検索対象範囲条件定義マスタ(組織、役職)(MAST_DATASECTIONPOSTMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MDSP_ID|numeric||否|IDカラム|
|MDSP_CPERMISSIONID|varchar||否|定義ID|
|MDSP_CTYPEID|varchar||否|定義区分|
|MDSP_CCOMPANYID|varchar||否|法人コード|
|MDSP_CSECTIONID|varchar||是|組織コード|
|MDSP_CPOSTID|varchar||是|役職コード|
|MDSP_CEMPLOYEEID|varchar||是|社員番号|
|MDSP_CMODIFIERUSERID|varchar||是|最終更新者|
|MDSP_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
