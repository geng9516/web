# 手当コード集計単位マッピングマスタ             日数、月数に変換する日時集計マッピングマスタ項目と手当コード(TMG_MAST_ALLOWANCEUNIT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMAU_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMAU_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMAU_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMAU_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMAU_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMAU_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMAU_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMAU_CMIDDLEID|varchar||否|中間ｺｰﾄﾞ                                                                                    |
|TMAU_CUNITID|varchar||否|集計単位ｺｰﾄﾞ                                                                                  |
