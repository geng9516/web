# クロス集計検索設定保存データ(HIST_CROSSSEARCH)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HCS_ID|numeric||否|IDカラム|
|HCS_NSETTINGID|numeric||否|設定ID|
|HCS_CCUSTOMERID_CK|varchar||否|顧客コード|
|HCS_CCOMPANYID_CK|varchar||否|法人コード|
|HCS_CUSERID|varchar||否|ユーザID|
|HCS_CFILENAME|varchar||否|設定名|
|HCS_CCOMMENT|varchar||是|備考|
|HCS_CIFPUBLIC|varchar||是|共有フラグ|
|HCS_CIGNORECASE|varchar||是|大文字小文字判定有無|
|HCS_CNODATANOOUTPUT|varchar||是|データのある行のみ表示|
|HCS_CMODIFIERUSERID|varchar||是|最終更新者|
|HCS_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
