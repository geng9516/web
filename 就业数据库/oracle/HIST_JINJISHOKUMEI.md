# 人事職名歴                         COMPANYから連携した人事発令データに基く職名歴です  (HIST_JINJISHOKUMEI)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HJS_CCUSTOMERID_CK|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|HJS_CCOMPANYID_CK|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|HJS_CEMPLOYEEID_CK|VARCHAR2||否|職員番号                                                                                      |
|HJS_DSTARTDATE_CK|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|HJS_DENDDATE|DATE||是|ﾃﾞｰﾀ終了日                                                                                   |
|HJS_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|HJS_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|HJS_CSHOKUMEIKB|VARCHAR2||是|職名ｺｰﾄﾞ(人事)                                                                                |
|HJS_CSHOKUMEINM|VARCHAR2||是|職名名称(人事)                                                                                  |
