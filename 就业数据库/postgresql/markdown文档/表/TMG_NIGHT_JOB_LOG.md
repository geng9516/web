# [勤怠]夜間更新処理ログ                                                (TMG_NIGHT_JOB_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TNJL_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TNJL_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TNJL_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TNJL_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TNJL_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TNJL_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TNJL_CMODIFIERPROGRAMID|varchar||否|更新プログラムID                                                                                 |
|TNJL_DYYYYMM|date||否|対象日                                                                                       |
|TNJL_DOPEN|date||是|処理開始時刻                                                                                    |
|TNJL_DCLOSE|date||是|処理終了時刻                                                                                    |
|TNJL_CSTATUSFLG|varchar||是|処理ステータス                                                     MGD:TMG_CALCSTATUS            |
|TNJL_NCOUNT|numeric||是|処理件数                                                                                      |
|TNJL_CERRCODE|varchar||是|エラーコード                        ORACLEのエラーコードを格納                                            |
|TNJL_CERRMSG|varchar||是|エラーメッセージ                      ORACLEのエラーメッセージを格納                                          |
