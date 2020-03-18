# 手当宿日直マスタ                      宿日直関連の日時集計マッピングマスタ項目と手当ｺｰﾄﾞ、所属(TMG_MAST_ALLOWANCEDUTY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMAD_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMAD_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMAD_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMAD_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMAD_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMAD_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMAD_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMAD_CDUTYID|varchar||否|宿日直ｺｰﾄﾞ                                                                                   |
|TMAD_CMIDDLESECTIONID|varchar||否|中間所属ｺｰﾄﾞ                                                                                  |
|TMAD_CMIDDLEPOSTID|varchar||否|中間職名ｺｰﾄﾞ                                                                                  |
|TMAD_CMIDDLEID|varchar||否|中間ｺｰﾄﾞ                                                                                    |
