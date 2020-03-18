# 手当宿日直マスタ                      宿日直関連の日時集計マッピングマスタ項目と手当ｺｰﾄﾞ、所属(TMG_MAST_ALLOWANCEDUTY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMAD_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMAD_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMAD_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMAD_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMAD_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMAD_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMAD_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMAD_CDUTYID|VARCHAR2||否|宿日直ｺｰﾄﾞ                                                                                   |
|TMAD_CMIDDLESECTIONID|VARCHAR2||否|中間所属ｺｰﾄﾞ                                                                                  |
|TMAD_CMIDDLEPOSTID|VARCHAR2||否|中間職名ｺｰﾄﾞ                                                                                  |
|TMAD_CMIDDLEID|VARCHAR2||否|中間ｺｰﾄﾞ                                                                                    |
