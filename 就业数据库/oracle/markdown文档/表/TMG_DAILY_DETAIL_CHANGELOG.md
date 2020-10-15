# [勤怠]日別詳細情報変更ログ                                              (TMG_DAILY_DETAIL_CHANGELOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDDCL_NID|NUMBER||否|更新ID                                                                                      |
|TDDCL_NIFBEFOREORAFTER|NUMBER||否|前後区分                          ( 0：前 1：後)                                                  |
|TDDCL_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDDCL_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TDDCL_CEMPLOYEEID|VARCHAR2||否|職員番号                                                                                      |
|TDDCL_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TDDCL_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TDDCL_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TDDCL_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TDDCL_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TDDCL_DYYYYMMDD|DATE||否|該当年月日                         YYYY/MM/DD                                                  |
|TDDCL_CNOTWORKID|VARCHAR2||是|非勤務区分                         MGD:TMG_NOTWORK                                             |
|TDDCL_NOPEN|NUMBER||是|非勤務開始時刻                                                                                   |
|TDDCL_NCLOSE|NUMBER||是|非勤務終了時刻                                                                                   |
|TDDCL_CSPARECHAR1|VARCHAR2||是|予備文字列1                                                                                    |
|TDDCL_CSPARECHAR2|VARCHAR2||是|予備文字列2                                                                                    |
|TDDCL_CSPARECHAR3|VARCHAR2||是|予備文字列3                                                                                    |
|TDDCL_CSPARECHAR4|VARCHAR2||是|予備文字列4                                                                                    |
|TDDCL_CSPARECHAR5|VARCHAR2||是|予備文字列5                                                                                    |
|TDDCL_NSPARENUM1|NUMBER||是|予備数値1                                                                                     |
|TDDCL_NSPARENUM2|NUMBER||是|予備数値2                                                                                     |
|TDDCL_NSPARENUM3|NUMBER||是|予備数値3                                                                                     |
|TDDCL_NSPARENUM4|NUMBER||是|予備数値4                                                                                     |
|TDDCL_NSPARENUM5|NUMBER||是|予備数値5                                                                                     |
|TDDCL_DSPAREDATE1|DATE||是|予備日付1                                                                                     |
|TDDCL_DSPAREDATE2|DATE||是|予備日付2                                                                                     |
|TDDCL_DSPAREDATE3|DATE||是|予備日付3                                                                                     |
|TDDCL_DSPAREDATE4|DATE||是|予備日付4                                                                                     |
|TDDCL_DSPAREDATE5|DATE||是|予備日付5                                                                                     |
|TDDCL_CCODE01|VARCHAR2||是|予備コード1                                                                                    |
|TDDCL_CCODE02|VARCHAR2||是|予備コード2                                                                                    |
|TDDCL_CCODE03|VARCHAR2||是|予備コード3                                                                                    |
|TDDCL_CCODE04|VARCHAR2||是|予備コード4                                                                                    |
|TDDCL_CCODE05|VARCHAR2||是|予備コード5                                                                                    |
