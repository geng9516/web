# [勤怠]エラーメッセージ格納テーブル                                          (TMG_ERRMSG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TER_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TER_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TER_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TER_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TER_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TER_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TER_CMODIFIERPROGRAMID|varchar||否|更新プログラムID                                                                                 |
|TER_CERRCODE|varchar||是|エラーコード                                                                                    |
|TER_CLANGUAGE|varchar||是|言語                                                                                        |
