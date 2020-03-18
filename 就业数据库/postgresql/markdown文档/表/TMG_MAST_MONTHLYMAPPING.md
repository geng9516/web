# 月次テーブルマッピングマスタ                月次集計処理において、TMG_MONTHLY_TOTALIZ(TMG_MAST_MONTHLYMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMMM_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMMM_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMMM_DSTARTDATE|date||否|データ開始日                                                                                    |
|TMMM_DENDDATE|date||否|データ終了日                                                                                    |
|TMMM_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMMM_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMMM_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMMM_CTOTALIZATIONID|varchar||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMMM_CMONTHLYCOLUMN|varchar||否|マッピング先カラム名                                                                                |
