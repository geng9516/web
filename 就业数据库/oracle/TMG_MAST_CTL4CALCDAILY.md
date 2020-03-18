# 日次集計制御マスタ                                                   (TMG_MAST_CTL4CALCDAILY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMCC_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMCC_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMCC_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMCC_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMCC_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMCC_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMCC_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMCC_CSTATUSFLG|VARCHAR2||否|ステータスフラグ                                                                                  |
