# [勤怠]日別情報操作ログ(TMG_DAILY_ACTIONLOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDAL_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01|
|TDAL_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ|
|TDAL_CEMPLOYEEID|VARCHAR2||否|社員番号|
|TDAL_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01|
|TDAL_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31|
|TDAL_CMODIFIERUSERID|VARCHAR2||是|更新者|
|TDAL_DMODIFIEDDATE|DATE||是|更新日|
|TDAL_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TDAL_DYYYYMMDD|DATE||否|該当年月日                         YYYY/MM/DD|
|TDAL_NID|NUMBER||是|更新ID|
