# メッセージ通知 メッセージ受信者情報テーブル(HIST_MESSAGE_RECEIVER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HMR_ID|NUMBER||否|IDカラム|
|HMR_NSENDER_ID|NUMBER||否|メッセージ送信者情報IDカラム|
|HMR_CCUSTOMERID|VARCHAR2||否|受信者顧客ID|
|HMR_CUSERID|VARCHAR2||否|受信者ユーザID|
|HMR_NREAD_FLAG|NUMBER||否|既読未読フラグ|
|HMR_NSENDMAILFLAG|NUMBER||否|送信フラグ|
|HMR_DSENDDATE|DATE||是|メール送信日時|
|HMR_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|HMR_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
