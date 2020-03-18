# 法人ツリーマスタ(MAST_COMPANY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MAC_ID|numeric||否|IDカラム|
|MAC_CCUSTOMERID_CK_FK|varchar||是|顧客コード|
|MAC_CCOMPANYID_CK|varchar||是|法人コード|
|MAC_CLAYEREDCOMPANYID|varchar||否|法人階層コード|
|MAC_CPARENTID|varchar||是|上位法人コード|
|MAC_NLEVEL|numeric||否|階層レベル|
|MAC_NSEQ|numeric||否|行|
|MAC_CCOMPANYNAME|varchar||是|法人名称|
|MAC_CCOMPANYNAMEJA|varchar||是|法人名称（日本語）|
|MAC_CCOMPANYNAMEEN|varchar||是|法人名称（英語）|
|MAC_CCOMPANYNAMECH|varchar||是|法人名称（中国語）|
|MAC_CCOMPANYNAME01|varchar||是|法人名称（予備１）|
|MAC_CCOMPANYNAME02|varchar||是|法人名称（予備２）|
|MAC_CCOMPANYNICK|varchar||是|法人略称（通称）|
|MAC_CCOMPANYNICKJA|varchar||是|法人略称（通称_日本語）|
|MAC_CCOMPANYNICKEN|varchar||是|法人略称（通称_英語）|
|MAC_CCOMPANYNICKCH|varchar||是|法人略称（通称_中国語）|
|MAC_CCOMPANYNICK01|varchar||是|法人略称（通称_予備１）|
|MAC_CCOMPANYNICK02|varchar||是|法人略称（通称_予備２）|
|MAC_CLANGUAGE|varchar||是|言語区分|
|MAC_DSTART|date||否|データ開始日|
|MAC_DEND|date||是|データ終了日|
|MAC_CDELFLAG|varchar||是|削除フラグ|
|MAC_DDELDATE|date||是|削除日付|
|MAC_CMODIFIERUSERID|varchar||是|最終更新者|
|MAC_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
