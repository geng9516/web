# [勤怠]アラートメッセージ格納テーブル                                         (TMG_ALERTMSG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TAM_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TAM_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TAM_CEMPLOYEEID|varchar||否|職員番号                                                                                      |
|TAM_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TAM_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TAM_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TAM_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TAM_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TAM_DDATE|date||否|発生日付                                                                                      |
|TAM_DYYYYMM|date||否|処理月                                                                                       |
|TAM_MODULE|varchar||否|モジュール名                                                                                    |
|TAM_MESSAGE|varchar||是|アラートメッセージ                                                                                 |
