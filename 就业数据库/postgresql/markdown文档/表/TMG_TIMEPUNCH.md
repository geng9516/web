# [勤怠]打刻データ（未反映）                                              (TMG_TIMEPUNCH)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TTP_NID|numeric||否|ID                                                          TMG_TIMEPUNCH_SEQ             |
|TTP_CCUSTOMERID|varchar||否|顧客コード                                                                                     |
|TTP_CCOMPANYID|varchar||否|法人コード                                                                                     |
|TTP_CEMPLOYEEID|varchar||否|職員番号                                                                                      |
|TTP_DSTARTDATE|date||否|データ開始日                                                                                    |
|TTP_DENDDATE|date||否|データ終了日                                                                                    |
|TTP_CMODIFIERUSERID|varchar||是|更新ユーザーID                                                                                  |
|TTP_DMODIFIEDDATE|date||是|更新日時                                                                                      |
|TTP_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TTP_DTPDATE|date||否|打刻年月日                         時刻丸め                                                        |
|TTP_DTPTIME|date||否|打刻時刻                                                                                      |
|TTP_CTPTYPEID|varchar||否|打刻区分                                                        MGD：TMG_TPTYPE                |
