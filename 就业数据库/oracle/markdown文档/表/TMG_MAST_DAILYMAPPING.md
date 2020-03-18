# 日次テーブルマッピングマスタ                日次集計処理において、TMG_DAILY_TOTALIZAT(TMG_MAST_DAILYMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMDM_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMDM_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMDM_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMDM_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMDM_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMDM_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMDM_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMDM_CTOTALIZATIONID|VARCHAR2||否|集計項目ｺｰﾄﾞ                                                                                  |
|TMDM_NOFFSET|NUMBER||否|集計先勤務年月日（オフセット値）                                                                          |
|TMDM_CDAILYCOLUMN|VARCHAR2||否|マッピング先カラム名                                                                                |
