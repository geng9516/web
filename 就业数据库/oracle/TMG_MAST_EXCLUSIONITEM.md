# 除外項目マスタ                       集計処理カテゴリごとに、重複している場合に重複部分を除外する(TMG_MAST_EXCLUSIONITEM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMEI_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMEI_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMEI_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMEI_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMEI_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMEI_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMEI_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMEI_CCATEGORYID|VARCHAR2||否|集計カテゴリｺｰﾄﾞ                                                                                |
|TMEI_CMASTER|VARCHAR2||否|入力項目ｺｰﾄﾞ                                                                                  |
