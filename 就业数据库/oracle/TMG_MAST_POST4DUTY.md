# 宿日直用職名ｺｰﾄﾞ変換マスタ               職名ｺｰﾄﾞを宿日直用の中間コードに変換する        (TMG_MAST_POST4DUTY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMPD_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMPD_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMPD_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMPD_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMPD_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMPD_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMPD_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMPD_CPOSTID|VARCHAR2||否|職名ｺｰﾄﾞ                                                                                    |
|TMPD_CMIDDLEPOSTID|VARCHAR2||否|中間職名ｺｰﾄﾞ                                                                                  |
