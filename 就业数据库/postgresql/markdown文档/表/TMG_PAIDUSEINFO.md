# 年休取得情報(TMG_PAIDUSEINFO)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TP_CCUSTOMERID|varchar||否|顧客コード|
|TP_CCOMPANYID|varchar||否|法人コード|
|TP_DSTARTDATE|date||否|データ開始日 (1900/01/01)|
|TP_DENDDATE|date||否|データ終了日 (2222/12/31)|
|TP_CMODIFIERUSERID|varchar||是|更新者|
|TP_DMODIFIERDATE|date||是|更新日|
|TP_CMODIFIERPROGRAMID|varchar||是|更新プログラムＩＤ|
|TP_CEMPLOYEEID|varchar||否|職員番号|
|TP_CUSERID|varchar||否|ユーザID|
|TP_DBASEDATE_START|date||否|基準日期間（開始日）|
|TP_DNTF_SURVEY_START|date||是|年休調査期間（開始日）|
|TP_DNTF_SURVEY_END|date||是|年休調査期間（終了日）|
|TP_NNECESSARY_DAYS|numeric||是|年休取得日数|
