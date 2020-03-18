# 年5日時季指定取得情報(TMG_ACQUIRED5DAYSHOLIDAY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TA_CCUSTOMERID|varchar||否|顧客コード|
|TA_CCOMPANYID|varchar||否|法人コード|
|TA_DSTARTDATE|date||否|データ開始日 (1900/01/01)|
|TA_DENDDATE|date||否|データ終了日 (2222/12/31)|
|TA_CMODIFIERUSERID|varchar||是|更新者|
|TA_DMODIFIERDATE|date||是|更新日|
|TA_CMODIFIERPROGRAMID|varchar||是|更新プログラムＩＤ|
|TA_CEMPLOYEEID|varchar||否|職員番号|
|TA_CUSERID|varchar||否|ユーザID|
|TA_DPAID_HOLIDAY|date||否|付与日|
|TA_NHOLIDAY_DAYS|numeric||否|付与日数|
|TA_DBASEDATE_START|date||是|基準日期間（開始日）|
|TA_DBASEDATE_END|date||是|基準日期間（終了日）|
|TA_NNECESSARY_DAYS|numeric||是|取得必要日数|
|TA_CDUPLICATEFLG|varchar||是|重複FLG|
