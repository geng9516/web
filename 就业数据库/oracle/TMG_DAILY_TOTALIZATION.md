# [勤怠]日次集計情報                                                  (TMG_DAILY_TOTALIZATION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDT_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDT_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TDT_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                      |
|TDT_DSTARTDATE|DATE||否|データ開始日                        固定：1900/01/01                                               |
|TDT_DENDDATE|DATE||否|データ終了日                        固定：2222/12/31                                               |
|TDT_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TDT_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TDT_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TDT_DYYYYMMDD|DATE||否|該当年月日                         YYYY/MM/DD                                                  |
|TDT_DTARGETDATE|DATE||否|集計先の暦日                        YYYY/MM/DD                                                  |
|TDT_CTOTALIZATIONID|VARCHAR2||否|集計項目ｺｰﾄﾞ                                                    TMG_MAST_TOTALITEM            |
|TDT_CJOURNALIZINGID|VARCHAR2||是|仕訳項目ｺｰﾄﾞ                                                    TMG_MAST_JOURNALIZE           |
|TDT_NVALUE|NUMBER||否|値                                                                                         |
