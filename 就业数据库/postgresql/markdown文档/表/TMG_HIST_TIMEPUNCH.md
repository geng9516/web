# [勤怠]反映済打刻データログ                                              (TMG_HIST_TIMEPUNCH)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THTP_NID|varchar||否|ID                            対応SEQ無し（TTP_NIDをセット）                                        |
|THTP_CCUSTOMERID|varchar||否|顧客コード                                                                                     |
|THTP_CCOMPANYID|varchar||否|法人コード                                                                                     |
|THTP_CEMPLOYEEID|varchar||否|職員番号                                                                                      |
|THTP_DSTARTDATE|date||否|データ開始日                                                                                    |
|THTP_DENDDATE|date||否|データ終了日                                                                                    |
|THTP_CMODIFIERUSERID|varchar||是|更新ユーザーID                                                                                  |
|THTP_DMODIFIEDDATE|date||是|更新日時                                                                                      |
|THTP_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|THTP_DTPDATE|date||否|打刻年月日                         時刻丸め                                                        |
|THTP_DTPTIME|date||否|打刻時刻                                                                                      |
|THTP_CTPTYPEID|varchar||否|打刻区分                                                        MGD：TMG_TPTYPE                |
|THTP_DYYYYMMDD|date||是|反映先勤務日                        反映先のTMG_DAILY                                               |
|THTP_CTPSTATUS|varchar||否|反映ステータス                                                     MGD：TMG_TPSTATUS              |
|THTP_CMEMO|varchar||是|備考                            反映失敗時のエラーコード等                                               |
