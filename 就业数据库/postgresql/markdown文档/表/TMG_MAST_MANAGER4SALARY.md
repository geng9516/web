# 給与用管理職判定マスタ                   人給発令データから連携した管理職ﾌﾗｸﾞ・指定職ﾌﾗｸﾞを元(TMG_MAST_MANAGER4SALARY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMMS_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMMS_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMMS_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMMS_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMMS_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMMS_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMMS_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMMS_CMANAGERFLG|varchar||否|管理職ﾌﾗｸﾞ                                                                                   |
|TMMS_CSPECIFYFLG|varchar||否|指定職ﾌﾗｸﾞ                                                                                   |
|TMMS_CMANAGERID|varchar||是|管理職ｺｰﾄﾞ                                                                                   |
