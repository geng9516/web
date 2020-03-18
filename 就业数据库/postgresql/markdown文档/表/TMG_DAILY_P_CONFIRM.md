# [勤怠]予定確認比較用情報 (TMG_DAILY_P_CONFIRM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDC_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDC_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TDC_CEMPLOYEEID|varchar||否|社員番号                                                                                      |
|TDC_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日|
|TDC_DENDDATE|date||否|ﾃﾞｰﾀ終了日|
|TDC_CMODIFIERUSERID|varchar||是|更新者|
|TDC_DMODIFIEDDATE|date||是|更新日|
|TDC_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TDC_DYYYYMM|date||否|該当年月                          YYYY/MM/01                                                  |
|TDC_DYYYYMMDD|date||否|勤務年月日                         YYYY/MM/DD                                                  |
|TDC_CWORKINGID_P|varchar||是|[予定]就業区分                                                    MGD:TMG_WORK                  |
|TDC_CBUSINESSTRIPID_P|varchar||是|[予定]出張区分                                                    MGD:TMG_BUSINESS_TRIP         |
|TDC_CCONFIRM|varchar||是|予定比較用文字列                                                                                  |
