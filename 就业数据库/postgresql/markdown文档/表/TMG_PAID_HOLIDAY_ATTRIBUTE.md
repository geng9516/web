# [勤怠]年次休暇付与属性テーブル                                            (TMG_PAID_HOLIDAY_ATTRIBUTE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPHA_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TPHA_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TPHA_CEMPLOYEEID|varchar||否|社員番号                                                                                      |
|TPHA_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TPHA_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TPHA_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TPHA_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TPHA_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TPHA_NAVGWORKTIME|numeric||否|週平均勤務時間数                                                                                  |
|TPHA_NWORKINGDAYS_WEEK|numeric||否|週の所定労働日数                                                                                  |
|TPHA_CWORKINGDAYS_WEEK|varchar||否|予定勤務パターン|
