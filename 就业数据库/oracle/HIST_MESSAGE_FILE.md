# メッセージ通知 メッセージファイル情報テーブル(HIST_MESSAGE_FILE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HMF_ID|NUMBER||否|IDカラム|
|HMF_NSENDER_ID|NUMBER||否|メッセージ送信者情報IDカラム|
|HMF_CNAME|NVARCHAR2||否|名称|
|HMF_CCONTENT|BLOB||否|内容|
|HMF_CMIMETYPE|VARCHAR2||否|MIMEタイプ|
|HMF_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|HMF_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
