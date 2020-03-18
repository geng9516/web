# コンテンツ横断自由条件検索連携テーブル(HIST_SEARCH_COOP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HSC_ID|NUMBER||否|IDカラム|
|HSC_NDATA_ID|NUMBER||否|データID|
|HSC_CCUSTOMERID|VARCHAR2||否|顧客コード|
|HSC_CUSERID|VARCHAR2||否|ユーザID|
|HSC_CDATANAME|NVARCHAR2||否|データ名称|
|HSC_CPUBLIC|VARCHAR2||是|共有有無|
|HSC_CCOMMENT|NVARCHAR2||是|備考|
|HSC_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|HSC_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
