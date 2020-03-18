# 仕訳項目マスタ                                                     (TMG_MAST_JOURNALIZE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMJ_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMJ_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMJ_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMJ_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMJ_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMJ_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMJ_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMJ_CJOURNALIZINGID|varchar||否|仕訳項目ｺｰﾄﾞ                                                                                  |
|TMJ_CJOURNALIZINGNM|varchar||否|仕訳項目名称                                                                                    |
|TMJ_NOVERTIME_FLG|numeric||是|null|
