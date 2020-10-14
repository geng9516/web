# [勤怠]日次仕訳情報                                                  (TMG_DAILY_JOURNALIZING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDJ_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                                                                                    |
|TDJ_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TDJ_CEMPLOYEEID|varchar||否|職員番号                                                                                      |
|TDJ_DSTARTDATE|date||否|データ開始日                                                                                    |
|TDJ_DENDDATE|date||否|データ終了日                                                                                    |
|TDJ_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TDJ_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TDJ_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TDJ_DYYYYMMDD|date||否|該当年月                                                                                      |
|TDJ_CJOURNALIZINGID|varchar||否|仕訳項目ｺｰﾄﾞ                                                    TMG_MAST_JOURNALIZE           |
|TDJ_NSTART|numeric||否|開始時刻                          分単位                                                         |
|TDJ_NEND|numeric||否|終了時刻                          分単位                                                         |
