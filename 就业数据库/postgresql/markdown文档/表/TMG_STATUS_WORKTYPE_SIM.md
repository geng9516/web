# 段階導入シュミレーション登録(TMG_STATUS_WORKTYPE_SIM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TSWS_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ|
|TSWS_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TSWS_DSTARTDATE|date||否|データ開始日|
|TSWS_DENDDATE|date||否|データ終了日|
|TSWS_CMODIFIERUSERID|varchar||是|更新者|
|TSWS_DMODIFIEDDATE|date||是|更新日時|
|TSWS_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TSWS_CSTATUS|varchar||否|ｽﾃｰﾀｽｺｰﾄﾞ|
|TSWS_NJOBID|numeric||是|ジョブID|
|TSWS_DCREATEDATE|date||是|登録日時|
|TSWS_CCREATEUSERID|varchar||是|登録ユーザーID|
|TSWS_DCANCELDATE|date||是|取消日時|
|TSWS_CCANCELUSERID|varchar||是|取消ユーザーID|
