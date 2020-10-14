# [勤怠]月次集計情報                                                  (TMG_MONTHLY_TOTALIZATION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMT_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMT_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMT_CEMPLOYEEID|varchar||否|職員番号                                                                                      |
|TMT_DSTARTDATE|date||否|データ開始日                        固定：1900/01/01                                               |
|TMT_DENDDATE|date||否|データ終了日                        固定：2222/12/31                                               |
|TMT_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMT_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMT_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMT_DYYYYMM|date||否|該当年月                          YYYY/MM/01                                                  |
|TMT_CTOTALIZATIONID|varchar||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMT_CJOURNALIZINGID|varchar||是|仕訳項目ｺｰﾄﾞ                                                                                  |
|TMT_NVALUE|numeric||否|値                                                                                         |
