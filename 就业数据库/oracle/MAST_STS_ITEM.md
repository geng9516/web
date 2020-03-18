# 項目マスタ(MAST_STS_ITEM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MSM_ID|NUMBER||否|IDカラム|
|MSM_CCUSTOMERID|VARCHAR2||是|顧客コード  |
|MSM_CCOMPANYID|VARCHAR2||是|法人コード  |
|MSM_CCONTENTID|VARCHAR2||是|コンテンツ識別  |
|MSM_CITEMID|VARCHAR2||是|項目識別  |
|MSM_CITEMNAME|NVARCHAR2||否|項目名称  |
|MSM_CITEMTYPE|VARCHAR2||否|項目種別|
|MSM_DSTARTDATE|DATE||是|開始日  |
|MSM_DENDDATE|DATE||否|終了日  |
|MSM_CMODIFIER|VARCHAR2||是|最終更新者  |
|MSM_DMODIFY|DATE||是|最終更新日  |
|MSM_NSEQ|NUMBER||是|表示順序|
|VERSIONNO|NUMBER||否|バージョンNo|
