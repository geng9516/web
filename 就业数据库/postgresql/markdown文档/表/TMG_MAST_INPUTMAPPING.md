# 入力項目マッピングマスタ                  日次集計処理において、TMG_DAILYおよびTMG_DAI(TMG_MAST_INPUTMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMIM_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMIM_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMIM_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMIM_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMIM_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMIM_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMIM_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMIM_CTABLE|varchar||否|参照テーブル                                                                                    |
|TMIM_CCOLUMN|varchar||否|参照カラム                                                                                     |
|TMIM_CMASTER|varchar||否|入力項目ｺｰﾄﾞ                                                                                  |
|TMIM_CCATEGORYID|varchar||是|集計カテゴリｺｰﾄﾞ                                                                                |
|TMIM_CTOTALTYPEID|varchar||是|集計タイプｺｰﾄﾞ                     ﾊﾟｯｹｰｼﾞ定数                                                   |
