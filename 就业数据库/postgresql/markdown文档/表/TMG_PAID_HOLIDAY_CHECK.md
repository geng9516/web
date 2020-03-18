# [勤怠]エラーチェック用・年次休暇情報                                         (TMG_PAID_HOLIDAY_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPH_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TPH_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TPH_CEMPLOYEEID|varchar||否|社員番号                                                                                      |
|TPH_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TPH_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TPH_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TPH_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TPH_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TPH_DYYYYMM|date||否|付与年月                          YYYY/MM/01                                                  |
|TPH_NINVEST|numeric||是|付与日数                                                                                      |
|TPH_NTHROUGHOUT|numeric||是|繰越日数                                                                                      |
|TPH_NADJUST|numeric||是|調整日数(付与)|
|TPH_NSPARENUM1|numeric||是|予備数値１                                                                                     |
|TPH_NSPARENUM2|numeric||是|予備数値２                                                                                     |
|TPH_NSPARENUM3|numeric||是|予備数値３                                                                                     |
|TPH_NSPARENUM4|numeric||是|予備数値４                                                                                     |
|TPH_NSPARENUM5|numeric||是|予備数値５                                                                                     |
|TPH_DSPAREDATE1|date||是|予備日付１                                                                                     |
|TPH_DSPAREDATE2|date||是|予備日付２                                                                                     |
|TPH_DSPAREDATE3|date||是|予備日付３                                                                                     |
|TPH_DSPAREDATE4|date||是|予備日付４                                                                                     |
|TPH_DSPAREDATE5|date||是|予備日付５                                                                                     |
|TPH_NCONFIRM|numeric||是|認定出勤日数(計算)|
|TPH_NADJUST_HOURS|numeric||是|調整時間(付与)|
|TPH_NADJUST_TO|numeric||是|調整日数(繰越)|
|TPH_NADJUST_HOURS_TO|numeric||是|調整時間(繰越)|
|TPH_NCONFIRM_EDIT|numeric||是|認定出勤日数(編集)|
