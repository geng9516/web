# [初期移行]年次休暇情報反映用テーブル    (DT_TMG_PAID_HOLIDAY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPH_NSPARENUM2|NUMBER||是|予備数値２                                                |
|TPH_NSPARENUM3|NUMBER||是|予備数値３                                                |
|TPH_NSPARENUM4|NUMBER||是|予備数値４                                                |
|TPH_NSPARENUM5|NUMBER||是|予備数値５                                                |
|TPH_DSPAREDATE1|DATE||是|予備日付１                                                |
|TPH_DSPAREDATE2|DATE||是|予備日付２                                                |
|TPH_DSPAREDATE3|DATE||是|予備日付３                                                |
|TPH_DSPAREDATE4|DATE||是|予備日付４                                                |
|TPH_DSPAREDATE5|DATE||是|予備日付５                                                |
|TPH_NCONFIRM|NUMBER||是|認定出勤日数(計算)                                           |
|TPH_NADJUST_HOURS|NUMBER||是|調整時間(付与)                                             |
|TPH_NADJUST_TO|NUMBER||是|調整日数(繰越)                                             |
|TPH_NADJUST_HOURS_TO|NUMBER||是|調整時間(繰越)                                             |
|TPH_NCONFIRM_EDIT|NUMBER||是|認定出勤日数(編集)                                           |
|TPH_CCUSTOMERID|VARCHAR2||是|顧客ｺｰﾄﾞ                                               |
|TPH_CCOMPANYID|VARCHAR2||是|法人ｺｰﾄﾞ                                               |
|TPH_CEMPLOYEEID|VARCHAR2||是|職員番号                                                 |
|TPH_DSTARTDATE|DATE||是|ﾃﾞｰﾀ開始日                                              |
|TPH_DENDDATE|DATE||是|ﾃﾞｰﾀ終了日                                              |
|TPH_CMODIFIERUSERID|VARCHAR2||是|更新者                                                  |
|TPH_DMODIFIEDDATE|DATE||是|更新日                                                  |
|TPH_CMODIFIERPROGRAM|VARCHAR2||是|更新プログラムID                                            |
|TPH_DYYYYMM|DATE||是|付与年月                                                 |
|TPH_DYYYYMMDD|DATE||是|付与年月日                                                |
|TPH_NINVEST|NUMBER||是|付与日数                                                 |
|TPH_NTHROUGHOUT|NUMBER||是|繰越日数                                                 |
|TPH_NADJUST|NUMBER||是|調整日数(付与)                                             |
|TPH_NSPARENUM1|NUMBER||是|予備数値１                                                |
