# [勤怠]権限設定  データ開始日、終了日は親となる部署のデータ開始日、終了日とする(WORK_TMG_EVALUATER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TEV_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01|
|TEV_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TEV_CEMPLOYEEID|varchar||否|職員番号|
|TEV_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日|
|TEV_DENDDATE|date||否|ﾃﾞｰﾀ終了日|
|TEV_CMODIFIERUSERID|varchar||是|更新者|
|TEV_DMODIFIEDDATE|date||是|更新日|
|TEV_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TEV_CSECTIONID|varchar||否|担当部署コード                                                     MO:MO_CSECTIONID_CK|
|TEV_CGROUPID|varchar||否|担当グループコード                     グループでなく部署に対する設定の場合、null       TMG_GROUP：TGR_CSECTIONID|
|TEV_CEDITABLEFLG|varchar||是|編集可能フラグ                       権限設定の変更可/不可                   MGD:TMG_ONOFF|
|TEV_CRESULTS|varchar||是|権限：勤怠承認                       勤怠承認コンテンツの使用可/不可              MGD:TMG_ONOFF|
|TEV_CNOTIFICATION|varchar||是|権限：休暇休出承認                     休暇・休出承認コンテンツの使用可/不可           MGD:TMG_ONOFF|
|TEV_COVERTIME|varchar||是|権限：超過勤務指示                     超過勤務指示コンテンツの使用可/不可            MGD:TMG_ONOFF|
|TEV_CSCHEDULE|varchar||是|権限：予定作成                       予定作成コンテンツの使用可/不可              MGD:TMG_ONOFF|
|TEV_CAUTHORITY|varchar||是|権限：権限付与                       権限付与コンテンツの使用可/不可              MGD:TMG_ONOFF|
|TEV_CADMINFLG|varchar||是|人事管理ﾌﾗｸﾞ|
|TEV_CSECTIONEVALUATER|varchar||否|担当部署デフォルト承認者フラグ|
|TEV_CMONTHLYAPPROVAL|varchar||否|権限：勤怠承認(月次)|
|TEV_CAPPROVAL_LEVEL|varchar||是|null|
