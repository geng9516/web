# 休暇休業申請添付ファイルテーブル                                            (TMG_NTF_ATTACHEDFILE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TNAF_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                                                                                    |
|TNAF_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TNAF_CEMPLOYEEID|VARCHAR2||否|職員番号                                                                                      |
|TNAF_CNTFNO|VARCHAR2||否|申請番号                                                                                      |
|TNAF_CFILENAME|VARCHAR2||否|ファイル名                                                                                     |
|TNAF_NSEQ|NUMBER||否|表示順                                                                                       |
|TNAF_BATTACH|BLOB||否|添付ファイル                                                                                    |
|TNAF_CMODIFIERUSERID|VARCHAR2||是|更新ユーザーID                                                                                  |
|TNAF_DMODIFIEDDATE|DATE||是|更新日時                                                                                      |
