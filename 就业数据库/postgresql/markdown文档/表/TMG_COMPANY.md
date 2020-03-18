# [勤怠]法人情報                      2007/01/31項目追加｢年度の開始月」        (TMG_COMPANY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TCO_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TCO_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TCO_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TCO_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TCO_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TCO_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TCO_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TCO_NOPEN|numeric||是|開始時刻                          300                                                         |
|TCO_NCLOSE|numeric||是|終了時刻                          1740                                                        |
|TCO_NMIDNIGHTOPEN|numeric||是|深夜勤務開始時刻                      1320                                                        |
|TCO_NMIDNIGHTCLOSE|numeric||是|深夜勤務終了時刻                      1740                                                        |
|TCO_NTIMEPUNCHOPEN|numeric||是|打刻サービス開始時刻                    420                                                         |
|TCO_NTIMEPUNCHCLOSE|numeric||是|打刻サービス終了時刻                    1320                                                        |
|TCO_NBEGINMONTH|numeric||是|年度の開始月                                                                                    |
