# [勤怠]日次集計情報                                                  (TMG_DAILY_TOTALIZATION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDT_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDT_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TDT_CEMPLOYEEID|varchar||否|社員番号                                                                                      |
|TDT_DSTARTDATE|date||否|データ開始日                        固定：1900/01/01                                               |
|TDT_DENDDATE|date||否|データ終了日                        固定：2222/12/31                                               |
|TDT_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TDT_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TDT_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TDT_DYYYYMMDD|date||否|該当年月日                         YYYY/MM/DD                                                  |
|TDT_DTARGETDATE|date||否|集計先の暦日                        YYYY/MM/DD                                                  |
|TDT_CTOTALIZATIONID|varchar||否|集計項目ｺｰﾄﾞ                                                    TMG_MAST_TOTALITEM            |
|TDT_CJOURNALIZINGID|varchar||是|仕訳項目ｺｰﾄﾞ                                                    TMG_MAST_JOURNALIZE           |
|TDT_NVALUE|numeric||否|値                                                                                         |
