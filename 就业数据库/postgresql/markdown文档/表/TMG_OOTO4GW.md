# [勤怠]ＧＷデータ連携  休暇等の不在情報（最新）(TMG_OOTO4GW)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TOW_NID|numeric||是|ID                                                          TMG_OOTO4GW_SEQ|
|TOW_CCUSTOMERID|varchar||否|顧客コード|
|TOW_CCOMPANYID|varchar||否|法人コード|
|TOW_CEMPLOYEEID|varchar||否|職員番号|
|TOW_DSTARTDATE|date||否|開始日|
|TOW_DENDDATE|date||否|終了日|
|TOW_CMODIFIERUSERID|varchar||是|更新ユーザーID|
|TOW_DMODIFIEDDATE|date||是|更新日時|
|TOW_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TOW_NSTARTTIME|numeric||是|開始時刻|
|TOW_NENDTIME|numeric||是|終了時刻|
|TOW_DCREATEDATE|date||是|作成日時|
|TOW_CDELFLG|numeric||是|削除フラグ|
|TOW_DDELDATE|date||是|削除日時|
|TOW_CTITLE|varchar||是|タイトル|
