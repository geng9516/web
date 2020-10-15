# [勤怠]申請ログ情報(TMG_NTFACTIONLOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TNAL_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01|
|TNAL_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TNAL_CEMPLOYEEID|varchar||否|職員番号|
|TNAL_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01|
|TNAL_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31|
|TNAL_CMODIFIERUSERID|varchar||是|更新者|
|TNAL_DMODIFIEDDATE|date||是|更新日|
|TNAL_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TNAL_CSITEID|varchar||是|更新サイト|
|TNAL_CNTFNO|varchar||是|申請番号                          申請者職員番号|連番|
|TNAL_CNTFACTION|varchar||是|操作種別|
|TNAL_CPRE_STATUSFLG|varchar||是|更新前ステータスフラグ MGD:TMG_NTFSTATUS|
|TNAL_CPRE_APPROVAL_LEVEL|varchar||是|更新前決裁レベル MGD:TMG_APPROVAL_LEVEL|
|TNAL_CAFT_STATUSFLG|varchar||是|更新後ステータスフラグ MGD:TMG_NTFSTATUS|
|TNAL_CAFT_APPROVAL_LEVEL|varchar||是|更新後決裁レベル MGD:TMG_APPROVAL_LEVEL|
|TNAL_CUPDATECCOMMENT|varchar||是|更新時コメント|
