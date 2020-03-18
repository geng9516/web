# 差引優先項目マスタ                     給与データ出力時に超勤項目から時間を差引く際の、差し引く対象(TMG_MAST_BALANCEITEM4SALARY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMBI_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMBI_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMBI_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMBI_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMBI_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMBI_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMBI_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMBI_CMASTER|VARCHAR2||否|差引対象項目                                                                                    |
|TMBI_NORDER|NUMBER||否|優先順位                                                                                      |
