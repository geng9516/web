# 仕訳項目マスタ                                                     (TMG_MAST_JOURNALIZE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMJ_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMJ_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMJ_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMJ_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMJ_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMJ_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMJ_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMJ_CJOURNALIZINGID|VARCHAR2||否|仕訳項目ｺｰﾄﾞ                                                                                  |
|TMJ_CJOURNALIZINGNM|VARCHAR2||否|仕訳項目名称                                                                                    |
|TMJ_NOVERTIME_FLG|NUMBER||是|null|
