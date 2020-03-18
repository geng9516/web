# 手当給与項目用マスタ                    仕訳情報から給与項目単位への変換マスタ           (TMG_MAST_JOURNAL2SALARY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMJS_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMJS_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMJS_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMJS_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMJS_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMJS_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMJS_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMJS_CJOURNALIZINGID|VARCHAR2||否|仕訳項目ｺｰﾄﾞ                                                                                  |
|TMJS_CMIDDLEID|VARCHAR2||否|中間ｺｰﾄﾞ                                                                                    |
