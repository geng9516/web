# [勤怠]HR連携用(勤怠種別)                                             (TMG_HIST_WORKTYPE_SIM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THWS_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|THWS_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|THWS_CEMPLOYEEID|varchar||否|職員番号                                                                                      |
|THWS_CUSERID|varchar||否|ユーザID                                                                                      |
|THWS_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|THWS_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|THWS_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|THWS_DMODIFIEDDATE|date||是|更新日                                                                                       |
|THWS_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|THWS_CWORKTYPEID|varchar||是|勤怠種別                                                        MGD:TMG_WORKTYPE              |
|THWS_CWORKTYPENAME|varchar||是|勤怠種別名称                                                                                    |
