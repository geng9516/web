# [勤怠]月次集計処理ログ(ユーザー自動作成スクリプト用、一時退避テーブル)(WORK_TMG_MONTHLY_OUTPUT_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMOL_CCUSTOMERID|varchar||否|null|
|TMOL_CCOMPANYID|varchar||否|null|
|TMOL_DSTARTDATE|date||否|null|
|TMOL_DENDDATE|date||否|null|
|TMOL_CMODIFIERUSERID|varchar||是|null|
|TMOL_DMODIFIEDDATE|date||是|null|
|TMOL_CMODIFIERPROGRAMID|varchar||是|null|
|TMOL_CYYYYMM|date||否|null|
|TMOL_CSTATUSFLG|varchar||是|null|
|TMOL_NCOUNT|numeric||是|null|
|TNOL_CERRCODE|varchar||是|null|
|TNOL_CERRMSG|varchar||是|null|
