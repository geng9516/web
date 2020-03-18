# 支給控除情報(TMG_V_CDS_ALLOWDEDUCT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TVCAD_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ|
|TVCAD_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ|
|TVCAD_CEMPLOYEEID|VARCHAR2||否|社員番号|
|TVCAD_CUSERID|VARCHAR2||否|ユーザーID|
|TVCAD_DSTARTDATE|DATE||是|ﾃﾞｰﾀ開始日|
|TVCAD_DENDDATE|DATE||是|ﾃﾞｰﾀ終了日|
|TVCAD_DYYYYMM|DATE||否|年月|
|TVCAD_NCALC001|NUMBER||是|振替休日による(-100/100)減額時間|
|TVCAD_NCALC002|NUMBER||是|代休日の減額時間|
|TVCAD_NCALC003|NUMBER||是|読み替え相殺による減額時間|
|TVCAD_NCALC004|NUMBER||是|月またぎの振替休日による100/100支給対象時間|
