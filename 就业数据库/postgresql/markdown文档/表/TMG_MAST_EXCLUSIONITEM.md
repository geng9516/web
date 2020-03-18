# 除外項目マスタ                       集計処理カテゴリごとに、重複している場合に重複部分を除外する(TMG_MAST_EXCLUSIONITEM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMEI_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMEI_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMEI_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMEI_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMEI_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMEI_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMEI_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMEI_CCATEGORYID|varchar||否|集計カテゴリｺｰﾄﾞ                                                                                |
|TMEI_CMASTER|varchar||否|入力項目ｺｰﾄﾞ                                                                                  |
