# [勤怠]エラーチェック用・日別情報詳細           2007/02/23元テーブルのレイアウト変更に伴い修正  (TMG_DAILY_DETAIL_CHECK_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDA_DCREATED|date||是|null|
|TDAD_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDAD_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TDAD_CEMPLOYEEID|varchar||否|社員番号                                                                                      |
|TDAD_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TDAD_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TDAD_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TDAD_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TDAD_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TDAD_NYYYY|numeric||否|該当年                           YYYY                                                        |
|TDAD_DYYYYMM|date||否|該当年月                          YYYY/MM/01                                                  |
|TDAD_DYYYYMMDD|date||否|該当年月日                         YYYY/MM/DD                                                  |
|TDAD_CNOTWORKID|varchar||是|非勤務区分                                                       MGD:TMG_NOTWORK               |
|TDAD_CNOTWORKNAME|varchar||是|非勤務名称                                                                                     |
|TDAD_NOPEN|numeric||是|非勤務開始時刻                                                                                   |
|TDAD_NCLOSE|numeric||是|非勤務終了時刻                                                                                   |
|TDAD_NDELETEFLG|numeric||是|削除フラグ                         0：有効、1：削除済                                                  |
|TDAD_SEQ|numeric||否|SEQ|
|TDAD_CSPARECHAR1|varchar||是|予備文字列1|
|TDAD_CSPARECHAR2|varchar||是|予備文字列2|
|TDAD_CSPARECHAR3|varchar||是|予備文字列3|
|TDAD_CSPARECHAR4|varchar||是|予備文字列4|
|TDAD_CSPARECHAR5|varchar||是|予備文字列5|
|TDAD_NSPARENUM1|numeric||是|予備数値1|
|TDAD_NSPARENUM2|numeric||是|予備数値2|
|TDAD_NSPARENUM3|numeric||是|予備数値3|
|TDAD_NSPARENUM4|numeric||是|予備数値4|
|TDAD_NSPARENUM5|numeric||是|予備数値5|
|TDAD_DSPAREDATE1|date||是|予備日付1|
|TDAD_DSPAREDATE2|date||是|予備日付2|
|TDAD_DSPAREDATE3|date||是|予備日付3|
|TDAD_DSPAREDATE4|date||是|予備日付4|
|TDAD_DSPAREDATE5|date||是|予備日付5|
|TDAD_CCODE01|varchar||是|予備コード1|
|TDAD_CCODENM01|varchar||是|予備コード1コード|
|TDAD_CCODE02|varchar||是|予備コード2|
|TDAD_CCODENM02|varchar||是|予備コード2コード|
|TDAD_CCODE03|varchar||是|予備コード3|
|TDAD_CCODENM03|varchar||是|予備コード3コード|
|TDAD_CCODE04|varchar||是|予備コード4|
|TDAD_CCODENM04|varchar||是|予備コード4コード|
|TDAD_CCODE05|varchar||是|予備コード5|
|TDAD_CCODENM05|varchar||是|予備コード5コード|
|TDAD_CREFNTFNO|varchar||是|申請反映元申請番号|
