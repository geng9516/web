# [勤怠]申請情報                      2007/01/31項目追加「申請日」「対象曜日：指定なし」(TMG_NOTIFICATION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TNTF_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01|
|TNTF_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TNTF_CEMPLOYEEID|varchar||否|社員番号|
|TNTF_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01|
|TNTF_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31|
|TNTF_CMODIFIERUSERID|varchar||是|更新者|
|TNTF_DMODIFIEDDATE|date||是|更新日|
|TNTF_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TNTF_NSEQ|numeric||否|連番|
|TNTF_CNTFNO|varchar||是|申請番号                          申請者社員番号|連番|
|TNTF_CSTATUSFLG|varchar||否|ステータスフラグ                                                    MGD:TMG_NTFSTATUS|
|TNTF_CALTEREMPLOYEEID|varchar||否|申請者社員番号|
|TNTF_DNOTIFICATION|date||是|申請日|
|TNTF_DBEGIN|date||否|対象期間：開始日|
|TNTF_DEND|date||是|対象期間：終了日|
|TNTF_DCANCEL|date||是|対象期間：解除日|
|TNTF_NTIME_OPEN|numeric||是|対象時間：始業時の非勤務時間                時間の直接指定の場合|
|TNTF_NTIME_CLOSE|numeric||是|対象時間：終業時の非勤務時間                時間の直接指定の場合|
|TNTF_NTIMEZONE_OPEN|numeric||是|対象時間：開始時刻                     時間帯指定の場合|
|TNTF_NTIMEZONE_CLOSE|numeric||是|対象時間：終了時刻                     時間帯指定の場合|
|TNTF_NNORESERVED|numeric||是|対象曜日：指定なし|
|TNTF_NMON|numeric||是|対象曜日：月曜|
|TNTF_NTUE|numeric||是|対象曜日：火曜|
|TNTF_NWED|numeric||是|対象曜日：水曜|
|TNTF_NTHU|numeric||是|対象曜日：木曜|
|TNTF_NFRI|numeric||是|対象曜日：金曜|
|TNTF_NSAT|numeric||是|対象曜日：土曜|
|TNTF_NSUN|numeric||是|対象曜日：日曜|
|TNTF_NDAYOFWEEK|numeric||是|対象曜日指定                        ビットフラグ|
|TNTF_CTYPE|varchar||是|申請種類                                                        MGD:TMG_NTFTYPE|
|TNTF_COWNCOMMENT|varchar||是|申請事由|
|TNTF_CBOSS|varchar||是|承認者社員番号|
|TNTF_CBOSSCOMMENT|varchar||是|承認者コメント|
|TNTF_DBOSS|date||是|承認日|
|TNTF_CCANCEL|varchar||是|申請解除社員番号|
|TNTF_CCANCELCOMMENT|varchar||是|申請解除者のコメント|
|TNTF_DDAIKYU|date||是|代休日|
|TNTF_CSICK_TYPE|varchar||是|特別休暇：傷病種類|
|TNTF_CSICK_NAME|varchar||是|特別休暇：傷病名|
|TNTF_CSAME_SICK_TYPE|varchar||是|特別休暇：同一傷病区分|
|TNTF_CDISASTER|varchar||是|特別休暇：災害申請区分|
|TNTF_DPERIOD_DATE|date||是|特別休暇：起算日|
|TNTF_NUAPPER_ADDITION|numeric||是|特別休暇：上限加算|
|TNTF_CNTFNO_IM|varchar||是|IMワークフロー用申請番号|
|TNTF_NRESTOPEN|numeric||是|休憩開始時刻|
|TNTF_NRESTCLOSE|numeric||是|休憩終了時刻|
|TNTF_CKANJINAME|varchar||是|氏名(家族、子等の)|
|TNTF_CRELATION|varchar||是|続柄|
|TNTF_DDATEOFBIRTH|date||是|生年月日|
|TNTF_NNUMBER_OF_TARGET|numeric||是|対象の人数|
|TNTF_CNTFNO_MOTO|varchar||是|分割前申請番号|
|TNTF_DCANCELEND|date||是|解除：終了日|
|TNTF_CAPPROVAL_LEVEL|varchar||是|決裁レベル|
|TNTF_CSITEID|varchar||是|サイトID|
|TNTF_CNTFACTION|varchar||是|操作種別|
