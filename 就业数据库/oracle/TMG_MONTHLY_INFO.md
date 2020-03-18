# [勤怠]月単位日別情報                   TMG_DAILYのビュー代わり。承認状況一覧、超過勤務指示(TMG_MONTHLY_INFO)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMI_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMI_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMI_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                      |
|TMI_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMI_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMI_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMI_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMI_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMI_DYYYYMM|DATE||否|該当年月                          YYYY/MM/01                                                  |
|TMI_CCONTENTID|VARCHAR2||否|コンテンツタイプ                                                    MGD:TMG_CONTENTID             |
|TMI_CINFO01|VARCHAR2||是|日別情報：1日                                                                                   |
|TMI_CINFO02|VARCHAR2||是|日別情報：2日                                                                                   |
|TMI_CINFO03|VARCHAR2||是|日別情報：3日                                                                                   |
|TMI_CINFO04|VARCHAR2||是|日別情報：4日                                                                                   |
|TMI_CINFO05|VARCHAR2||是|日別情報：5日                                                                                   |
|TMI_CINFO06|VARCHAR2||是|日別情報：6日                                                                                   |
|TMI_CINFO07|VARCHAR2||是|日別情報：7日                                                                                   |
|TMI_CINFO08|VARCHAR2||是|日別情報：8日                                                                                   |
|TMI_CINFO09|VARCHAR2||是|日別情報：9日                                                                                   |
|TMI_CINFO10|VARCHAR2||是|日別情報：10日                                                                                  |
|TMI_CINFO11|VARCHAR2||是|日別情報：11日                                                                                  |
|TMI_CINFO12|VARCHAR2||是|日別情報：12日                                                                                  |
|TMI_CINFO13|VARCHAR2||是|日別情報：13日                                                                                  |
|TMI_CINFO14|VARCHAR2||是|日別情報：14日                                                                                  |
|TMI_CINFO15|VARCHAR2||是|日別情報：15日                                                                                  |
|TMI_CINFO16|VARCHAR2||是|日別情報：16日                                                                                  |
|TMI_CINFO17|VARCHAR2||是|日別情報：17日                                                                                  |
|TMI_CINFO18|VARCHAR2||是|日別情報：18日                                                                                  |
|TMI_CINFO19|VARCHAR2||是|日別情報：19日                                                                                  |
|TMI_CINFO20|VARCHAR2||是|日別情報：20日                                                                                  |
|TMI_CINFO21|VARCHAR2||是|日別情報：21日                                                                                  |
|TMI_CINFO22|VARCHAR2||是|日別情報：22日                                                                                  |
|TMI_CINFO23|VARCHAR2||是|日別情報：23日                                                                                  |
|TMI_CINFO24|VARCHAR2||是|日別情報：24日                                                                                  |
|TMI_CINFO25|VARCHAR2||是|日別情報：25日                                                                                  |
|TMI_CINFO26|VARCHAR2||是|日別情報：26日                                                                                  |
|TMI_CINFO27|VARCHAR2||是|日別情報：27日                                                                                  |
|TMI_CINFO28|VARCHAR2||是|日別情報：28日                                                                                  |
|TMI_CINFO29|VARCHAR2||是|日別情報：29日                                                                                  |
|TMI_CINFO30|VARCHAR2||是|日別情報：30日                                                                                  |
|TMI_CINFO31|VARCHAR2||是|日別情報：31日                                                                                  |
