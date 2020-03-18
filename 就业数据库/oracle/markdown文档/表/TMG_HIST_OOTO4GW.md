# [勤怠]ＧＷデータ連携  休暇等の不在情報（累計）(TMG_HIST_OOTO4GW)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THOW_NID|NUMBER||否|ID                                                          TMG_HIST_OOTO4GW_SEQ|
|THOW_CCUSTOMERID|VARCHAR2||否|顧客コード|
|THOW_CCOMPANYID|VARCHAR2||否|法人コード|
|THOW_CEMPLOYEEID|VARCHAR2||否|職員番号|
|THOW_DSTARTDATE|DATE||否|開始日|
|THOW_DENDDATE|DATE||否|終了日|
|THOW_CMODIFIERUSERID|VARCHAR2||是|更新ユーザーID|
|THOW_DMODIFIEDDATE|DATE||是|更新日時|
|THOW_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|THOW_NSTARTTIME|NUMBER||是|開始時刻|
|THOW_NENDTIME|NUMBER||是|終了時刻|
|THOW_DCREATEDATE|DATE||是|作成日時|
|THOW_CDELFLG|NUMBER||是|削除フラグ|
|THOW_DDELDATE|DATE||是|削除日時|
|THOW_CTITLE|VARCHAR2||是|タイトル|
