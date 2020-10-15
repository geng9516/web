# [勤怠]年次休暇付与属性テーブル                                            (TMG_PAID_HOLIDAY_ATTRIBUTE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPHA_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TPHA_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TPHA_CEMPLOYEEID|VARCHAR2||否|職員番号                                                                                      |
|TPHA_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TPHA_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TPHA_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TPHA_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TPHA_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TPHA_NAVGWORKTIME|NUMBER||否|週平均勤務時間数                                                                                  |
|TPHA_NWORKINGDAYS_WEEK|NUMBER||否|週の所定労働日数                                                                                  |
|TPHA_CWORKINGDAYS_WEEK|VARCHAR2||否|予定勤務パターン|
