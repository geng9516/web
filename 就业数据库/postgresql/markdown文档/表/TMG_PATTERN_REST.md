# 勤務パターン休憩情報(TMG_PATTERN_REST)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPR_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ|
|TPR_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TPR_CSECTIONID|varchar||是|部局ｺｰﾄﾞ|
|TPR_CGROUPID|varchar||是|グループコード|
|TPR_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日|
|TPR_DENDDATE|date||否|ﾃﾞｰﾀ終了日|
|TPR_CMODIFIERUSERID|varchar||是|更新者|
|TPR_DMODIFIEDDATE|date||是|更新日|
|TPR_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TPR_CPATTERNID|varchar||否|勤務パターンID|
|TPR_SEQ|numeric||否|並び順|
|TPR_NRESTOPEN|numeric||是|休憩開始時間|
|TPR_NRESTCLOSE|numeric||是|休憩終了時間|
