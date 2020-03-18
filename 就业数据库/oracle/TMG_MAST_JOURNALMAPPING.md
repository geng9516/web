# 仕訳項目マッピングマスタ                  日次仕訳処理において、各種仕訳処理の結果を仕訳項目にマッピン(TMG_MAST_JOURNALMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMJM_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMJM_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMJM_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMJM_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMJM_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMJM_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMJM_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMJM_CWORKINGHOURSTYPEID|VARCHAR2||是|労働時間ｺｰﾄﾞ                                                                                  |
|TMJM_COVERTMJMEBORDERID|VARCHAR2||是|超勤閾値ｺｰﾄﾞ                                                                                  |
|TMJM_CINSTRUCTIONTYPEID|VARCHAR2||是|超勤命令ｺｰﾄﾞ                                                                                  |
|TMJM_CWORKTIMEZONE|VARCHAR2||否|深夜重複ﾌﾗｸﾞ                                                                                  |
|TMJM_CJOURNALIZINGID|VARCHAR2||否|仕訳項目ｺｰﾄﾞ                                                                                  |
|TMJM_CPLANWORK_INNER|NUMBER||是|予定内勤務ﾌﾗｸﾞ|
|TMJM_CLEAGALWORK_INNER|NUMBER||是|法定内勤務ﾌﾗｸﾞ|
