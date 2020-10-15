# [勤怠]HR連携用(勤怠種別)                                             (TMG_HIST_WORKTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THW_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|THW_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|THW_CEMPLOYEEID|varchar||否|職員番号                                                                                      |
|THW_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|THW_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|THW_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|THW_DMODIFIEDDATE|date||是|更新日                                                                                       |
|THW_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|THW_CWORKTYPEID|varchar||是|勤怠種別                                                        MGD:TMG_WORKTYPE              |
|THW_CWORKTYPENAME|varchar||是|勤怠種別名称                                                                                    |
