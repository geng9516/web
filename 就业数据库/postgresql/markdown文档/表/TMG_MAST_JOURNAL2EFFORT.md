# エフォート項目中間コードマッピングマスタ          主契約・従契約の区分と、仕訳ｺｰﾄﾞの組み合わせ→ｴﾌｫｰﾄ(TMG_MAST_JOURNAL2EFFORT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMJE_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMJE_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMJE_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMJE_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMJE_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMJE_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMJE_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMJE_CJOURNALIZINGID|varchar||否|仕訳項目ｺｰﾄﾞ                                                                                  |
|TMJE_CEFFORTID|varchar||否|ｴﾌｫｰﾄ区分                       ｴﾌｫｰﾄ用変換ｺｰﾄﾞ ﾊﾟｯｹｰｼﾞ定数：1：主契約 2                              |
|TMJE_CMIDDLEID|varchar||否|中間ｺｰﾄﾞ                                                                                    |
