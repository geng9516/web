# ステータス種別マスタ(MAST_STS_STSTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MSS_CSTATUSTYPEIDANOTHER|NVARCHAR2||是|ステータスタイプ識別（別名）|
|VERSIONNO|NUMBER||否|バージョンNo|
|MSS_ID|NUMBER||否|IDカラム|
|MSS_CCUSTOMERID|VARCHAR2||是|顧客コード  |
|MSS_CCOMPANYID|VARCHAR2||是|法人コード  |
|MSS_CCONTENTID|VARCHAR2||是|コンテンツ識別  |
|MSS_CSTATUSTYPEID|VARCHAR2||是|ステータスタイプ識別  |
|MSS_CSTATUSTYPENAME|NVARCHAR2||否|ステータスタイプ名称  |
|MSS_CLANGUAGE|VARCHAR2||是|言語種別|
|MSS_DSTARTDATE|DATE||是|開始日  |
|MSS_DENDDATE|DATE||否|終了日  |
|MSS_CMODIFIER|VARCHAR2||是|最終更新者  |
|MSS_DMODIFY|DATE||是|最終更新日  |
|MSS_CNAMEMASTUSE|VARCHAR2||是|名称マスタ使用区分|
