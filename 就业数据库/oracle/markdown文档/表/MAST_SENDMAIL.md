# 汎用メール送信テーブル(MAST_SENDMAIL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MAS_ID|NUMBER||否|IDカラム|
|MAS_CID|VARCHAR2||是|メール定義ID|
|MAS_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MAS_DMODIFIEDDATE|DATE||是|最終更新日|
|MAS_CFROMADDRESS|NVARCHAR2||是|送信者アドレス|
|MAS_DSENDDATE|DATE||是|送信日時|
|MAS_CTOADDRESS|CLOB||是|送信先アドレス(TO)|
|MAS_CCCADDRESS|CLOB||是|送信先アドレス(CC)|
|MAS_CBCCADDRESS|CLOB||是|送信先アドレス(BCC)|
|MAS_CTITLE|NVARCHAR2||否|送信メール件名|
|MAS_CCONTENT|CLOB||是|送信メール本文|
|MAS_CATTACHNAME|NVARCHAR2||是|添付ファイル名|
|MAS_BATTACH|BLOB||是|添付ファイル|
|MAS_NSEND|NUMBER||否|送信フラグ|
|VERSIONNO|NUMBER||否|バージョンNo|
