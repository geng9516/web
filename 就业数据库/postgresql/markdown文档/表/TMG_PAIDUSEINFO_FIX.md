# 年休取得修正情報(TMG_PAIDUSEINFO_FIX)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPF_CCUSTOMERID|varchar||否|顧客コード|
|TPF_CCOMPANYID|varchar||否|法人コード|
|TPF_DSTARTDATE|date||否|データ開始日 (1900/01/01)|
|TPF_DENDDATE|date||否|データ終了日 (2222/12/31)|
|TPF_CMODIFIERUSERID|varchar||是|更新者|
|TPF_DMODIFIERDATE|date||是|更新日|
|TPF_CMODIFIERPROGRAMID|varchar||是|更新プログラムＩＤ|
|TPF_CEMPLOYEEID|varchar||否|職員番号|
|TPF_DPAID_HOLIDAY|date||是|基準日|
|TPF_NYYYY|numeric||否|基準日修正年|
|TPF_DPAID_HOLIDAY_FIX|date||是|修正基準日|
|TPF_DKIKANBI_FIX|date||是|修正チェック期間|
|TPF_NUSEDAYS_FIX|numeric||是|修正取得日数|
|TPF_NMUSTDAYS_FIX|numeric||是|修正必要日数|
|TPF_NUSEDAYS_AJDUST|numeric||是|調整取得日数|
|TPF_NUSEHOURS_AJDUST|numeric||是|調整取得時間数|
