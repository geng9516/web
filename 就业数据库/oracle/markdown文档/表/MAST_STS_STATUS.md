# ステータスマスタ(MAST_STS_STATUS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MST_ID|NUMBER||否|IDカラム|
|MST_CCUSTOMERID|VARCHAR2||是|顧客コード  |
|MST_CCOMPANYID|VARCHAR2||是|法人コード  |
|MST_CCONTENTID|VARCHAR2||是|コンテンツ識別  |
|MST_CSTATUSTYPEID|VARCHAR2||是|ステータス種別識別  |
|MST_CSTATUSID|VARCHAR2||是|ステータス識別  |
|MST_CSTATUSNAME|NVARCHAR2||否|ステータス種別名称  |
|MST_CLANGUAGE|VARCHAR2||是|言語種別|
|MST_DSTARTDATE|DATE||是|開始日  |
|MST_DENDDATE|DATE||否|終了日  |
|MST_CMODIFIER|VARCHAR2||是|最終更新者  |
|MST_DMODIFY|DATE||是|最終更新日  |
|MST_NSEQ|NUMBER||是|表示順序|
|VERSIONNO|NUMBER||否|バージョンNo|
