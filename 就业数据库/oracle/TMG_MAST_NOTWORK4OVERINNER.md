# 法定内残業計算対象非勤務マスタ                                             (TMG_MAST_NOTWORK4OVERINNER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMNO_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMNO_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMNO_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMNO_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMNO_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMNO_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMNO_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMNO_CMASTER|VARCHAR2||否|マスターコード                                                                                   |
