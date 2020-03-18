# [勤怠]予定確認比較用情報 (TMG_DAILY_P_CONFIRM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDC_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDC_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TDC_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                      |
|TDC_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日|
|TDC_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日|
|TDC_CMODIFIERUSERID|VARCHAR2||是|更新者|
|TDC_DMODIFIEDDATE|DATE||是|更新日|
|TDC_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TDC_DYYYYMM|DATE||否|該当年月                          YYYY/MM/01                                                  |
|TDC_DYYYYMMDD|DATE||否|勤務年月日                         YYYY/MM/DD                                                  |
|TDC_CWORKINGID_P|VARCHAR2||是|[予定]就業区分                                                    MGD:TMG_WORK                  |
|TDC_CBUSINESSTRIPID_P|VARCHAR2||是|[予定]出張区分                                                    MGD:TMG_BUSINESS_TRIP         |
|TDC_CCONFIRM|VARCHAR2||是|予定比較用文字列                                                                                  |
