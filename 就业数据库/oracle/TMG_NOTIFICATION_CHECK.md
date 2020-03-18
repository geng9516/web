# [勤怠]エラーチェック用・申請情報(TMG_NOTIFICATION_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TNTF_NFRI|NUMBER||是|対象曜日：金曜|
|TNTF_NSAT|NUMBER||是|対象曜日：土曜|
|TNTF_NSUN|NUMBER||是|対象曜日：日曜|
|TNTF_NDAYOFWEEK|NUMBER||是|対象曜日指定                        ビットフラグ|
|TNTF_CTYPE|VARCHAR2||是|申請種類                                      MGD:TMG_NTFTYPE|
|TNTF_COWNCOMMENT|VARCHAR2||是|申請事由|
|TNTF_CBOSS|VARCHAR2||是|承認者社員番号|
|TNTF_CBOSSCOMMENT|VARCHAR2||是|承認者コメント|
|TNTF_DBOSS|DATE||是|承認日|
|TNTF_CCANCEL|VARCHAR2||是|申請解除社員番号|
|TNTF_CCANCELCOMMENT|VARCHAR2||是|申請解除者のコメント|
|TNTF_DDAIKYU|DATE||是|代休日|
|TNTF_CSICK_TYPE|VARCHAR2||是|特別休暇：傷病種類|
|TNTF_CSICK_NAME|VARCHAR2||是|特別休暇：傷病名|
|TNTF_CSAME_SICK_TYPE|VARCHAR2||是|特別休暇：同一傷病区分|
|TNTF_CDISASTER|VARCHAR2||是|特別休暇：災害申請区分|
|TNTF_DPERIOD_DATE|DATE||是|特別休暇：起算日|
|TNTF_NUAPPER_ADDITION|NUMBER||是|特別休暇：上限加算|
|TNTF_CNTFNO_IM|VARCHAR2||是|IMワークフロー用申請番号|
|TNTF_NRESTOPEN|NUMBER||是|休憩開始時刻|
|TNTF_NRESTCLOSE|NUMBER||是|休憩終了時刻|
|TNTF_CKANJINAME|VARCHAR2||是|氏名(家族、子等の)|
|TNTF_CRELATION|VARCHAR2||是|続柄|
|TNTF_DDATEOFBIRTH|DATE||是|生年月日|
|TNTF_NNUMBER_OF_TARGET|NUMBER||是|対象の人数|
|TNTF_CNTFNO_MOTO|VARCHAR2||是|分割前申請番号|
|TNTF_DCANCELEND|DATE||是|解除：終了日|
|TNTF_CAPPROVAL_LEVEL|VARCHAR2||是|null|
|TNTF_CSITEID|VARCHAR2||是|null|
|TNTF_CNTFACTION|VARCHAR2||是|null|
|TNTF_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01|
|TNTF_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ|
|TNTF_CEMPLOYEEID|VARCHAR2||否|社員番号|
|TNTF_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01|
|TNTF_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31|
|TNTF_CMODIFIERUSERID|VARCHAR2||是|更新者|
|TNTF_DMODIFIEDDATE|DATE||是|更新日|
|TNTF_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TNTF_NSEQ|NUMBER||否|連番|
|TNTF_CNTFNO|VARCHAR2||是|申請番号                          申請者社員番号|連番|
|TNTF_CSTATUSFLG|VARCHAR2||否|ステータスフラグ               MGD:TMG_NTFSTATUS|
|TNTF_CALTEREMPLOYEEID|VARCHAR2||否|申請者社員番号|
|TNTF_DNOTIFICATION|DATE||是|申請日|
|TNTF_DBEGIN|DATE||否|対象期間：開始日|
|TNTF_DEND|DATE||是|対象期間：終了日|
|TNTF_DCANCEL|DATE||是|対象期間：解除日|
|TNTF_NTIME_OPEN|NUMBER||是|対象時間：始業時の非勤務時間                時間の直接指定の場合|
|TNTF_NTIME_CLOSE|NUMBER||是|対象時間：終業時の非勤務時間                時間の直接指定の場合|
|TNTF_NTIMEZONE_OPEN|NUMBER||是|対象時間：開始時刻                     時間帯指定の場合|
|TNTF_NTIMEZONE_CLOSE|NUMBER||是|対象時間：終了時刻                     時間帯指定の場合|
|TNTF_NNORESERVED|NUMBER||是|対象曜日：指定なし |
|TNTF_NMON|NUMBER||是|対象曜日：月曜|
|TNTF_NTUE|NUMBER||是|対象曜日：火曜|
|TNTF_NWED|NUMBER||是|対象曜日：水曜|
|TNTF_NTHU|NUMBER||是|対象曜日：木曜|
