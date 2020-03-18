# メール送信テーブル(TMG_HIST_MAILDATA)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THMD_ID|NUMBER||否|IDカラム|
|THMD_CCUSTOMERID|VARCHAR2||否|顧客コード|
|THMD_CCOMPANYID|VARCHAR2||否|法人コード|
|THMD_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|THMD_DMODIFIEDDATE|DATE||是|最終更新日|
|THMD_CMODIFIERPROGRAMID|VARCHAR2||是|最終更新プログラムID|
|THMD_CEVENTID|VARCHAR2||是|イベントID|
|THMD_CID|VARCHAR2||是|メール定義ID|
|THMD_CEMPLOYEEID_RECEIVE|VARCHAR2||是|メールを受け取る職員番号|
|THMD_CEMPLOYEEID_TARGET|VARCHAR2||是|対象の職員番号。超勤の職員。日次・月次未承認はNULL、申請は申請者|
|THMD_DYYYYMMDD|DATE||是|基準日|
|THMD_DCREATE|DATE||是|作成日時|
|THMD_CFROMADDRESS|VARCHAR2||是|差出人アドレス（ＦＲＯＭ）|
|THMD_CTOADDRESS|VARCHAR2||是|送信先アドレス（ＴＯ）|
|THMD_CTITLE|NVARCHAR2||是|送信メール件名|
|THMD_CCONTENT|CLOB||是|送信メール本文|
|THMD_DSEND|DATE||是|送信日時|
|THMD_NSEND_STATUS|NUMBER||否|送信ステータス ０：未送信　１：送信済　２：送信エラー|
|VERSIONNO|NUMBER||否|バージョンNo|
