# [勤怠]月次集計情報                                                  (TMG_MONTHLY_TOTALIZATION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMT_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMT_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMT_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                      |
|TMT_DSTARTDATE|DATE||否|データ開始日                        固定：1900/01/01                                               |
|TMT_DENDDATE|DATE||否|データ終了日                        固定：2222/12/31                                               |
|TMT_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMT_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMT_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMT_DYYYYMM|DATE||否|該当年月                          YYYY/MM/01                                                  |
|TMT_CTOTALIZATIONID|VARCHAR2||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMT_CJOURNALIZINGID|VARCHAR2||是|仕訳項目ｺｰﾄﾞ                                                                                  |
|TMT_NVALUE|NUMBER||否|値                                                                                         |
