# 休日区分マスタ                                                     (TMG_MAST_HOLIDAYTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMHT_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMHT_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMHT_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMHT_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMHT_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMHT_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMHT_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMHT_CWORKINGID|VARCHAR2||否|就業区分ｺｰﾄﾞ                                                                                  |
|TMHT_CWORKINGHOURSID|VARCHAR2||否|労働時間ｺｰﾄﾞ                                                                                  |
