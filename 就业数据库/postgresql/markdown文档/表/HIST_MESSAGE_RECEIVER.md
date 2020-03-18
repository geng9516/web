# メッセージ通知 メッセージ受信者情報テーブル(HIST_MESSAGE_RECEIVER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HMR_ID|numeric||否|IDカラム|
|HMR_NSENDER_ID|numeric||否|メッセージ送信者情報IDカラム|
|HMR_CCUSTOMERID|varchar||否|受信者顧客ID|
|HMR_CUSERID|varchar||否|受信者ユーザID|
|HMR_NREAD_FLAG|numeric||否|既読未読フラグ|
|HMR_NSENDMAILFLAG|numeric||否|送信フラグ|
|HMR_DSENDDATE|date||是|メール送信日時|
|HMR_CMODIFIERUSERID|varchar||是|最終更新者|
|HMR_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
