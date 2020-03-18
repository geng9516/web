# メール定義マスタ(MAST_MAILINFO)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MM_ID|NUMBER||否|IDカラム|
|MM_CID|VARCHAR2||否|メール定義ID|
|MM_CMAILNAME|NVARCHAR2||否|メール定義名称|
|MM_CADDRESS|VARCHAR2||否|送信者アドレス|
|MM_CNAME|NVARCHAR2||否|送信者氏名|
|MM_CTITLE|NVARCHAR2||否|送信メール件名|
|MM_CCONTENT|NVARCHAR2||否|送信メールメッセージ|
|MM_CLANGUAGE|VARCHAR2||否|言語区分|
|MM_CDESC|NVARCHAR2||是|説明|
|MM_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MM_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
|MM_CMODULETYPE|VARCHAR2||是|モジュール区分|
