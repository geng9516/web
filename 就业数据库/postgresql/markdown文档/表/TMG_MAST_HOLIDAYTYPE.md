# 休日区分マスタ                                                     (TMG_MAST_HOLIDAYTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMHT_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMHT_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMHT_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMHT_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMHT_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMHT_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMHT_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMHT_CWORKINGID|varchar||否|就業区分ｺｰﾄﾞ                                                                                  |
|TMHT_CWORKINGHOURSID|varchar||否|労働時間ｺｰﾄﾞ                                                                                  |
