# 手当コード集計単位マッピングマスタ             日数、月数に変換する日時集計マッピングマスタ項目と手当コード(TMG_MAST_ALLOWANCEUNIT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMAU_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMAU_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMAU_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMAU_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMAU_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMAU_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMAU_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMAU_CMIDDLEID|VARCHAR2||否|中間ｺｰﾄﾞ                                                                                    |
|TMAU_CUNITID|VARCHAR2||否|集計単位ｺｰﾄﾞ                                                                                  |
