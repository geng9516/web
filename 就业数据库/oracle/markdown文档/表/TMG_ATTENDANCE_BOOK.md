# [勤怠]出勤簿情報                                                   (TMG_ATTENDANCE_BOOK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMA_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMA_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMA_CEMPLOYEEID|VARCHAR2||否|職員番号                                                                                      |
|TMA_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMA_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMA_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMA_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMA_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMA_NYYYY|NUMBER||否|該当年                           YYYY                                                        |
|TMA_DYYYYMM|DATE||否|該当年月                          YYYY/MM/01                                                  |
|TMA_WORKTYPEID01|VARCHAR2||是|出勤区分：1日                                                                                   |
|TMA_WORKCONTENT01|VARCHAR2||是|出勤内容：1日                                                                                   |
|TMA_WORKTYPEID02|VARCHAR2||是|出勤区分：2日                                                                                   |
|TMA_WORKCONTENT02|VARCHAR2||是|出勤内容：2日                                                                                   |
|TMA_WORKTYPEID03|VARCHAR2||是|出勤区分：3日                                                                                   |
|TMA_WORKCONTENT03|VARCHAR2||是|出勤内容：3日                                                                                   |
|TMA_WORKTYPEID04|VARCHAR2||是|出勤区分：4日                                                                                   |
|TMA_WORKCONTENT04|VARCHAR2||是|出勤内容：4日                                                                                   |
|TMA_WORKTYPEID05|VARCHAR2||是|出勤区分：5日                                                                                   |
|TMA_WORKCONTENT05|VARCHAR2||是|出勤内容：5日                                                                                   |
|TMA_WORKTYPEID06|VARCHAR2||是|出勤区分：6日                                                                                   |
|TMA_WORKCONTENT06|VARCHAR2||是|出勤内容：6日                                                                                   |
|TMA_WORKTYPEID07|VARCHAR2||是|出勤区分：7日                                                                                   |
|TMA_WORKCONTENT07|VARCHAR2||是|出勤内容：7日                                                                                   |
|TMA_WORKTYPEID08|VARCHAR2||是|出勤区分：8日                                                                                   |
|TMA_WORKCONTENT08|VARCHAR2||是|出勤内容：8日                                                                                   |
|TMA_WORKTYPEID09|VARCHAR2||是|出勤区分：9日                                                                                   |
|TMA_WORKCONTENT09|VARCHAR2||是|出勤内容：9日                                                                                   |
|TMA_WORKTYPEID10|VARCHAR2||是|出勤区分：10日                                                                                  |
|TMA_WORKCONTENT10|VARCHAR2||是|出勤内容：10日                                                                                  |
|TMA_WORKTYPEID11|VARCHAR2||是|出勤区分：11日                                                                                  |
|TMA_WORKCONTENT11|VARCHAR2||是|出勤内容：11日                                                                                  |
|TMA_WORKTYPEID12|VARCHAR2||是|出勤区分：12日                                                                                  |
|TMA_WORKCONTENT12|VARCHAR2||是|出勤内容：12日                                                                                  |
|TMA_WORKTYPEID13|VARCHAR2||是|出勤区分：13日                                                                                  |
|TMA_WORKCONTENT13|VARCHAR2||是|出勤内容：13日                                                                                  |
|TMA_WORKTYPEID14|VARCHAR2||是|出勤区分：14日                                                                                  |
|TMA_WORKCONTENT14|VARCHAR2||是|出勤内容：14日                                                                                  |
|TMA_WORKTYPEID15|VARCHAR2||是|出勤区分：15日                                                                                  |
|TMA_WORKCONTENT15|VARCHAR2||是|出勤内容：15日                                                                                  |
|TMA_WORKTYPEID16|VARCHAR2||是|出勤区分：16日                                                                                  |
|TMA_WORKCONTENT16|VARCHAR2||是|出勤内容：16日                                                                                  |
|TMA_WORKTYPEID17|VARCHAR2||是|出勤区分：17日                                                                                  |
|TMA_WORKCONTENT17|VARCHAR2||是|出勤内容：17日                                                                                  |
|TMA_WORKTYPEID18|VARCHAR2||是|出勤区分：18日                                                                                  |
|TMA_WORKCONTENT18|VARCHAR2||是|出勤内容：18日                                                                                  |
|TMA_WORKTYPEID19|VARCHAR2||是|出勤区分：19日                                                                                  |
|TMA_WORKCONTENT19|VARCHAR2||是|出勤内容：19日                                                                                  |
|TMA_WORKTYPEID20|VARCHAR2||是|出勤区分：20日                                                                                  |
|TMA_WORKCONTENT20|VARCHAR2||是|出勤内容：20日                                                                                  |
|TMA_WORKTYPEID21|VARCHAR2||是|出勤区分：21日                                                                                  |
|TMA_WORKCONTENT21|VARCHAR2||是|出勤内容：21日                                                                                  |
|TMA_WORKTYPEID22|VARCHAR2||是|出勤区分：22日                                                                                  |
|TMA_WORKCONTENT22|VARCHAR2||是|出勤内容：22日                                                                                  |
|TMA_WORKTYPEID23|VARCHAR2||是|出勤区分：23日                                                                                  |
|TMA_WORKCONTENT23|VARCHAR2||是|出勤内容：23日                                                                                  |
|TMA_WORKTYPEID24|VARCHAR2||是|出勤区分：24日                                                                                  |
|TMA_WORKCONTENT24|VARCHAR2||是|出勤内容：24日                                                                                  |
|TMA_WORKTYPEID25|VARCHAR2||是|出勤区分：25日                                                                                  |
|TMA_WORKCONTENT25|VARCHAR2||是|出勤内容：25日                                                                                  |
|TMA_WORKTYPEID26|VARCHAR2||是|出勤区分：26日                                                                                  |
|TMA_WORKCONTENT26|VARCHAR2||是|出勤内容：26日                                                                                  |
|TMA_WORKTYPEID27|VARCHAR2||是|出勤区分：27日                                                                                  |
|TMA_WORKCONTENT27|VARCHAR2||是|出勤内容：27日                                                                                  |
|TMA_WORKTYPEID28|VARCHAR2||是|出勤区分：28日                                                                                  |
|TMA_WORKCONTENT28|VARCHAR2||是|出勤内容：28日                                                                                  |
|TMA_WORKTYPEID29|VARCHAR2||是|出勤区分：29日                                                                                  |
|TMA_WORKCONTENT29|VARCHAR2||是|出勤内容：29日                                                                                  |
|TMA_WORKTYPEID30|VARCHAR2||是|出勤区分：30日                                                                                  |
|TMA_WORKCONTENT30|VARCHAR2||是|出勤内容：30日                                                                                  |
|TMA_WORKTYPEID31|VARCHAR2||是|出勤区分：31日                                                                                  |
|TMA_WORKCONTENT31|VARCHAR2||是|出勤内容：31日                                                                                  |
|TMA_NPAID_USED_DAYS|NUMBER||是|年休取得日数                                                                                    |
|TMA_NPAID_REST_DAYS|NUMBER||是|年休残日数                                                                                     |
|TMA_NSICHNESS_USED_DAYS|NUMBER||是|病休取得日数                                                                                    |
|TMA_NSPECIAL_USED_DAYS|NUMBER||是|特休取得日数                                                                                    |
|TMA_NPAID_USED_HOURS|NUMBER||是|年休取得時間                                                                                    |
|TMA_NPAID_REST_HOURS|NUMBER||是|年休残時間                                                                                     |
|TMA_NSICHNESS_USED_HOURS|NUMBER||是|病休取得時間数                                                                                   |
|TMA_NSPECIAL_USED_HOURS|NUMBER||是|特休取得時間数                                                                                   |
|TMA_NABSENCE_DAYS|NUMBER||是|欠勤日数                                                                                      |
|TMA_NSHOKU_USED_DAYS|NUMBER||是|職専免(無給)取得日数|
|TMA_NSHOKU_REST_HOURS|NUMBER||是|職専免(無給)取得時間数|
|TMA_NIKUJI_USED_DAYS|NUMBER||是|育児休業取得日数|
|TMA_NIKUJI_REST_HOURS|NUMBER||是|育児部分休業取得時間数|
|TMA_NKAIGO_USED_DAYS|NUMBER||是|介護休業取得日数|
|TMA_NKAIGO_REST_HOURS|NUMBER||是|介護部分休業取得時間数|
|TMA_NJIKO_USED_DAYS|NUMBER||是|自己啓発休業取得日数|
|TMA_NJIKO_REST_HOURS|NUMBER||是|自己啓発部分休業取得時間数|
|TMA_NSHOKU_YU_USED_DAYS|NUMBER||是|職専免(有給)取得日数|
|TMA_NSHOKU_YU_REST_HOURS|NUMBER||是|職専免(有給)取得時数|
|TMA_NKINGYOH_USED_DAYS|NUMBER||是|就業禁止(終日)取得日数|
|TMA_NKINGYOH_REST_HOURS|NUMBER||是|就業禁止(時間)取得時数|
|TMA_NSUM_WORKING_HOURS|NUMBER||是|勤務時間数合計|
