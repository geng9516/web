# [勤怠]アラートメッセージ格納テーブル                                         (TMG_ALERTMSG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TAM_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TAM_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TAM_CEMPLOYEEID|VARCHAR2||否|職員番号                                                                                      |
|TAM_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TAM_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TAM_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TAM_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TAM_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TAM_DDATE|DATE||否|発生日付                                                                                      |
|TAM_DYYYYMM|DATE||否|処理月                                                                                       |
|TAM_MODULE|VARCHAR2||否|モジュール名                                                                                    |
|TAM_MESSAGE|VARCHAR2||是|アラートメッセージ                                                                                 |
