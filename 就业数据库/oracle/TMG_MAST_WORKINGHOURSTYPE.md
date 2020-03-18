# 労働時間マスタ                                                     (TMG_MAST_WORKINGHOURSTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMWT_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMWT_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMWT_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMWT_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMWT_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMWT_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMWT_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMWT_CWORKTYPEID|VARCHAR2||否|勤怠種別                                                                                  |
|TMWT_CWORKINGHOURSTYPEID|VARCHAR2||否|労働時間ｺｰﾄﾞ                                                                                  |
|TMWT_CWORKINGHOURSTYPENM|VARCHAR2||否|労働時間名称                                                                                    |
|TMWT_NLEAGALINOUTFLG|NUMBER||是|法定内外時間判定ﾌﾗｸﾞ|
