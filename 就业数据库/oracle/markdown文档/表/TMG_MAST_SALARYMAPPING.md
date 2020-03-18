# 給与テーブルマッピングマスタ                給与データ出力処理において、TMG_DAILY_TOTALI(TMG_MAST_SALARYMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMSM_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMSM_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMSM_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMSM_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMSM_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMSM_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMSM_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMSM_CTOTALIZATIONID|VARCHAR2||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMSM_CSALARYCOLUMN|VARCHAR2||否|マッピング先カラム名                                                                                |
