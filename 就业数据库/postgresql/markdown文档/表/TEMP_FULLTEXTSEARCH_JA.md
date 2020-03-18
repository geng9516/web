# 全文検索対象データ(TEMP_FULLTEXTSEARCH_JA)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TFTSJA_ID|numeric||否|IDカラム|
|TFTSJA_CDOMAINID|varchar||否|ドメインID|
|TFTSJA_CCUSTOMERID|varchar||否|顧客コード|
|TFTSJA_CCOMPANYID|varchar||是|法人コード|
|TFTSJA_CTARGET|varchar||否|検索対象物|
|TFTSJA_CAPPLICATIONID|varchar||否|アプリケーションID|
|TFTSJA_CSUBAPPLICATIONID|varchar||是|サブアプリケーションID|
|TFTSJA_CSCREENID|varchar||是|画面ID|
|TFTSJA_CPERMALINK|varchar||否|PermaLink|
|TFTSJA_CDATA|text||否|検索対象データ|
|TFTSJA_CSRCTABLE|varchar||否|取得元テーブル|
|TFTSJA_CSRCID|numeric||否|取得元IDカラム|
|TFTSJA_CSRCCOLUMN|varchar||否|取得元カラム|
|TFTSJA_DMODIFIEDDATE|date||是|データ更新日|
