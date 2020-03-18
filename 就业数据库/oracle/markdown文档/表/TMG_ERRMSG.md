# [勤怠]エラーメッセージ格納テーブル                                          (TMG_ERRMSG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TER_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TER_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TER_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TER_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TER_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TER_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TER_CMODIFIERPROGRAMID|VARCHAR2||否|更新プログラムID                                                                                 |
|TER_CERRCODE|VARCHAR2||是|エラーコード                                                                                    |
|TER_CLANGUAGE|VARCHAR2||是|言語                                                                                        |
