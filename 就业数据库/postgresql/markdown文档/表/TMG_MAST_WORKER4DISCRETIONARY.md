# 裁量労働身分マスタ                                                   (TMG_MAST_WORKER4DISCRETIONARY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMWD_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMWD_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMWD_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMWD_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMWD_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMWD_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMWD_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMWD_CWORKTYPEID|varchar||否|勤怠種別                                                                                      |
