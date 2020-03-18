# 集計項目マスタ                                                     (TMG_MAST_TOTALITEM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMTI_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMTI_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMTI_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMTI_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMTI_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMTI_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMTI_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMTI_CCATEGORYID|varchar||否|集計カテゴリｺｰﾄﾞ                                                                                |
|TMTI_CUNITID|varchar||是|集計単位ｺｰﾄﾞ                                                                                  |
|TMTI_CTOTALIZATIONID|varchar||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMTI_CTOTALIZATIONNM|varchar||否|集計項目名称                                                                                    |
|TMTI_NAUTO_APPROVE_EXEMPT_FLG|numeric||是|自動承認除外フラグ　０：除外しない　１：除外する|
