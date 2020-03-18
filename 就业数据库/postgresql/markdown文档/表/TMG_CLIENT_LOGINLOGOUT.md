# [勤怠]ログインログアウト時刻保持(TMG_CLIENT_LOGINLOGOUT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TCLL_CCUSTOMERID|varchar||否|顧客コード|
|TCLL_CCOMPANYID|varchar||否|法人コード|
|TCLL_CEMPLOYEEID|varchar||否|社員番号|
|TCLL_DSTARTDATE|date||是|データ開始日 固定：1900/01/01|
|TCLL_DENDDATE|date||是|データ終了日 固定：2222/12/31|
|TCLL_CMODIFIERUSERID|varchar||是|更新者|
|TCLL_DMODIFIEDDATE|date||是|更新日|
|TCLL_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TCLL_DYYYYMMDD|date||否|年月日 YYYY/MM/DD|
|TCLL_DLOGIN|date||是|ログイン日時|
|TCLL_NLOGIN|numeric||是|ログイン時刻（数値型）例 1440|
|TCLL_DLOGOUT|date||是|ログアウト日時|
|TCLL_NLOGOUT|numeric||是|ログアウト時刻（数値型）例 1440|
