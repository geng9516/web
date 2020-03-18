# コンテンツマスタ(MAST_STS_CONTENTS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MSC_ID|NUMBER||否|IDカラム|
|MSC_CCUSTOMERID|VARCHAR2||是|顧客コード  |
|MSC_CCOMPANYID|VARCHAR2||是|法人コード  |
|MSC_CCONTENTID|VARCHAR2||是|コンテンツ識別  |
|MSC_CCONTENTNAME|NVARCHAR2||否|コンテンツ名称  |
|MSC_DSTARTDATE|DATE||是|開始日  |
|MSC_DENDDATE|DATE||否|終了日  |
|MSC_CMODIFIER|VARCHAR2||是|最終更新者  |
|MSC_DMODIFY|DATE||是|最終更新日  |
|VERSIONNO|NUMBER||否|バージョンNo|
