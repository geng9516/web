# 60hオーバー算定マスタ(TMG_MAST_60HOVERCALC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TM60_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ|
|TM60_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TM60_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日|
|TM60_DENDDATE|date||否|ﾃﾞｰﾀ終了日|
|TM60_CMODIFIERUSERID|varchar||是|更新者|
|TM60_DMODIFIEDDATE|date||是|更新日|
|TM60_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TM60_CWORKTYPEID|varchar||否|勤怠種別ｺｰﾄﾞ|
|TM60_CWORKTIMEZONE|varchar||否|勤務時間帯区分|
|TM60_CWORKINGHOURSTYPEID|varchar||否|労働時間ｺｰﾄﾞ|
|TM60_NPLANWORK_INNER|numeric||否|予定内勤務ﾌﾗｸﾞ       予定内：1　予定外：0|
|TM60_NLEAGALWORK_INNER|numeric||否|法定内勤務ﾌﾗｸﾞ     法定内：1　法定外：0|
|TM60_NINCLUDE60HOVERFLG|numeric||否|閾値合計対象ﾌﾗｸﾞ|
