# [勤怠]HR連携用(勤怠種別)                                             (TMG_HIST_WORKTYPE_SIM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THWS_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|THWS_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|THWS_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                      |
|THWS_CUSERID|VARCHAR2||否|ユーザID                                                                                      |
|THWS_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|THWS_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|THWS_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|THWS_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|THWS_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|THWS_CWORKTYPEID|VARCHAR2||是|勤怠種別                                                        MGD:TMG_WORKTYPE              |
|THWS_CWORKTYPENAME|VARCHAR2||是|勤怠種別名称                                                                                    |
