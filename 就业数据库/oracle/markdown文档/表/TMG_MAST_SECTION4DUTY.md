# 宿日直用所属ｺｰﾄﾞ変換マスタ               所属ｺｰﾄﾞを宿日直用の中間コードに変換する        (TMG_MAST_SECTION4DUTY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMSD_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMSD_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMSD_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMSD_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMSD_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMSD_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMSD_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMSD_CSECTIONID|VARCHAR2||否|所属ｺｰﾄﾞ                                                                                    |
|TMSD_CMIDDLESECTIONID|VARCHAR2||否|中間所属ｺｰﾄﾞ                                                                                  |
