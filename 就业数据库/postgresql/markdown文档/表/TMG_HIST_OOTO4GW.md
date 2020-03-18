# [勤怠]ＧＷデータ連携  休暇等の不在情報（累計）(TMG_HIST_OOTO4GW)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THOW_NID|numeric||否|ID                                                          TMG_HIST_OOTO4GW_SEQ|
|THOW_CCUSTOMERID|varchar||否|顧客コード|
|THOW_CCOMPANYID|varchar||否|法人コード|
|THOW_CEMPLOYEEID|varchar||否|職員番号|
|THOW_DSTARTDATE|date||否|開始日|
|THOW_DENDDATE|date||否|終了日|
|THOW_CMODIFIERUSERID|varchar||是|更新ユーザーID|
|THOW_DMODIFIEDDATE|date||是|更新日時|
|THOW_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|THOW_NSTARTTIME|numeric||是|開始時刻|
|THOW_NENDTIME|numeric||是|終了時刻|
|THOW_DCREATEDATE|date||是|作成日時|
|THOW_CDELFLG|numeric||是|削除フラグ|
|THOW_DDELDATE|date||是|削除日時|
|THOW_CTITLE|varchar||是|タイトル|
