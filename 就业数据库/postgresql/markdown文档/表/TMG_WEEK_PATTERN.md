# [勤怠]週次勤務パターンテーブル(エラーチェック用)(TMG_WEEK_PATTERN)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TWP_NID|numeric||否|ID|
|TWP_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ|
|TWP_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TWP_CEMPLOYEEID|varchar||否|社員番号|
|TWP_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日|
|TWP_DENDDATE|date||否|ﾃﾞｰﾀ終了日|
|TWP_CMODIFIERUSERID|varchar||是|更新者|
|TWP_DMODIFIEDDATE|date||是|更新日|
|TWP_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TWP_COPEN1|varchar||是|勤務開始時刻(日曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE1|varchar||是|勤務終了時刻(日曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN1|varchar||是|休憩開始時刻(日曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE1|varchar||是|休憩終了時刻(日曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG1|varchar||是|休日フラグ1|
|TWP_COPEN2|varchar||是|勤務開始時刻(月曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE2|varchar||是|勤務終了時刻(月曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN2|varchar||是|休憩開始時刻(月曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE2|varchar||是|休憩終了時刻(月曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG2|varchar||是|休日フラグ2|
|TWP_COPEN3|varchar||是|勤務開始時刻(火曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE3|varchar||是|勤務終了時刻(火曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN3|varchar||是|休憩開始時刻(火曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE3|varchar||是|休憩終了時刻(火曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG3|varchar||是|休日フラグ3|
|TWP_COPEN4|varchar||是|勤務開始時刻(水曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE4|varchar||是|勤務終了時刻(水曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN4|varchar||是|休憩開始時刻(水曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE4|varchar||是|休憩終了時刻(水曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG4|varchar||是|休日フラグ4|
|TWP_COPEN5|varchar||是|勤務開始時刻(木曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE5|varchar||是|勤務終了時刻(木曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN5|varchar||是|休憩開始時刻(木曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE5|varchar||是|休憩終了時刻(木曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG5|varchar||是|休日フラグ5|
|TWP_COPEN6|varchar||是|勤務開始時刻(金曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE6|varchar||是|勤務終了時刻(金曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN6|varchar||是|休憩開始時刻(金曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE6|varchar||是|休憩終了時刻(金曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG6|varchar||是|休日フラグ6|
|TWP_COPEN7|varchar||是|勤務開始時刻(土曜)HH:MI               休日はNULL指定|
|TWP_CCLOSE7|varchar||是|勤務終了時刻(土曜)HH:MI               休日はNULL指定|
|TWP_CRESTOPEN7|varchar||是|休憩開始時刻(土曜)HH:MI               休日はNULL指定|
|TWP_CRESTCLOSE7|varchar||是|休憩終了時刻(土曜)HH:MI               休日はNULL指定|
|TWP_CHOLFLG7|varchar||是|休日フラグ7|
