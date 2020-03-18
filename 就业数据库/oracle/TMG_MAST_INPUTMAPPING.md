# 入力項目マッピングマスタ                  日次集計処理において、TMG_DAILYおよびTMG_DAI(TMG_MAST_INPUTMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMIM_CMASTER|VARCHAR2||否|入力項目ｺｰﾄﾞ                                                                                  |
|TMIM_CCATEGORYID|VARCHAR2||是|集計カテゴリｺｰﾄﾞ                                                                                |
|TMIM_CTOTALTYPEID|VARCHAR2||是|集計タイプｺｰﾄﾞ                     ﾊﾟｯｹｰｼﾞ定数                                                   |
|TMIM_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMIM_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMIM_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMIM_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMIM_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMIM_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMIM_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMIM_CTABLE|VARCHAR2||否|参照テーブル                                                                                    |
|TMIM_CCOLUMN|VARCHAR2||否|参照カラム                                                                                     |
