# [勤怠]入社取消用バックアップ・年次休暇情報(TMG_BAK_PAID_HOLIDAY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPH_DCREATE|DATE||是|作成日|
|TPH_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01|
|TPH_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ|
|TPH_CEMPLOYEEID|VARCHAR2||否|職員番号|
|TPH_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                      固定：1900/01/01|
|TPH_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                      固定：2222/12/31|
|TPH_CMODIFIERUSERID|VARCHAR2||是|更新者|
|TPH_DMODIFIEDDATE|DATE||是|更新日|
|TPH_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TPH_DYYYYMM|DATE||否|付与年月                        YYYY/MM/01|
|TPH_DYYYYMMDD|DATE||否|付与年月日                      YYYY/MM/DD|
|TPH_CINVESTTYPE|VARCHAR2||是|付与区分|
|TPH_NINVEST|NUMBER||是|付与日数|
|TPH_NTHROUGHOUT|NUMBER||是|繰越日数|
|TPH_NTHROUGHOUT_HOURS|NUMBER||是|繰越時間|
|TPH_NADJUST|NUMBER||是|調整日数(付与)|
|TPH_NADJUST_HOURS|NUMBER||是|調整時間(付与)|
|TPH_NADJUST_TO|NUMBER||是|調整日数(繰越)|
|TPH_NADJUST_HOURS_TO|NUMBER||是|調整時間(繰越)|
|TPH_NPLANWORKDAY|NUMBER||是|予定出勤日数|
|TPH_NPLANWORKDAY_LIMIT|NUMBER||是|予定の8割日数|
|TPH_NCONFIRM|NUMBER||是|認定出勤日数(計算)|
|TPH_DEXPIRE_INVEST|DATE||是|付与の有効期限|
|TPH_DEXPIRE_ADJUST|DATE||是|調整付与の有効期限|
|TPH_DEXPIRE_ADJUST_TO|DATE||是|調整繰越の有効期限|
|TPH_NLOSE_DAYS|NUMBER||是|喪失日数|
|TPH_NLOSE_HOURS|NUMBER||是|喪失時間数|
|TPH_CCOMMENT|VARCHAR2||是|変更者コメント欄|
