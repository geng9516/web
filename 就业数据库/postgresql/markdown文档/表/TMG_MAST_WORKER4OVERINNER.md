# 法定内残業計算対象身分マスタ                                              (TMG_MAST_WORKER4OVERINNER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMWO_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMWO_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMWO_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMWO_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMWO_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMWO_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMWO_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMWO_CWORKTYPEID|varchar||否|勤怠種別                                                                                      |
