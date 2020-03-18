# 集計項目マスタ                                                     (TMG_MAST_TOTALITEM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMTI_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMTI_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMTI_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMTI_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMTI_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMTI_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMTI_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMTI_CCATEGORYID|VARCHAR2||否|集計カテゴリｺｰﾄﾞ                                                                                |
|TMTI_CUNITID|VARCHAR2||是|集計単位ｺｰﾄﾞ                                                                                  |
|TMTI_CTOTALIZATIONID|VARCHAR2||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMTI_CTOTALIZATIONNM|VARCHAR2||否|集計項目名称                                                                                    |
|TMTI_NAUTO_APPROVE_EXEMPT_FLG|NUMBER||是|自動承認除外フラグ　０：除外しない　１：除外する|
