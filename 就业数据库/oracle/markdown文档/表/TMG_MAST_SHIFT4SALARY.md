# シフト区分マスタ                      給与データ出力に使用するシフト区分を管理します。      (TMG_MAST_SHIFT4SALARY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMSS_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMSS_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMSS_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMSS_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMSS_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMSS_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMSS_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMSS_CTOTALIZATIONID|VARCHAR2||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMSS_CSHIFTID|VARCHAR2||否|シフト区分                                                                                     |
