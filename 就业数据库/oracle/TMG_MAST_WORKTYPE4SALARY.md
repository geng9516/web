# 給与用勤怠種別マッピングマスタ                                             (TMG_MAST_WORKTYPE4SALARY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMWT_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMWT_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMWT_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMWT_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMWT_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMWT_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMWT_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMWT_CWORKTYPEID4SALARY|VARCHAR2||否|給与用勤怠種別                                                                                   |
|TMWT_CWORKTYPEID|VARCHAR2||否|勤怠種別                                                                                      |
