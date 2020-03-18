# [勤怠]エラーチェック用・権限設定             データ開始日、終了日は親となる部署のデータ開始日、終了日とす(TMG_EVALUATER_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TEV_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TEV_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TEV_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                      |
|TEV_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TEV_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TEV_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TEV_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TEV_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TEV_CSECTIONID|VARCHAR2||是|担当部署コード                                                     MO:MO_CSECTIONID_CK           |
|TEV_CGROUPID|VARCHAR2||是|担当グループコード                     グループでなく部署に対する設定の場合、null       TMG_GROUP：TGR_CSECTIONID      |
|TEV_CEDITABLEFLG|VARCHAR2||是|編集可能フラグ                       権限設定の変更可/不可                   MGD:TMG_ONOFF                 |
|TEV_CRESULTS|VARCHAR2||是|権限：勤怠承認                       勤怠承認コンテンツの使用可/不可              MGD:TMG_ONOFF                 |
|TEV_CNOTIFICATION|VARCHAR2||是|権限：休暇休出承認                     休暇・休出承認コンテンツの使用可/不可           MGD:TMG_ONOFF                 |
|TEV_COVERTIME|VARCHAR2||是|権限：超過勤務指示                     超過勤務指示コンテンツの使用可/不可            MGD:TMG_ONOFF                 |
|TEV_CSCHEDULE|VARCHAR2||是|権限：予定作成                       予定作成コンテンツの使用可/不可              MGD:TMG_ONOFF                 |
|TEV_CAUTHORITY|VARCHAR2||是|権限：権限付与                       権限付与コンテンツの使用可/不可              MGD:TMG_ONOFF                 |
|TEV_CADMINFLG|VARCHAR2||是|人事管理ﾌﾗｸﾞ                                                                                   |
|TEV_CSECTIONEVALUATER|VARCHAR2||否|null|
|TEV_CMONTHLYAPPROVAL|VARCHAR2||否|権限：勤怠承認(月次)|
|TEV_CAPPROVAL_LEVEL|VARCHAR2||是|決裁レベル|
