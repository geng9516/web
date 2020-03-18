# [勤怠]日次仕訳情報                                                  (TMG_DAILY_JOURNALIZING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDJ_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                                                                                    |
|TDJ_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TDJ_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                      |
|TDJ_DSTARTDATE|DATE||否|データ開始日                                                                                    |
|TDJ_DENDDATE|DATE||否|データ終了日                                                                                    |
|TDJ_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TDJ_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TDJ_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TDJ_DYYYYMMDD|DATE||否|該当年月                                                                                      |
|TDJ_CJOURNALIZINGID|VARCHAR2||否|仕訳項目ｺｰﾄﾞ                                                    TMG_MAST_JOURNALIZE           |
|TDJ_NSTART|NUMBER||否|開始時刻                          分単位                                                         |
|TDJ_NEND|NUMBER||否|終了時刻                          分単位                                                         |
