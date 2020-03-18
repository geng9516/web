# [勤怠]法人情報                      2007/01/31項目追加｢年度の開始月」        (TMG_COMPANY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TCO_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TCO_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TCO_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TCO_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TCO_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TCO_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TCO_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TCO_NOPEN|NUMBER||是|開始時刻                          300                                                         |
|TCO_NCLOSE|NUMBER||是|終了時刻                          1740                                                        |
|TCO_NMIDNIGHTOPEN|NUMBER||是|深夜勤務開始時刻                      1320                                                        |
|TCO_NMIDNIGHTCLOSE|NUMBER||是|深夜勤務終了時刻                      1740                                                        |
|TCO_NTIMEPUNCHOPEN|NUMBER||是|打刻サービス開始時刻                    420                                                         |
|TCO_NTIMEPUNCHCLOSE|NUMBER||是|打刻サービス終了時刻                    1320                                                        |
|TCO_NBEGINMONTH|NUMBER||是|年度の開始月                                                                                    |
