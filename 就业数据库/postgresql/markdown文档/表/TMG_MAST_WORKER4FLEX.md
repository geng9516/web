# フレックス制身分マスタ                                                   (TMG_MAST_WORKER4FLEX)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMWF_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMWF_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMWF_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMWF_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMWF_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMWF_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMWF_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMWF_CWORKTYPEID|varchar||否|勤怠種別                                                                                      |
