# フレックス制身分マスタ                                                   (TMG_MAST_WORKER4FLEX)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMWF_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMWF_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMWF_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMWF_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMWF_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMWF_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMWF_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMWF_CWORKTYPEID|VARCHAR2||否|勤怠種別                                                                                      |
