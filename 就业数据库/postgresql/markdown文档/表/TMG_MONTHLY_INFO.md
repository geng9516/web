# [勤怠]月単位日別情報                   TMG_DAILYのビュー代わり。承認状況一覧、超過勤務指示(TMG_MONTHLY_INFO)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMI_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMI_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMI_CEMPLOYEEID|varchar||否|社員番号                                                                                      |
|TMI_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMI_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMI_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMI_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMI_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMI_DYYYYMM|date||否|該当年月                          YYYY/MM/01                                                  |
|TMI_CCONTENTID|varchar||否|コンテンツタイプ                                                    MGD:TMG_CONTENTID             |
|TMI_CINFO01|varchar||是|日別情報：1日                                                                                   |
|TMI_CINFO02|varchar||是|日別情報：2日                                                                                   |
|TMI_CINFO03|varchar||是|日別情報：3日                                                                                   |
|TMI_CINFO04|varchar||是|日別情報：4日                                                                                   |
|TMI_CINFO05|varchar||是|日別情報：5日                                                                                   |
|TMI_CINFO06|varchar||是|日別情報：6日                                                                                   |
|TMI_CINFO07|varchar||是|日別情報：7日                                                                                   |
|TMI_CINFO08|varchar||是|日別情報：8日                                                                                   |
|TMI_CINFO09|varchar||是|日別情報：9日                                                                                   |
|TMI_CINFO10|varchar||是|日別情報：10日                                                                                  |
|TMI_CINFO11|varchar||是|日別情報：11日                                                                                  |
|TMI_CINFO12|varchar||是|日別情報：12日                                                                                  |
|TMI_CINFO13|varchar||是|日別情報：13日                                                                                  |
|TMI_CINFO14|varchar||是|日別情報：14日                                                                                  |
|TMI_CINFO15|varchar||是|日別情報：15日                                                                                  |
|TMI_CINFO16|varchar||是|日別情報：16日                                                                                  |
|TMI_CINFO17|varchar||是|日別情報：17日                                                                                  |
|TMI_CINFO18|varchar||是|日別情報：18日                                                                                  |
|TMI_CINFO19|varchar||是|日別情報：19日                                                                                  |
|TMI_CINFO20|varchar||是|日別情報：20日                                                                                  |
|TMI_CINFO21|varchar||是|日別情報：21日                                                                                  |
|TMI_CINFO22|varchar||是|日別情報：22日                                                                                  |
|TMI_CINFO23|varchar||是|日別情報：23日                                                                                  |
|TMI_CINFO24|varchar||是|日別情報：24日                                                                                  |
|TMI_CINFO25|varchar||是|日別情報：25日                                                                                  |
|TMI_CINFO26|varchar||是|日別情報：26日                                                                                  |
|TMI_CINFO27|varchar||是|日別情報：27日                                                                                  |
|TMI_CINFO28|varchar||是|日別情報：28日                                                                                  |
|TMI_CINFO29|varchar||是|日別情報：29日                                                                                  |
|TMI_CINFO30|varchar||是|日別情報：30日                                                                                  |
|TMI_CINFO31|varchar||是|日別情報：31日                                                                                  |
