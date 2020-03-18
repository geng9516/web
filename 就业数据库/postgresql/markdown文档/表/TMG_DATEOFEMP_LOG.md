# [勤怠]勤務開始日編集ログ                                                (TMG_DATEOFEMP_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDLG_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDLG_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TDLG_CEMPLOYEEID|varchar||否|社員番号                                                                                    |
|TDLG_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TDLG_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TDLG_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TDLG_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TDLG_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TDLG_CMODIFIEDDETAIL|varchar||是|更新内容                                          |
