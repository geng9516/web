# 自由条件検索定義条件式データ(HIST_SEARCH_DEFINITIONS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HSD_ID|numeric||否|IDカラム|
|HSD_NSETTINGID|numeric||否|設定ID|
|HSD_NSEQ|numeric||是|行番号|
|HSD_CANDOR|varchar||是|論理演算子|
|HSD_COPENEDPARENTHSIS|varchar||是|カッコ|
|HSD_CTABLEID|varchar||是|テーブルID|
|HSD_CCOLUMNID|varchar||是|カラムID|
|HSD_CCOLUMNNAME|varchar||是|カラム名|
|HSD_CTYPEOFCOLUMN|varchar||是|データ型|
|HSD_COPERATOR|varchar||是|演算子|
|HSD_CVALUE|varchar||是|比較値|
|HSD_CDISPLAYVALUE|varchar||是|表示文字列|
|HSD_CCLOSEDPARENTHSIS|varchar||是|閉じカッコ|
|HSD_CMODIFIERUSERID|varchar||是|最終更新者|
|HSD_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
