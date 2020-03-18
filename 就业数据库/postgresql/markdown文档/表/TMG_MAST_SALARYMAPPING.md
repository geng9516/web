# 給与テーブルマッピングマスタ                給与データ出力処理において、TMG_DAILY_TOTALI(TMG_MAST_SALARYMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMSM_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMSM_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMSM_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMSM_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMSM_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMSM_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMSM_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMSM_CTOTALIZATIONID|varchar||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMSM_CSALARYCOLUMN|varchar||否|マッピング先カラム名                                                                                |
