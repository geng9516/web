# [勤怠]初期移行用連携データ格納テーブル(TMG_IF_INITIALIZE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TII_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TII_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TII_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                      |
|TII_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TII_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TII_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TII_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TII_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TII_DBEGINDATE|DATE||否|利用開始年月                        YYYYMM                                                      |
|TII_NPAID_REST_DAYS|NUMBER||否|年休残日数                                                                                     |
|TII_NPAID_REST_HOURS|NUMBER||否|年休残時間数                        分単位で格納                                                      |
