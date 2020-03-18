# 勤務時間帯区分マスタ(TMG_MAST_TRANSTIMEZONE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMTZ_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ|
|TMTZ_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TMTZ_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日|
|TMTZ_DENDDATE|date||否|ﾃﾞｰﾀ終了日|
|TMTZ_CMODIFIERUSERID|varchar||是|更新者|
|TMTZ_DMODIFIEDDATE|date||是|更新日|
|TMTZ_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TMTZ_CPHASE|varchar||否|処理フェーズ（変換FUNCTIONのオブジェクト名）|
|TMTZ_CMASTER4SOURCE|varchar||否|変換前マスタコード|
|TMTZ_CMASTER4DEST|varchar||否|変換後マスタコード|
