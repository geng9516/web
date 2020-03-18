# 顧客マスタ(MAST_CUSTOMER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MC_ID|NUMBER||否|IDカラム|
|MC_CCUSTOMERID_PK|VARCHAR2||是|顧客コード|
|MC_CCUSTOMERNAME|NVARCHAR2||是|顧客名称|
|MC_CDELFLAG|VARCHAR2||否|削除フラグ|
|MC_DDELDATE|DATE||是|削除日付|
|MC_DCREATEDDATE|DATE||否|作成日付|
|MC_CLANGUAGE|VARCHAR2||是|言語区分|
|MC_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MC_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
