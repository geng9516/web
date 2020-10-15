# [勤怠]日別情報詳細                    2007/02/23 予定実績を再統合。また、申請番号のカラ(TMG_DAILY_DETAIL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDAD_CCODENM03|VARCHAR2||是|予備コード3コード|
|TDAD_CCODE04|VARCHAR2||是|予備コード4|
|TDAD_CCODENM04|VARCHAR2||是|予備コード4コード|
|TDAD_CCODE05|VARCHAR2||是|予備コード5|
|TDAD_CCODENM05|VARCHAR2||是|予備コード5コード|
|TDAD_CREFNTFNO|VARCHAR2||是|申請反映元申請番号|
|TDAD_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDAD_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TDAD_CEMPLOYEEID|VARCHAR2||否|職員番号                                                                                      |
|TDAD_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TDAD_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TDAD_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TDAD_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TDAD_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TDAD_NYYYY|NUMBER||否|該当年                           YYYY                                                        |
|TDAD_DYYYYMM|DATE||否|該当年月                          YYYY/MM/01                                                  |
|TDAD_DYYYYMMDD|DATE||否|該当年月日                         YYYY/MM/DD                                                  |
|TDAD_CNOTWORKID|VARCHAR2||是|非勤務区分                                                       MGD:TMG_NOTWORK               |
|TDAD_CNOTWORKNAME|VARCHAR2||是|非勤務名称                                                                                     |
|TDAD_NOPEN|NUMBER||是|非勤務開始時刻                                                                                   |
|TDAD_NCLOSE|NUMBER||是|非勤務終了時刻                                                                                   |
|TDAD_NDELETEFLG|NUMBER||是|削除フラグ                         0：有効、1：削除済                                                  |
|TDAD_SEQ|NUMBER||否|SEQ|
|TDAD_CSPARECHAR1|VARCHAR2||是|予備文字列1|
|TDAD_CSPARECHAR2|VARCHAR2||是|予備文字列2|
|TDAD_CSPARECHAR3|VARCHAR2||是|予備文字列3|
|TDAD_CSPARECHAR4|VARCHAR2||是|予備文字列4|
|TDAD_CSPARECHAR5|VARCHAR2||是|予備文字列5|
|TDAD_NSPARENUM1|NUMBER||是|予備数値1|
|TDAD_NSPARENUM2|NUMBER||是|予備数値2|
|TDAD_NSPARENUM3|NUMBER||是|予備数値3|
|TDAD_NSPARENUM4|NUMBER||是|予備数値4|
|TDAD_NSPARENUM5|NUMBER||是|予備数値5|
|TDAD_DSPAREDATE1|DATE||是|予備日付1|
|TDAD_DSPAREDATE2|DATE||是|予備日付2|
|TDAD_DSPAREDATE3|DATE||是|予備日付3|
|TDAD_DSPAREDATE4|DATE||是|予備日付4|
|TDAD_DSPAREDATE5|DATE||是|予備日付5|
|TDAD_CCODE01|VARCHAR2||是|予備コード1|
|TDAD_CCODENM01|VARCHAR2||是|予備コード1コード|
|TDAD_CCODE02|VARCHAR2||是|予備コード2|
|TDAD_CCODENM02|VARCHAR2||是|予備コード2コード|
|TDAD_CCODE03|VARCHAR2||是|予備コード3|
