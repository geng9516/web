# 検索対象範囲条件定義マスタ(条件式)(MAST_DATAPERMISSIONDEFS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MDPD_ID|numeric||否|IDカラム|
|MDPD_CPERMISSIONID|varchar||否|定義ID|
|MDPD_NSEQ|numeric||否|シーケンス番号|
|MDPD_CANDOR|varchar||是|論理演算子|
|MDPD_COPENEDPARENTHSIS|varchar||是|左カッコ|
|MDPD_CTABLEID|varchar||是|テーブルID|
|MDPD_CCOLUMNID|varchar||是|カラムID|
|MDPD_CCOLUMNNAME|varchar||是|カラム名|
|MDPD_CTYPEOFCOLUMN|varchar||是|データ型|
|MDPD_COPERATOR|varchar||是|比較演算子|
|MDPD_CCOMPANYID|varchar||是|法人コード|
|MDPD_CVALUE|varchar||是|比較値|
|MDPD_CDISPLAYVALUE|varchar||是|表示文字列|
|MDPD_CMYFLAG|varchar||是|自分のフラグ|
|MDPD_CCLOSEDPARENTHSIS|varchar||是|右カッコ|
|MDPD_CMODIFIERUSERID|varchar||是|最終更新者|
|MDPD_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
