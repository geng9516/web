# 裁量労働身分マスタ                                                   (TMG_MAST_WORKER4DISCRETIONARY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMWD_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMWD_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMWD_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMWD_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMWD_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMWD_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMWD_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMWD_CWORKTYPEID|VARCHAR2||否|勤怠種別                                                                                      |
