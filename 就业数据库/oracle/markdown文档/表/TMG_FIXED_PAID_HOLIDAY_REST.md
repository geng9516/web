# [勤怠]年休固定残日数情報(TMG_FIXED_PAID_HOLIDAY_REST)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TFPH_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01|
|TFPH_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ|
|TFPH_CEMPLOYEEID|VARCHAR2||否|職員番号|
|TFPH_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                      固定：1900/01/01|
|TFPH_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                      固定：2222/12/31|
|TFPH_CMODIFIERUSERID|VARCHAR2||是|更新者|
|TFPH_DMODIFIEDDATE|DATE||是|更新日|
|TFPH_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TFPH_DYYYYMMDD|DATE||否|基準日                          YYYY/MM/DD|
|TFPH_NREST_DAYS|NUMBER||是|残日数|
|TFPH_NREST_HOURS|NUMBER||是|残時間数|
|TFPH_CNOTES|VARCHAR2||是|備考|
|TFPH_NNEXT_THROUGHOUTLMT_DAYS|NUMBER||是|次回付与時繰越上限日数|
|TFPH_NNEXT_THROUGHOUTLMT_HOURS|NUMBER||是|次回付与時繰越上限時間数|
