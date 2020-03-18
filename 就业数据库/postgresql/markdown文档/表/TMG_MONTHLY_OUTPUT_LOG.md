# [勤怠]月次集計処理ログ                                                (TMG_MONTHLY_OUTPUT_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMOL_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMOL_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMOL_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMOL_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMOL_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMOL_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMOL_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMOL_DYYYYMM|date||否|該当年月                                                                                      |
|TMOL_CSTATUSFLG|varchar||是|処理ステータス                                                     MGD:TMG_CALCSTATUS            |
|TMOL_NCOUNT|numeric||是|処理件数                                                                                      |
|TMOL_CERRCODE|varchar||是|エラーコード                        ORACLEのエラーコードを格納                                            |
|TMOL_CERRMSG|varchar||是|エラーメッセージ                      ORACLEのエラーメッセージを格納                                          |
