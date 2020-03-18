# [勤怠]月次集計処理ログ(部局別)                                           (TMG_MONTHLY_OUTPUT_LOG_SEC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMOLS_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMOLS_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMOLS_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMOLS_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMOLS_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMOLS_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMOLS_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMOLS_DYYYYMM|DATE||否|該当年月                                                                                      |
|TMOLS_CSECTIONID|VARCHAR2||否|所属コード                                                                                     |
|TMOLS_NJOBNO|NUMBER||否|ジョブNo                                                                                     |
|TMOLS_CSTATUSFLG|VARCHAR2||是|処理ステータス                                                     MGD:TMG_CALCSTATUS            |
|TMOLS_NCOUNT|NUMBER||是|処理件数                                                                                      |
|TMOLS_CERRCODE|VARCHAR2||是|エラーコード                        ORACLEのエラーコードを格納                                            |
|TMOLS_CERRMSG|VARCHAR2||是|エラーメッセージ                      ORACLEのエラーメッセージを格納                                          |
