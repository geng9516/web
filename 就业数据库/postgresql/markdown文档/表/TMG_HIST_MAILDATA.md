# メール送信テーブル(TMG_HIST_MAILDATA)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THMD_ID|numeric||否|IDカラム|
|THMD_CCUSTOMERID|varchar||否|顧客コード|
|THMD_CCOMPANYID|varchar||否|法人コード|
|THMD_CMODIFIERUSERID|varchar||是|最終更新者|
|THMD_DMODIFIEDDATE|date||是|最終更新日|
|THMD_CMODIFIERPROGRAMID|varchar||是|最終更新プログラムID|
|THMD_CEVENTID|varchar||是|イベントID|
|THMD_CID|varchar||是|メール定義ID|
|THMD_CEMPLOYEEID_RECEIVE|varchar||是|メールを受け取る職員番号|
|THMD_CEMPLOYEEID_TARGET|varchar||是|対象の職員番号。超勤の職員。日次・月次未承認はNULL、申請は申請者|
|THMD_DYYYYMMDD|date||是|基準日|
|THMD_DCREATE|date||是|作成日時|
|THMD_CFROMADDRESS|varchar||是|差出人アドレス（ＦＲＯＭ）|
|THMD_CTOADDRESS|varchar||是|送信先アドレス（ＴＯ）|
|THMD_CTITLE|varchar||是|送信メール件名|
|THMD_CCONTENT|text||是|送信メール本文|
|THMD_DSEND|date||是|送信日時|
|THMD_NSEND_STATUS|numeric||否|送信ステータス ０：未送信　１：送信済　２：送信エラー|
|VERSIONNO|numeric||否|バージョンNo|
