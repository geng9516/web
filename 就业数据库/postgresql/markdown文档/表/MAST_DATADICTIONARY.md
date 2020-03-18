# データディクショナリマスタ(MAST_DATADICTIONARY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MD_ID|numeric||否|IDカラム|
|MD_CCUSTOMERID|varchar||是|顧客コード|
|MD_CTABLENAME|varchar||是|テーブルID|
|MD_NTABLESEQ|numeric||是|テーブル並び順|
|MD_CCOLUMNNAME|varchar||是|カラムID|
|MD_CCOLUMNDESC|varchar||是|カラム名称（別名用ダミー）|
|MD_CCOLUMNDESCJA|varchar||是|カラム名称（日本語）|
|MD_CCOLUMNDESCEN|varchar||是|カラム名称（英語）|
|MD_CCOLUMNDESCCH|varchar||是|カラム名称（中国語）|
|MD_CCOLUMNDESC01|varchar||是|カラム名称（予備１）|
|MD_CCOLUMNDESC02|varchar||是|カラム名称（予備２）|
|MD_CTYPEOFCOLUMN|varchar||是|カラムタイプ|
|MD_CEXCEPTEDDATATYPE|varchar||是|カラムタイプ|
|MD_NSEQ|numeric||是|並び順|
|MD_CLEVELID|varchar||是|セキュリティーレベル|
|MD_CMASTERTBLNAME|varchar||是|マスタテーブル区分|
|MD_CAVLFORAXESINCR|varchar||是|クロス集計軸使用フラグ|
|MD_CAVLFORCTNINCR|varchar||是|クロス集計集計項目フラグ|
|MD_CAVLFORCONDITIONINCR|varchar||是|クロス集計条件項目フラグ|
|MD_CAVLGROUPS|varchar||是|グループ定義フラグ|
|MD_CAVLFORCKSTART|varchar||是|条件検索使用フラグ|
|MD_CFULLTEXTFLG|varchar||是|全文検索用フラグ|
|MD_CRESULTAPPID|varchar||是|全文検索結果表示アプリケーション|
|MD_CRESULTSUBAPPID|varchar||是|全文検索結果表示サブアプリケーション|
|MD_CRESULTSCREENID|varchar||是|全文検索結果表示画面|
|MD_CPERMALINKKEYID|varchar||是|PermaLinkキー文字列|
|MD_CCALCULATECOLUMN|varchar||是|計算式|
|MD_CMASTTBLCOLUMN|varchar||是|（未使用）|
|MD_CMATCHESWITH|varchar||是|（未使用）|
|MD_CSRCID|varchar||是|取得元IDカラム|
|MD_CMODIFIERUSERID|varchar||是|最終更新者|
|MD_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
