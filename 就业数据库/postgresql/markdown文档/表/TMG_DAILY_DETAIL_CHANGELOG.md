# [勤怠]日別詳細情報変更ログ                                              (TMG_DAILY_DETAIL_CHANGELOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDDCL_NID|numeric||否|更新ID                                                                                      |
|TDDCL_NIFBEFOREORAFTER|numeric||否|前後区分                          ( 0：前 1：後)                                                  |
|TDDCL_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDDCL_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TDDCL_CEMPLOYEEID|varchar||否|社員番号                                                                                      |
|TDDCL_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TDDCL_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TDDCL_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TDDCL_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TDDCL_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TDDCL_DYYYYMMDD|date||否|該当年月日                         YYYY/MM/DD                                                  |
|TDDCL_CNOTWORKID|varchar||是|非勤務区分                         MGD:TMG_NOTWORK                                             |
|TDDCL_NOPEN|numeric||是|非勤務開始時刻                                                                                   |
|TDDCL_NCLOSE|numeric||是|非勤務終了時刻                                                                                   |
|TDDCL_CSPARECHAR1|varchar||是|予備文字列1                                                                                    |
|TDDCL_CSPARECHAR2|varchar||是|予備文字列2                                                                                    |
|TDDCL_CSPARECHAR3|varchar||是|予備文字列3                                                                                    |
|TDDCL_CSPARECHAR4|varchar||是|予備文字列4                                                                                    |
|TDDCL_CSPARECHAR5|varchar||是|予備文字列5                                                                                    |
|TDDCL_NSPARENUM1|numeric||是|予備数値1                                                                                     |
|TDDCL_NSPARENUM2|numeric||是|予備数値2                                                                                     |
|TDDCL_NSPARENUM3|numeric||是|予備数値3                                                                                     |
|TDDCL_NSPARENUM4|numeric||是|予備数値4                                                                                     |
|TDDCL_NSPARENUM5|numeric||是|予備数値5                                                                                     |
|TDDCL_DSPAREDATE1|date||是|予備日付1                                                                                     |
|TDDCL_DSPAREDATE2|date||是|予備日付2                                                                                     |
|TDDCL_DSPAREDATE3|date||是|予備日付3                                                                                     |
|TDDCL_DSPAREDATE4|date||是|予備日付4                                                                                     |
|TDDCL_DSPAREDATE5|date||是|予備日付5                                                                                     |
|TDDCL_CCODE01|varchar||是|予備コード1                                                                                    |
|TDDCL_CCODE02|varchar||是|予備コード2                                                                                    |
|TDDCL_CCODE03|varchar||是|予備コード3                                                                                    |
|TDDCL_CCODE04|varchar||是|予備コード4                                                                                    |
|TDDCL_CCODE05|varchar||是|予備コード5                                                                                    |
