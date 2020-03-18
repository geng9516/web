# 労働時間マスタ                                                     (TMG_MAST_WORKINGHOURSTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMWT_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMWT_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMWT_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMWT_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMWT_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMWT_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMWT_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMWT_CWORKTYPEID|varchar||否|勤怠種別                                                                                  |
|TMWT_CWORKINGHOURSTYPEID|varchar||否|労働時間ｺｰﾄﾞ                                                                                  |
|TMWT_CWORKINGHOURSTYPENM|varchar||否|労働時間名称                                                                                    |
|TMWT_NLEAGALINOUTFLG|numeric||是|法定内外時間判定ﾌﾗｸﾞ|
