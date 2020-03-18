# 宿日直用職名ｺｰﾄﾞ変換マスタ               職名ｺｰﾄﾞを宿日直用の中間コードに変換する        (TMG_MAST_POST4DUTY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMPD_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMPD_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMPD_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMPD_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMPD_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMPD_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMPD_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMPD_CPOSTID|varchar||否|職名ｺｰﾄﾞ                                                                                    |
|TMPD_CMIDDLEPOSTID|varchar||否|中間職名ｺｰﾄﾞ                                                                                  |
