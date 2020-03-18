# [勤怠]日別情報変更ログ                                                (TMG_DAILY_CHANGELOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDCL_NID|numeric||否|更新ID                                                                                      |
|TDCL_NIFBEFOREORAFTER|numeric||否|前後区分( 0：前 1：後)                                                                            |
|TDCL_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TDCL_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TDCL_CEMPLOYEEID|varchar||否|社員番号                                                                                      |
|TDCL_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TDCL_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TDCL_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TDCL_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TDCL_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TDCL_DYYYYMMDD|date||否|勤務年月日                         YYYY/MM/DD                                                  |
|TDCL_CSTATUSFLG|varchar||是|ステータスフラグ                      MGD:TMG_DATASTATUS                                          |
|TDCL_NOPEN_TP|numeric||是|[打刻]始業時刻                      300～1740(朝5:00～翌朝5:00)                                      |
|TDCL_NCLOSE_TP|numeric||是|[打刻]終業時刻                                                                                  |
|TDCL_CWORKINGID_P|varchar||是|[予定]就業区分                      MGD:TMG_WORK                                                |
|TDCL_NOPEN_P|numeric||是|[予定]始業時刻                                                                                  |
|TDCL_NCLOSE_P|numeric||是|[予定]終業時刻                                                                                  |
|TDCL_NOPEN_N|numeric||是|[申請反映]始業時刻                                                                                |
|TDCL_NCLOSE_N|numeric||是|[申請反映]終業時刻                                                                                |
|TDCL_CWORKINGID_R|varchar||是|[実績]就業区分                      MGD:TMG_WORK                                                |
|TDCL_NOPEN_R|numeric||是|[実績]始業時刻                                                                                  |
|TDCL_NCLOSE_R|numeric||是|[実績]終業時刻                                                                                  |
|TDCL_COWNCOMMENT_R|varchar||是|[実績]本人コメント                                                                                |
|TDCL_CBOSSCOMMENT_R|varchar||是|[実績]承認者コメント                                                                               |
|TDCL_CBUSINESSTRIPID_P|varchar||是|[予定]出張区分                                                                                  |
|TDCL_CBUSINESSTRIPID_R|varchar||是|[実績]出張区分                                                                                  |
|TDCL_CCOMMENT_P|varchar||是|[予定]コメント                                                                                  |
|TDCL_CPATTERNID|varchar||是|勤務パターンID                                                                                  |
