# メッセージ通知 メッセージファイル情報テーブル(HIST_MESSAGE_FILE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HMF_ID|numeric||否|IDカラム|
|HMF_NSENDER_ID|numeric||否|メッセージ送信者情報IDカラム|
|HMF_CNAME|varchar||否|名称|
|HMF_CCONTENT|bytea||否|内容|
|HMF_CMIMETYPE|varchar||否|MIMEタイプ|
|HMF_CMODIFIERUSERID|varchar||是|最終更新者|
|HMF_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
