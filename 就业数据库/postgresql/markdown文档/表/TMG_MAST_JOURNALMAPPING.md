# 仕訳項目マッピングマスタ                  日次仕訳処理において、各種仕訳処理の結果を仕訳項目にマッピン(TMG_MAST_JOURNALMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMJM_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMJM_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMJM_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMJM_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMJM_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMJM_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMJM_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMJM_CWORKINGHOURSTYPEID|varchar||是|労働時間ｺｰﾄﾞ                                                                                  |
|TMJM_COVERTMJMEBORDERID|varchar||是|超勤閾値ｺｰﾄﾞ                                                                                  |
|TMJM_CINSTRUCTIONTYPEID|varchar||是|超勤命令ｺｰﾄﾞ                                                                                  |
|TMJM_CWORKTIMEZONE|varchar||否|深夜重複ﾌﾗｸﾞ                                                                                  |
|TMJM_CJOURNALIZINGID|varchar||否|仕訳項目ｺｰﾄﾞ                                                                                  |
|TMJM_CPLANWORK_INNER|numeric||是|予定内勤務ﾌﾗｸﾞ|
|TMJM_CLEAGALWORK_INNER|numeric||是|法定内勤務ﾌﾗｸﾞ|
