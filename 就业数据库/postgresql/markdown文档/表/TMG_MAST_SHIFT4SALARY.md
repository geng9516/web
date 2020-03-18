# シフト区分マスタ                      給与データ出力に使用するシフト区分を管理します。      (TMG_MAST_SHIFT4SALARY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMSS_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMSS_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMSS_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMSS_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMSS_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMSS_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMSS_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMSS_CTOTALIZATIONID|varchar||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMSS_CSHIFTID|varchar||否|シフト区分                                                                                     |
