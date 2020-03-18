# 日次集計制御マスタ                                                   (TMG_MAST_CTL4CALCDAILY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMCC_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMCC_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMCC_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMCC_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMCC_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMCC_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMCC_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMCC_CSTATUSFLG|varchar||否|ステータスフラグ                                                                                  |
