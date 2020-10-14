# [勤怠]トリガーテーブル                                                (TMG_TRIGGER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TTR_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TTR_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TTR_CEMPLOYEEID|varchar||是|職員番号                                                                                      |
|TTR_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TTR_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TTR_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TTR_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TTR_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TTR_CPROGRAMID|varchar||否|プログラムID                                                                                   |
|TTR_NPARAMETER1|numeric||是|数値型パラメータ１                                                                                 |
|TTR_NPARAMETER2|numeric||是|数値型パラメータ２                                                                                 |
|TTR_NPARAMETER3|numeric||是|数値型パラメータ３                                                                                 |
|TTR_NPARAMETER4|numeric||是|数値型パラメータ４                                                                                 |
|TTR_NPARAMETER5|numeric||是|数値型パラメータ５                                                                                 |
|TTR_CPARAMETER1|varchar||是|文字列型パラメータ１                                                                                |
|TTR_CPARAMETER2|varchar||是|文字列型パラメータ２                                                                                |
|TTR_CPARAMETER3|varchar||是|文字列型パラメータ３                                                                                |
|TTR_CPARAMETER4|varchar||是|文字列型パラメータ４                                                                                |
|TTR_CPARAMETER5|varchar||是|文字列型パラメータ５                                                                                |
|TTR_DPARAMETER1|date||是|日付型パラメータ１                                                                                 |
|TTR_DPARAMETER2|date||是|日付型パラメータ２                                                                                 |
|TTR_DPARAMETER3|date||是|日付型パラメータ３                                                                                 |
|TTR_DPARAMETER4|date||是|日付型パラメータ４                                                                                 |
|TTR_DPARAMETER5|date||是|日付型パラメータ５                                                                                 |
