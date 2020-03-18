# 汎用メール送信テーブル(MAST_SENDMAIL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MAS_ID|numeric||否|IDカラム|
|MAS_CID|varchar||是|メール定義ID|
|MAS_CMODIFIERUSERID|varchar||是|最終更新者|
|MAS_DMODIFIEDDATE|date||是|最終更新日|
|MAS_CFROMADDRESS|varchar||是|送信者アドレス|
|MAS_DSENDDATE|date||是|送信日時|
|MAS_CTOADDRESS|text||是|送信先アドレス(TO)|
|MAS_CCCADDRESS|text||是|送信先アドレス(CC)|
|MAS_CBCCADDRESS|text||是|送信先アドレス(BCC)|
|MAS_CTITLE|varchar||否|送信メール件名|
|MAS_CCONTENT|text||是|送信メール本文|
|MAS_CATTACHNAME|varchar||是|添付ファイル名|
|MAS_BATTACH|bytea||是|添付ファイル|
|MAS_NSEND|numeric||否|送信フラグ|
|VERSIONNO|numeric||否|バージョンNo|
