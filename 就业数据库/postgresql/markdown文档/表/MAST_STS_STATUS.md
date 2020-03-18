# ステータスマスタ(MAST_STS_STATUS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MST_ID|numeric||否|IDカラム|
|MST_CCUSTOMERID|varchar||是|顧客コード  |
|MST_CCOMPANYID|varchar||是|法人コード  |
|MST_CCONTENTID|varchar||是|コンテンツ識別  |
|MST_CSTATUSTYPEID|varchar||是|ステータス種別識別  |
|MST_CSTATUSID|varchar||是|ステータス識別  |
|MST_CSTATUSNAME|varchar||否|ステータス種別名称  |
|MST_CLANGUAGE|varchar||是|言語種別|
|MST_DSTARTDATE|date||是|開始日  |
|MST_DENDDATE|date||否|終了日  |
|MST_CMODIFIER|varchar||是|最終更新者  |
|MST_DMODIFY|date||是|最終更新日  |
|MST_NSEQ|numeric||是|表示順序|
|VERSIONNO|numeric||否|バージョンNo|
