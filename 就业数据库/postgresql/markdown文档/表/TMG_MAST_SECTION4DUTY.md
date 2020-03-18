# 宿日直用所属ｺｰﾄﾞ変換マスタ               所属ｺｰﾄﾞを宿日直用の中間コードに変換する        (TMG_MAST_SECTION4DUTY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMSD_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMSD_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMSD_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMSD_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMSD_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMSD_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMSD_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMSD_CSECTIONID|varchar||否|所属ｺｰﾄﾞ                                                                                    |
|TMSD_CMIDDLESECTIONID|varchar||否|中間所属ｺｰﾄﾞ                                                                                  |
