# [勤怠]HR連携用(勤怠種別)・連携ワークテーブル (TMG_WORK_HIST_WORKTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TWHW_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ  |
|TWHW_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ |
|TWHW_CEMPLOYEEID|varchar||否|社員番号  |
|TWHW_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日 |
|TWHW_DENDDATE|date||否|ﾃﾞｰﾀ終了日  |
|TWHW_CMODIFIERUSERID|varchar||是|更新者   |
|TWHW_DMODIFIEDDATE|date||是|更新日  |
|TWHW_CMODIFIERPROGRAMID|varchar||是|更新プログラムID |
|TWHW_CWORKTYPEID|varchar||是|勤怠種別 MGD:TMG_WORKTYPE |
|TWHW_CWORKTYPENAME|varchar||是|null|
|TWHW_CMANAGEFLG|varchar||否|null|
