# [勤怠]月次集計処理ログ                                                (TMG_MONTHLY_OUTPUT_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMOL_CERRMSG|VARCHAR2||是|エラーメッセージ                      ORACLEのエラーメッセージを格納                                          |
|TMOL_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMOL_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMOL_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMOL_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMOL_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMOL_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMOL_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMOL_DYYYYMM|DATE||否|該当年月                                                                                      |
|TMOL_CSTATUSFLG|VARCHAR2||是|処理ステータス                                                     MGD:TMG_CALCSTATUS            |
|TMOL_NCOUNT|NUMBER||是|処理件数                                                                                      |
|TMOL_CERRCODE|VARCHAR2||是|エラーコード                        ORACLEのエラーコードを格納                                            |
