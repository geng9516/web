# ステータス種別マスタ(MAST_STS_STSTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MSS_ID|numeric||否|IDカラム|
|MSS_CCUSTOMERID|varchar||是|顧客コード  |
|MSS_CCOMPANYID|varchar||是|法人コード  |
|MSS_CCONTENTID|varchar||是|コンテンツ識別  |
|MSS_CSTATUSTYPEID|varchar||是|ステータスタイプ識別  |
|MSS_CSTATUSTYPENAME|varchar||否|ステータスタイプ名称  |
|MSS_CLANGUAGE|varchar||是|言語種別|
|MSS_DSTARTDATE|date||是|開始日  |
|MSS_DENDDATE|date||否|終了日  |
|MSS_CMODIFIER|varchar||是|最終更新者  |
|MSS_DMODIFY|date||是|最終更新日  |
|MSS_CNAMEMASTUSE|varchar||是|名称マスタ使用区分|
|MSS_CSTATUSTYPEIDANOTHER|varchar||是|ステータスタイプ識別（別名）|
|VERSIONNO|numeric||否|バージョンNo|
