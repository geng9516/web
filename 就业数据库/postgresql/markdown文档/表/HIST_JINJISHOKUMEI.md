# 人事職名歴                         COMPANYから連携した人事発令データに基く職名歴です  (HIST_JINJISHOKUMEI)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HJS_CCUSTOMERID_CK|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|HJS_CCOMPANYID_CK|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|HJS_CEMPLOYEEID_CK|varchar||否|職員番号                                                                                      |
|HJS_DSTARTDATE_CK|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|HJS_DENDDATE|date||是|ﾃﾞｰﾀ終了日                                                                                   |
|HJS_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|HJS_DMODIFIEDDATE|date||是|更新日                                                                                       |
|HJS_CSHOKUMEIKB|varchar||是|職名ｺｰﾄﾞ(人事)                                                                                |
|HJS_CSHOKUMEINM|varchar||是|職名名称(人事)                                                                                  |
