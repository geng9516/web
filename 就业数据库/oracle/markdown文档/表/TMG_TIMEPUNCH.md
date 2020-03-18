# [勤怠]打刻データ（未反映）                                              (TMG_TIMEPUNCH)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TTP_NID|NUMBER||否|ID                                                          TMG_TIMEPUNCH_SEQ             |
|TTP_CCUSTOMERID|VARCHAR2||否|顧客コード                                                                                     |
|TTP_CCOMPANYID|VARCHAR2||否|法人コード                                                                                     |
|TTP_CEMPLOYEEID|VARCHAR2||否|職員番号                                                                                      |
|TTP_DSTARTDATE|DATE||否|データ開始日                                                                                    |
|TTP_DENDDATE|DATE||否|データ終了日                                                                                    |
|TTP_CMODIFIERUSERID|VARCHAR2||是|更新ユーザーID                                                                                  |
|TTP_DMODIFIEDDATE|DATE||是|更新日時                                                                                      |
|TTP_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TTP_DTPDATE|DATE||否|打刻年月日                         時刻丸め                                                        |
|TTP_DTPTIME|DATE||否|打刻時刻                                                                                      |
|TTP_CTPTYPEID|VARCHAR2||否|打刻区分                                                        MGD：TMG_TPTYPE                |
