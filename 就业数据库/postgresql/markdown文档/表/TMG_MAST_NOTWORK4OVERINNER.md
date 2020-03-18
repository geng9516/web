# 法定内残業計算対象非勤務マスタ                                             (TMG_MAST_NOTWORK4OVERINNER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMNO_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMNO_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMNO_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMNO_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMNO_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMNO_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMNO_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMNO_CMASTER|varchar||否|マスターコード                                                                                   |
