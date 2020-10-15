# [勤怠]HR連携用(勤怠種別)                                             (TMG_HIST_WORKTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THW_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|THW_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|THW_CEMPLOYEEID|VARCHAR2||否|職員番号                                                                                      |
|THW_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|THW_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|THW_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|THW_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|THW_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|THW_CWORKTYPEID|VARCHAR2||是|勤怠種別                                                        MGD:TMG_WORKTYPE              |
|THW_CWORKTYPENAME|VARCHAR2||是|勤怠種別名称                                                                                    |
