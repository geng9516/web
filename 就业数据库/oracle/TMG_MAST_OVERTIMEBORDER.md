# 超勤閾値マスタ                                                     (TMG_MAST_OVERTIMEBORDER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMOB_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMOB_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMOB_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMOB_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMOB_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMOB_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMOB_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMOB_COVERTIMEBORDERID|VARCHAR2||否|超勤閾値コード                                                                                   |
|TMOB_NRANGEFROM|NUMBER||否|閾値（以上）                                                                                    |
|TMOB_NRANGETO|NUMBER||否|閾値（以下）                                                                                    |
