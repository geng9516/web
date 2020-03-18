# [勤怠]勤務パターン                    制約：月中に歴が切れないこと、デフォルトフラグがONの行は同(TMG_PATTERN)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPA_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TPA_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TPA_CSECTIONID|varchar||是|部局ｺｰﾄﾞ|
|TPA_CGROUPID|varchar||是|グループコード|
|TPA_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TPA_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TPA_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TPA_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TPA_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TPA_CPATTERNID|varchar||否|勤務パターンID                      京大では1パターンのみ                   MGD:TMG_PATTERN               |
|TPA_CPATTERNNAME|varchar||是|勤務パターン名称                                                                                  |
|TPA_CDEFAULTFLG|varchar||是|デフォルトフラグ                                                    MGD:TMG_ONOFF                 |
|TPA_NOPEN|numeric||是|始業時刻                                                                                      |
|TPA_NCLOSE|numeric||是|終業時刻                                                                                      |
|TPA_C2CALDAYS|varchar||是|2暦日勤務フラグ|
|TPA_CNEXTPTN|varchar||是|翌日の勤務パターン|
|TPA_NDATE_CHANGE_TIME|numeric||是|null|
