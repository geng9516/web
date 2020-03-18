# 手当閾値マスタ                       時間入力タイプの手当ｺｰﾄﾞを合計時間の閾値から中間コードに(TMG_MAST_ALLOWANCEBORDER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMAB_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMAB_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMAB_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMAB_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMAB_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMAB_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMAB_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMAB_CALLOWANCEID|varchar||否|手当コード                                                                                     |
|TMAB_NRANGEFROM|numeric||否|閾値（以上）                                                                                    |
|TMAB_NRANGETO|numeric||否|閾値（以下）                                                                                    |
|TMAB_CMIDDLEID|varchar||否|中間コード                                                                                     |
