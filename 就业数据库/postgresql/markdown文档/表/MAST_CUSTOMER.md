# 顧客マスタ(MAST_CUSTOMER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MC_ID|numeric||否|IDカラム|
|MC_CCUSTOMERID_PK|varchar||是|顧客コード|
|MC_CCUSTOMERNAME|varchar||是|顧客名称|
|MC_CDELFLAG|varchar||否|削除フラグ|
|MC_DDELDATE|date||是|削除日付|
|MC_DCREATEDDATE|date||否|作成日付|
|MC_CLANGUAGE|varchar||是|言語区分|
|MC_CMODIFIERUSERID|varchar||是|最終更新者|
|MC_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
