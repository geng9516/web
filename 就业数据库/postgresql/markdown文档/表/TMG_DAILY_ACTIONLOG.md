# [勤怠]日別情報操作ログ(TMG_DAILY_ACTIONLOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDAL_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01|
|TDAL_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TDAL_CEMPLOYEEID|varchar||否|職員番号|
|TDAL_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01|
|TDAL_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31|
|TDAL_CMODIFIERUSERID|varchar||是|更新者|
|TDAL_DMODIFIEDDATE|date||是|更新日|
|TDAL_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TDAL_DYYYYMMDD|date||否|該当年月日                         YYYY/MM/DD|
|TDAL_NID|numeric||是|更新ID|
