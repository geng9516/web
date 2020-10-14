# [勤怠]年次休暇情報(TMG_PAID_HOLIDAY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPH_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01|
|TPH_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TPH_CEMPLOYEEID|varchar||否|職員番号|
|TPH_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                      固定：1900/01/01|
|TPH_DENDDATE|date||否|ﾃﾞｰﾀ終了日                      固定：2222/12/31|
|TPH_CMODIFIERUSERID|varchar||是|更新者|
|TPH_DMODIFIEDDATE|date||是|更新日|
|TPH_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TPH_DYYYYMM|date||否|付与年月                        YYYY/MM/01|
|TPH_DYYYYMMDD|date||否|付与年月日                      YYYY/MM/DD|
|TPH_CINVESTTYPE|varchar||是|付与区分|
|TPH_NINVEST|numeric||是|付与日数|
|TPH_NTHROUGHOUT|numeric||是|繰越日数|
|TPH_NTHROUGHOUT_HOURS|numeric||是|繰越時間|
|TPH_NADJUST|numeric||是|調整日数(付与)|
|TPH_NADJUST_HOURS|numeric||是|調整時間(付与)|
|TPH_NADJUST_TO|numeric||是|調整日数(繰越)|
|TPH_NADJUST_HOURS_TO|numeric||是|調整時間(繰越)|
|TPH_NPLANWORKDAY|numeric||是|予定出勤日数|
|TPH_NPLANWORKDAY_LIMIT|numeric||是|予定の8割日数|
|TPH_NCONFIRM|numeric||是|認定出勤日数(計算)|
|TPH_DEXPIRE_INVEST|date||是|付与の有効期限|
|TPH_DEXPIRE_ADJUST|date||是|調整付与の有効期限|
|TPH_DEXPIRE_ADJUST_TO|date||是|調整繰越の有効期限|
|TPH_NLOSE_DAYS|numeric||是|喪失日数|
|TPH_NLOSE_HOURS|numeric||是|喪失時間数|
|TPH_CCOMMENT|varchar||是|変更者コメント欄|
