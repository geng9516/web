# 休暇休業申請添付ファイルテーブル                                            (TMG_NTF_ATTACHEDFILE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TNAF_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                                                                                    |
|TNAF_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TNAF_CEMPLOYEEID|varchar||否|職員番号                                                                                      |
|TNAF_CNTFNO|varchar||否|申請番号                                                                                      |
|TNAF_CFILENAME|varchar||否|ファイル名                                                                                     |
|TNAF_NSEQ|numeric||否|表示順                                                                                       |
|TNAF_BATTACH|bytea||否|添付ファイル                                                                                    |
|TNAF_CMODIFIERUSERID|varchar||是|更新ユーザーID                                                                                  |
|TNAF_DMODIFIEDDATE|date||是|更新日時                                                                                      |
