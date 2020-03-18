# [勤怠]週次勤務パターンテーブル(エラーチェック用)(TMG_WEEK_PATTERN_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TWP_NID|NUMBER||否|ID|
|TWP_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ|
|TWP_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ|
|TWP_CEMPLOYEEID|VARCHAR2||否|社員番号|
|TWP_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日|
|TWP_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日|
|TWP_CMODIFIERUSERID|VARCHAR2||是|更新者|
|TWP_DMODIFIEDDATE|DATE||是|更新日|
|TWP_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TWP_COPEN1|VARCHAR2||是|勤務開始時刻(日曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE1|VARCHAR2||是|勤務終了時刻(日曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN1|VARCHAR2||是|休憩開始時刻(日曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE1|VARCHAR2||是|休憩終了時刻(日曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG1|VARCHAR2||是|休日フラグ1|
|TWP_COPEN2|VARCHAR2||是|勤務開始時刻(月曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE2|VARCHAR2||是|勤務終了時刻(月曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN2|VARCHAR2||是|休憩開始時刻(月曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE2|VARCHAR2||是|休憩終了時刻(月曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG2|VARCHAR2||是|休日フラグ2|
|TWP_COPEN3|VARCHAR2||是|勤務開始時刻(火曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE3|VARCHAR2||是|勤務終了時刻(火曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN3|VARCHAR2||是|休憩開始時刻(火曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE3|VARCHAR2||是|休憩終了時刻(火曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG3|VARCHAR2||是|休日フラグ3|
|TWP_COPEN4|VARCHAR2||是|勤務開始時刻(水曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE4|VARCHAR2||是|勤務終了時刻(水曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN4|VARCHAR2||是|休憩開始時刻(水曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE4|VARCHAR2||是|休憩終了時刻(水曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG4|VARCHAR2||是|休日フラグ4|
|TWP_COPEN5|VARCHAR2||是|勤務開始時刻(木曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE5|VARCHAR2||是|勤務終了時刻(木曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN5|VARCHAR2||是|休憩開始時刻(木曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE5|VARCHAR2||是|休憩終了時刻(木曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG5|VARCHAR2||是|休日フラグ5|
|TWP_COPEN6|VARCHAR2||是|勤務開始時刻(金曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE6|VARCHAR2||是|勤務終了時刻(金曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN6|VARCHAR2||是|休憩開始時刻(金曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE6|VARCHAR2||是|休憩終了時刻(金曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG6|VARCHAR2||是|休日フラグ6|
|TWP_COPEN7|VARCHAR2||是|勤務開始時刻(土曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE7|VARCHAR2||是|勤務終了時刻(土曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN7|VARCHAR2||是|休憩開始時刻(土曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE7|VARCHAR2||是|休憩終了時刻(土曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG7|VARCHAR2||是|休日フラグ7|
