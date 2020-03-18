# 手当閾値マスタ                       時間入力タイプの手当ｺｰﾄﾞを合計時間の閾値から中間コードに(TMG_MAST_ALLOWANCEBORDER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMAB_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMAB_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMAB_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMAB_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMAB_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMAB_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMAB_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMAB_CALLOWANCEID|VARCHAR2||否|手当コード                                                                                     |
|TMAB_NRANGEFROM|NUMBER||否|閾値（以上）                                                                                    |
|TMAB_NRANGETO|NUMBER||否|閾値（以下）                                                                                    |
|TMAB_CMIDDLEID|VARCHAR2||否|中間コード                                                                                     |
