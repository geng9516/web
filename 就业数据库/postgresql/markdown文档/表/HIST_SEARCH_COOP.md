# コンテンツ横断自由条件検索連携テーブル(HIST_SEARCH_COOP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HSC_ID|numeric||否|IDカラム|
|HSC_NDATA_ID|numeric||否|データID|
|HSC_CCUSTOMERID|varchar||否|顧客コード|
|HSC_CUSERID|varchar||否|ユーザID|
|HSC_CDATANAME|varchar||否|データ名称|
|HSC_CPUBLIC|varchar||是|共有有無|
|HSC_CCOMMENT|varchar||是|備考|
|HSC_CMODIFIERUSERID|varchar||是|最終更新者|
|HSC_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
