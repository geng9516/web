# 月次テーブルマッピングマスタ                月次集計処理において、TMG_MONTHLY_TOTALIZ(TMG_MAST_MONTHLYMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMMM_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMMM_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMMM_DSTARTDATE|DATE||否|データ開始日                                                                                    |
|TMMM_DENDDATE|DATE||否|データ終了日                                                                                    |
|TMMM_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMMM_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMMM_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMMM_CTOTALIZATIONID|VARCHAR2||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMMM_CMONTHLYCOLUMN|VARCHAR2||否|マッピング先カラム名                                                                                |
