# [初期移行]年次休暇情報個人テーブル                                          (MIG_TMG_EMPLOYEE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MTE_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01             |
|MTE_CCOMPANYID|VARCHAR2||是|法人ｺｰﾄﾞ                                          |
|MTE_CEMPLOYEEID|VARCHAR2||是|個人番号                                            |
|MTE_DSTARTDATE|DATE||是|開始日                           1900-01-01 固定     |
|MTE_DENDDATE|DATE||是|終了日                           2222-12-31 固定     |
|MTE_CMODIFIERUSERID|VARCHAR2||是|更新ユーザ                                           |
|MTE_DMODIFIEDDATE|DATE||是|更新日                                             |
|MTE_CMODIFIERPROGRAMID|VARCHAR2||是|更新ﾌﾟﾛｸﾞﾗﾑ                                       |
|MTE_CWORKERTYPE|VARCHAR2||是|勤務形態                                            |
|MTE_NWEEKPETTERN|NUMBER||是|週勤務日数                                           |
|MTE_DDATEOFEMPLOYMENT|DATE||是|採用年月日                                           |
|CPH_NTHROUGHOUT|NUMBER||是|繰越日数                                            |
|CPH_NADJUST|NUMBER||是|繰越時間数                                           |
|MTE_NWEEKHOURS|NUMBER||是|週所定労働時間|
