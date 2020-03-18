# メッセージ通知 メッセージ送信情報テーブル(HIST_MESSAGE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HME_ID|numeric||否|IDカラム|
|HME_CCUSTOMERID|varchar||否|顧客コード|
|HME_CUSERID|varchar||是|送信者ユーザID|
|HME_CSITEID|varchar||是|サイトID|
|HME_CAPPID|varchar||否|コンテンツID|
|HME_DRELEASEDATE|date||否|公開日|
|HME_CTITLEJA|varchar||否|送信件名（日本語）|
|HME_CCONTENTJA|text||否|送信内容（日本語）|
|HME_CURL|text||是|表示url|
|HME_CNOTIFICATION_TYPE|varchar||是|通知種別|
|HME_NSUMMAILFLAG|numeric||否|送信内容まとめフラグ|
|HME_CFROMADDRESSJA|varchar||是|送信元メールアドレス（日本語）|
|HME_CMODIFIERUSERID|varchar||是|最終更新者|
|HME_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
|HME_CTITLEEN|varchar||是|送信件名（英語）|
|HME_CTITLECH|varchar||是|送信件名（中国語）|
|HME_CTITLE01|varchar||是|送信件名（予備１）|
|HME_CTITLE02|varchar||是|送信件名（予備２）|
|HME_CCONTENTEN|text||是|送信内容（英語）|
|HME_CCONTENTCH|text||是|送信内容（中国語）|
|HME_CCONTENT01|text||是|送信内容（予備１）|
|HME_CCONTENT02|text||是|送信内容（予備２）|
|HME_CFROMADDRESSEN|varchar||是|送信元メールアドレス（英語）|
|HME_CFROMADDRESSCH|varchar||是|送信元メールアドレス（中国語）|
|HME_CFROMADDRESS01|varchar||是|送信元メールアドレス（予備１）|
|HME_CFROMADDRESS02|varchar||是|送信元メールアドレス（予備２）|
