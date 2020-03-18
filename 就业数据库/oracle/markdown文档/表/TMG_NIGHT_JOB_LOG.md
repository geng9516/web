# [勤怠]夜間更新処理ログ                                                (TMG_NIGHT_JOB_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TNJL_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TNJL_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TNJL_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TNJL_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TNJL_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TNJL_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TNJL_CMODIFIERPROGRAMID|VARCHAR2||否|更新プログラムID                                                                                 |
|TNJL_DYYYYMM|DATE||否|対象日                                                                                       |
|TNJL_DOPEN|DATE||是|処理開始時刻                                                                                    |
|TNJL_DCLOSE|DATE||是|処理終了時刻                                                                                    |
|TNJL_CSTATUSFLG|VARCHAR2||是|処理ステータス                                                     MGD:TMG_CALCSTATUS            |
|TNJL_NCOUNT|NUMBER||是|処理件数                                                                                      |
|TNJL_CERRCODE|VARCHAR2||是|エラーコード                        ORACLEのエラーコードを格納                                            |
|TNJL_CERRMSG|VARCHAR2||是|エラーメッセージ                      ORACLEのエラーメッセージを格納                                          |
