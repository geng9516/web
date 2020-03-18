# [勤怠]勤務パターン                    制約：月中に歴が切れないこと、デフォルトフラグがONの行は同(TMG_PATTERN)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPA_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TPA_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TPA_CSECTIONID|VARCHAR2||是|部局ｺｰﾄﾞ|
|TPA_CGROUPID|VARCHAR2||是|グループコード|
|TPA_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TPA_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TPA_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TPA_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TPA_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TPA_CPATTERNID|VARCHAR2||否|勤務パターンID                      京大では1パターンのみ                   MGD:TMG_PATTERN               |
|TPA_CPATTERNNAME|VARCHAR2||是|勤務パターン名称                                                                                  |
|TPA_CDEFAULTFLG|VARCHAR2||是|デフォルトフラグ                                                    MGD:TMG_ONOFF                 |
|TPA_NOPEN|NUMBER||是|始業時刻                                                                                      |
|TPA_NCLOSE|NUMBER||是|終業時刻                                                                                      |
|TPA_C2CALDAYS|VARCHAR2||是|2暦日勤務フラグ|
|TPA_CNEXTPTN|VARCHAR2||是|翌日の勤務パターン|
|TPA_NDATE_CHANGE_TIME|NUMBER||是|null|
