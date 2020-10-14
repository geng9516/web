# [勤怠]GreenNuts用：打刻データログ                                      (TMG_GREENNUTS_TPLOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGTL_NID|NUMBER||否|ID                                                          TMG_GREENNUTS_TPLOG_SEQ       |
|TGTL_CTIMEPUNCHDATA|VARCHAR2||否|打刻データ                                                                                     |
|TGTL_CICCARDID|VARCHAR2||是|ICカードID                       打刻データ：1～16バイト                                               |
|TGTL_CEMPLOYEEID|VARCHAR2||是|職員コード                         打刻データ：17～26バイト                                              |
|TGTL_CTPTYPEID|VARCHAR2||是|出退勤区分                         打刻データ：27～28バイト                                              |
|TGTL_CTPTIME|VARCHAR2||是|スキャン日時                        打刻データ：29～42バイト                                              |
|TGTL_CMODIFIERUSERID|VARCHAR2||是|最終更新者                                                                                     |
|TGTL_DMODIFIEDDATE|DATE||是|最終更新日時                                                                                    |
|TGTL_CMEMO|VARCHAR2||是|備考                            反映失敗時のエラーコード等                                               |
