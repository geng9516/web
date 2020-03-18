# アクセスログワーク(TEMP_ACCESSLOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TAL_ID|numeric||否|IDカラム|
|TAL_DTAKENDATE|date||否|ログファイル取込日時|
|TAL_CLOGFILEPATH|varchar||否|ログファイル取得元パス|
|TAL_DSTRDATE|date||否|開始日時|
|TAL_DENDDATE|date||否|終了日時|
|TAL_NTAKENTIME|numeric||否|所要時間|
|TAL_CIPADDRESS|varchar||否|IPアドレス|
|TAL_CLOGINUSERID|varchar||是|ログインユーザID|
|TAL_CSUBSTUSERID|varchar||是|代替元ユーザID|
|TAL_CTARGETUSERID|varchar||是|閲覧対象ユーザID|
|TAL_CLANGUAGE|varchar||是|ログイン言語区分|
|TAL_CSITEID|varchar||是|サイトID|
|TAL_CAPPID|varchar||是|アプリケーションID|
|TAL_CSUBAPPID|varchar||是|サブアプリケーションID|
|TAL_CSCREENID|varchar||是|画面ID|
|TAL_CPROGRAMNAME|varchar||是|実行メソッド|
|TAL_CPERMALINK|text||是|パーマリンク|
