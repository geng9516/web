# 集計項目カテゴリマスタ                                                 (TMG_MAST_TOTALCATEGORY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMTC_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMTC_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMTC_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMTC_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMTC_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMTC_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMTC_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMTC_CCATEGORYID|VARCHAR2||否|集計カテゴリｺｰﾄﾞ                                                                                |
|TMTC_CCATEGORYNM|VARCHAR2||否|集計カテゴリ名称                                                                                  |
