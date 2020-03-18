# [勤怠]個人別勤務パターン(TMG_PERSONAL_PATTERN)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPPA_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01|
|TPPA_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TPPA_CEMPLOYEEID|varchar||否|社員番号|
|TPPA_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日|
|TPPA_DENDDATE|date||否|ﾃﾞｰﾀ終了日|
|TPPA_CMODIFIERUSERID|varchar||是|更新者|
|TPPA_DMODIFIEDDATE|date||是|更新日|
|TPPA_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TPPA_NAVGWORKTIME|numeric||否|週平均勤務時間|
|TPPA_CWORKINGDAYS_WEEK|varchar||否|予定勤務パターン|
|TPPA_NKINMU_NUM1|numeric||是|勤務コード1|
|TPPA_NOPEN1|numeric||是|勤務開始時刻1 HH:MI → 分数|
|TPPA_NCLOSE1|numeric||是|勤務終了時刻1 HH:MI → 分数|
|TPPA_NRESTOPEN1|numeric||是|休憩開始時刻1 HH:MI → 分数|
|TPPA_NRESTCLOSE1|numeric||是|休憩終了時刻1 HH:MI → 分数|
|TPPA_CYOBI1|varchar||是|曜日1|
|TPPA_CHOLFLG1|varchar||是|休日フラグ1|
|TPPA_NKINMU_NUM2|numeric||是|勤務コード2|
|TPPA_NOPEN2|numeric||是|勤務開始時刻2 HH:MI → 分数|
|TPPA_NCLOSE2|numeric||是|勤務終了時刻2 HH:MI → 分数|
|TPPA_NRESTOPEN2|numeric||是|休憩開始時刻2 HH:MI → 分数|
|TPPA_NRESTCLOSE2|numeric||是|休憩終了時刻2 HH:MI → 分数|
|TPPA_CYOBI2|varchar||是|曜日2|
|TPPA_CHOLFLG2|varchar||是|休日フラグ2|
|TPPA_NKINMU_NUM3|numeric||是|勤務コード3|
|TPPA_NOPEN3|numeric||是|勤務開始時刻3 HH:MI → 分数|
|TPPA_NCLOSE3|numeric||是|勤務終了時刻3 HH:MI → 分数|
|TPPA_NRESTOPEN3|numeric||是|休憩開始時刻3 HH:MI → 分数|
|TPPA_NRESTCLOSE3|numeric||是|休憩終了時刻3 HH:MI → 分数|
|TPPA_CYOBI3|varchar||是|曜日3|
|TPPA_CHOLFLG3|varchar||是|休日フラグ3|
|TPPA_NKINMU_NUM4|numeric||是|勤務コード4|
|TPPA_NOPEN4|numeric||是|勤務開始時刻4 HH:MI → 分数|
|TPPA_NCLOSE4|numeric||是|勤務終了時刻4 HH:MI → 分数|
|TPPA_NRESTOPEN4|numeric||是|休憩開始時刻4 HH:MI → 分数|
|TPPA_NRESTCLOSE4|numeric||是|休憩終了時刻4 HH:MI → 分数|
|TPPA_CYOBI4|varchar||是|曜日4|
|TPPA_CHOLFLG4|varchar||是|休日フラグ4|
|TPPA_NKINMU_NUM5|numeric||是|勤務コード5|
|TPPA_NOPEN5|numeric||是|勤務開始時刻5 HH:MI → 分数|
|TPPA_NCLOSE5|numeric||是|勤務終了時刻5 HH:MI → 分数|
|TPPA_NRESTOPEN5|numeric||是|休憩開始時刻5 HH:MI → 分数|
|TPPA_NRESTCLOSE5|numeric||是|休憩終了時刻5 HH:MI → 分数|
|TPPA_CYOBI5|varchar||是|曜日5|
|TPPA_CHOLFLG5|varchar||是|休日フラグ5|
|TPPA_NKINMU_NUM6|numeric||是|勤務コード6|
|TPPA_NOPEN6|numeric||是|勤務開始時刻6 HH:MI → 分数|
|TPPA_NCLOSE6|numeric||是|勤務終了時刻6 HH:MI → 分数|
|TPPA_NRESTOPEN6|numeric||是|休憩開始時刻6 HH:MI → 分数|
|TPPA_NRESTCLOSE6|numeric||是|休憩終了時刻6 HH:MI → 分数|
|TPPA_CYOBI6|varchar||是|曜日6|
|TPPA_CHOLFLG6|varchar||是|休日フラグ6|
|TPPA_NKINMU_NUM7|numeric||是|勤務コード7|
|TPPA_NOPEN7|numeric||是|勤務開始時刻7 HH:MI → 分数|
|TPPA_NCLOSE7|numeric||是|勤務終了時刻7 HH:MI → 分数|
|TPPA_NRESTOPEN7|numeric||是|休憩開始時刻7 HH:MI → 分数|
|TPPA_NRESTCLOSE7|numeric||是|休憩終了時刻7 HH:MI → 分数|
|TPPA_CYOBI7|varchar||是|曜日7|
|TPPA_CHOLFLG7|varchar||是|休日フラグ7|
|TPPA_NWORKINGDAYS_WEEK|numeric||是|週の所定労働日数|
