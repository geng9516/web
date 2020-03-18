# 超勤閾値マスタ                                                     (TMG_MAST_OVERTIMEBORDER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMOB_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMOB_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMOB_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMOB_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMOB_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMOB_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMOB_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMOB_COVERTIMEBORDERID|varchar||否|超勤閾値コード                                                                                   |
|TMOB_NRANGEFROM|numeric||否|閾値（以上）                                                                                    |
|TMOB_NRANGETO|numeric||否|閾値（以下）                                                                                    |
