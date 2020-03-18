# 集計項目カテゴリマスタ                                                 (TMG_MAST_TOTALUNIT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMTU_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMTU_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMTU_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMTU_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMTU_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMTU_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMTU_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMTU_CUNITID|VARCHAR2||否|集計単位ｺｰﾄﾞ                                                                                  |
|TMTU_CUNITNM|VARCHAR2||否|集計単位名称                                                                                    |
