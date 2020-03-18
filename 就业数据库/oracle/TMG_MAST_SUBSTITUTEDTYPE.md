# 振替休日区分マスタ                                                   (TMG_MAST_SUBSTITUTEDTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMSB_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMSB_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMSB_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMSB_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMSB_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMSB_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMSB_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMSB_CWORKINGID|VARCHAR2||否|就業区分ｺｰﾄﾞ                                                                                  |
