# 超勤命令マスタ                                                     (TMG_MAST_INSTRUCTIONTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMIT_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMIT_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMIT_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMIT_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMIT_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMIT_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMIT_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMIT_CMASTER|VARCHAR2||否|入力項目ｺｰﾄﾞ                                                                                  |
|TMIT_CINSTRUCTIONTYPEID|VARCHAR2||否|超勤命令ｺｰﾄﾞ                                                                                  |
