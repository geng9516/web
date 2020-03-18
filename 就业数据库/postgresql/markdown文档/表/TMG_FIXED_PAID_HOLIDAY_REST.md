# [勤怠]年休固定残日数情報(TMG_FIXED_PAID_HOLIDAY_REST)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TFPH_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01|
|TFPH_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TFPH_CEMPLOYEEID|varchar||否|社員番号|
|TFPH_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                      固定：1900/01/01|
|TFPH_DENDDATE|date||否|ﾃﾞｰﾀ終了日                      固定：2222/12/31|
|TFPH_CMODIFIERUSERID|varchar||是|更新者|
|TFPH_DMODIFIEDDATE|date||是|更新日|
|TFPH_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TFPH_DYYYYMMDD|date||否|基準日                          YYYY/MM/DD|
|TFPH_NREST_DAYS|numeric||是|残日数|
|TFPH_NREST_HOURS|numeric||是|残時間数|
|TFPH_CNOTES|varchar||是|備考|
|TFPH_NNEXT_THROUGHOUTLMT_DAYS|numeric||是|次回付与時繰越上限日数|
|TFPH_NNEXT_THROUGHOUTLMT_HOURS|numeric||是|次回付与時繰越上限時間数|
