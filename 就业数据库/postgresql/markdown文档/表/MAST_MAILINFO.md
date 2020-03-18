# メール定義マスタ(MAST_MAILINFO)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MM_ID|numeric||否|IDカラム|
|MM_CID|varchar||否|メール定義ID|
|MM_CMAILNAME|varchar||否|メール定義名称|
|MM_CADDRESS|varchar||否|送信者アドレス|
|MM_CNAME|varchar||否|送信者氏名|
|MM_CTITLE|varchar||否|送信メール件名|
|MM_CCONTENT|varchar||否|送信メールメッセージ|
|MM_CLANGUAGE|varchar||否|言語区分|
|MM_CDESC|varchar||是|説明|
|MM_CMODIFIERUSERID|varchar||是|最終更新者|
|MM_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
|MM_CMODULETYPE|varchar||是|モジュール区分|
