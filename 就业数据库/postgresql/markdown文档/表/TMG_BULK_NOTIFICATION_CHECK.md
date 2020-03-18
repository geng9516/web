# 休暇休業一括登録(TMG_BULK_NOTIFICATION_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TBN_NTBNID|numeric||否|ID|
|TBN_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ|
|TBN_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TBN_CSECTIONID|varchar||否|対象組織ｺｰﾄﾞ|
|TBN_DSTARTDATE|date||否|データ開始日|
|TBN_DENDDATE|date||否|データ終了日|
|TBN_CMODIFIERUSERID|varchar||是|更新者|
|TBN_DMODIFIEDDATE|date||是|更新日時|
|TBN_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TBN_CBULKNTFTYPE|varchar||否|一括登録種類|
|TBN_DBEGIN|date||是|対象期間：開始日|
|TBN_DEND|date||是|対象期間：終了日|
|TBN_CSTATUS|varchar||否|ｽﾃｰﾀｽｺｰﾄﾞ|
|TBN_NJOBID|numeric||是|ジョブID|
|TBN_DCREATEDATE|date||是|一括登録日時|
|TBN_CCREATEUSERID|varchar||是|一括登録ユーザーID|
|TBN_DCANCELDATE|date||是|一括取消日時|
|TBN_CCANCELUSERID|varchar||是|一括取消ユーザーID|
|TBN_NCOUNT|numeric||是|対象人数|
