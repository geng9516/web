# 勤務時間帯区分マスタ(TMG_MAST_WORKTIMEZONE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMWZ_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ|
|TMWZ_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TMWZ_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日|
|TMWZ_DENDDATE|date||否|ﾃﾞｰﾀ終了日|
|TMWZ_CMODIFIERUSERID|varchar||是|更新者|
|TMWZ_DMODIFIEDDATE|date||是|更新日|
|TMWZ_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TMWZ_NTIMEZONEFROM|numeric||否|開始時刻|
|TMWZ_NTIMEZONETO|numeric||否|終了時刻|
|TMWZ_CTIMEZONE|varchar||否|時間帯区分|
|TMWZ_CTIMEZONENM|varchar||否|時間帯名称|
