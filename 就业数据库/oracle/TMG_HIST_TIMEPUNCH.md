# [勤怠]反映済打刻データログ                                              (TMG_HIST_TIMEPUNCH)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THTP_NID|VARCHAR2||否|ID                            対応SEQ無し（TTP_NIDをセット）                                        |
|THTP_CCUSTOMERID|VARCHAR2||否|顧客コード                                                                                     |
|THTP_CCOMPANYID|VARCHAR2||否|法人コード                                                                                     |
|THTP_CEMPLOYEEID|VARCHAR2||否|職員番号                                                                                      |
|THTP_DSTARTDATE|DATE||否|データ開始日                                                                                    |
|THTP_DENDDATE|DATE||否|データ終了日                                                                                    |
|THTP_CMODIFIERUSERID|VARCHAR2||是|更新ユーザーID                                                                                  |
|THTP_DMODIFIEDDATE|DATE||是|更新日時                                                                                      |
|THTP_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|THTP_DTPDATE|DATE||否|打刻年月日                         時刻丸め                                                        |
|THTP_DTPTIME|DATE||否|打刻時刻                                                                                      |
|THTP_CTPTYPEID|VARCHAR2||否|打刻区分                                                        MGD：TMG_TPTYPE                |
|THTP_DYYYYMMDD|DATE||是|反映先勤務日                        反映先のTMG_DAILY                                               |
|THTP_CTPSTATUS|VARCHAR2||否|反映ステータス                                                     MGD：TMG_TPSTATUS              |
|THTP_CMEMO|VARCHAR2||是|備考                            反映失敗時のエラーコード等                                               |
