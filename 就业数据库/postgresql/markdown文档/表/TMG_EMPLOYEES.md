# [勤怠]基本情報(TMG_EMPLOYEES)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TEM_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ     固定：01|
|TEM_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TEM_CEMPLOYEEID|varchar||否|職員番号|
|TEM_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日|
|TEM_DENDDATE|date||否|ﾃﾞｰﾀ終了日|
|TEM_CMODIFIERUSERID|varchar||是|更新者|
|TEM_DMODIFIEDDATE|date||是|更新日|
|TEM_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TEM_CWORKTYPEID|varchar||是|勤怠種別     MGD:TMG_WORKTYPE|
|TEM_CWORKTYPENAME|varchar||是|勤怠種別名称|
|TEM_CSPARECHAR1|varchar||是|予備文字列１|
|TEM_CSPARECHAR2|varchar||是|予備文字列２|
|TEM_CSPARECHAR3|varchar||是|予備文字列３|
|TEM_CSPARECHAR4|varchar||是|予備文字列４|
|TEM_CSPARECHAR5|varchar||是|予備文字列５|
|TEM_NSPARENUM1|numeric||是|予備数値１|
|TEM_NSPARENUM2|numeric||是|予備数値２|
|TEM_NSPARENUM3|numeric||是|予備数値３|
|TEM_NSPARENUM4|numeric||是|予備数値４|
|TEM_NSPARENUM5|numeric||是|予備数値５|
|TEM_DSPAREDATE1|date||是|予備日付１|
|TEM_DSPAREDATE2|date||是|予備日付２|
|TEM_DSPAREDATE3|date||是|予備日付３|
|TEM_DSPAREDATE4|date||是|予備日付４|
|TEM_DSPAREDATE5|date||是|予備日付５|
|TEM_CMANAGEFLG|varchar||否|管理対象者フラグ|
|TEM_CDAY_DUTY_FLG|varchar||是|日直対象フラグ|
|TEM_CNIGHT_DUTY_FLG|varchar||是|宿直対象フラグ|
