# グループ定義条件式データ(HIST_GROUPDEFINITIONS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HGD_ID|numeric||否|IDカラム|
|HGD_CCUSTOMERID|varchar||是|顧客コード|
|HGD_CSYSTEMID|varchar||是|システムコード|
|HGD_CGROUPID|varchar||是|グループID|
|HGD_NSEQ|numeric||是|シーケンス番号|
|HGD_DSTARTDATE|date||是|データ開始日|
|HGD_DENDDATE|date||是|データ終了日|
|HGD_CCOMPANYID|varchar||是|法人コード|
|HGD_CANDOR|varchar||是|論理演算子|
|HGD_COPENEDPARENTHSIS|varchar||是|カッコ|
|HGD_CTABLEID|varchar||是|テーブルID|
|HGD_CCOLUMNID|varchar||是|カラムID|
|HGD_CCOLUMNNAME|varchar||是|カラム名|
|HGD_CTYPEOFCOLUMN|varchar||是|データ型|
|HGD_COPERATOR|varchar||是|演算子|
|HGD_CVALUE|varchar||是|比較値|
|HGD_CDISPLAYVALUE|varchar||是|表示文字列|
|HGD_CCLOSEDPARENTHSIS|varchar||是|閉じカッコ|
|HGD_CMODIFIERUSERID|varchar||是|最終更新者|
|HGD_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
