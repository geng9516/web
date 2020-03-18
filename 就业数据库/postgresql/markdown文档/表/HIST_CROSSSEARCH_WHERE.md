# クロス集計検索設定保存データ（検索条件定義）(HIST_CROSSSEARCH_WHERE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HSW_ID|numeric||否|IDカラム|
|HSW_CCUSTOMERID_CK|varchar||是|顧客コード|
|HSW_CCOMPANYID_CK|varchar||是|法人コード|
|HSW_CEMPLOYEEID_CK|varchar||是|社員番号|
|HSW_CFILENAME_CK|varchar||是|設定名称|
|HSW_CIFPUBLIC|varchar||是|共有フラグ|
|HSW_NSEQ|numeric||是|並び順|
|HSW_CITEMSEQ|varchar||否|カラム識別コード|
|HSW_CANDOR|varchar||否|AND/OR|
|HSW_CLPARENTHESIS|varchar||否|左カッコ|
|HSW_COPERATOR|varchar||否|比較演算子|
|HSW_CVALUE|varchar||否|比較値|
|HSW_CRPARENTHESIS|varchar||否|右カッコ|
|HSW_CMODIFIERUSERID|varchar||是|最終更新者|
|HSW_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
