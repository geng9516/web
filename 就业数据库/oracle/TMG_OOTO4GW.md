# [勤怠]ＧＷデータ連携  休暇等の不在情報（最新）(TMG_OOTO4GW)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TOW_NID|NUMBER||是|ID                                                          TMG_OOTO4GW_SEQ|
|TOW_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TOW_CCOMPANYID|VARCHAR2||否|法人コード|
|TOW_CEMPLOYEEID|VARCHAR2||否|職員番号|
|TOW_DSTARTDATE|DATE||否|開始日|
|TOW_DENDDATE|DATE||否|終了日|
|TOW_CMODIFIERUSERID|VARCHAR2||是|更新ユーザーID|
|TOW_DMODIFIEDDATE|DATE||是|更新日時|
|TOW_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TOW_NSTARTTIME|NUMBER||是|開始時刻|
|TOW_NENDTIME|NUMBER||是|終了時刻|
|TOW_DCREATEDATE|DATE||是|作成日時|
|TOW_CDELFLG|NUMBER||是|削除フラグ|
|TOW_DDELDATE|DATE||是|削除日時|
|TOW_CTITLE|VARCHAR2||是|タイトル|
