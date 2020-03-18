# 超勤手当支給対象判定マスタ                 管理職/非管理職ごとに、給与用項目として使用する仕訳ｺｰﾄﾞ(TMG_MAST_JOURNAL2MANAGER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMJM_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMJM_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMJM_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMJM_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMJM_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMJM_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMJM_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMJM_CJOURNALIZINGID4SALARY_I|varchar||否|給与用中間コード(変換前)                                                                                  |
|TMJM_CMANAGERID|varchar||否|管理職ｺｰﾄﾞ                                                                                   |
|TMJM_CJOURNALIZINGID4SALARY_O|varchar||是|給与用中間コード(変換後)                                                                                  |
