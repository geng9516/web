# [勤怠]勤務開始日編集ログ                                                (TMG_DATEOFEMP_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDLG_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDLG_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TDLG_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                    |
|TDLG_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TDLG_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TDLG_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TDLG_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TDLG_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TDLG_CMODIFIEDDETAIL|VARCHAR2||是|更新内容                                          |
