# [勤怠]エラーチェック用・グループ             データ開始日、終了日は親となる部署のデータ開始日、終了日とす(TMG_GROUP_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGR_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TGR_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TGR_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TGR_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TGR_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TGR_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TGR_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TGR_CSECTIONID|VARCHAR2||否|部署コード                                                       MO:MO_CSECTIONID_CK           |
|TGR_CGROUPID|VARCHAR2||否|グループコード                       グループ作成時にIDを付番                                               |
|TGR_CGROUPNAME|VARCHAR2||是|グループ名称                                                                                    |
|TGR_NMAXSEQ|NUMBER||是|組織配下のグループ数 各組織のデフォルトグループにのみ保持|
|TGR_OT_MONTLY_01|NUMBER||是|超勤実績警告値(月)01|
|TGR_OT_MONTLY_02|NUMBER||是|超勤実績警告値(月)02|
|TGR_OT_MONTLY_03|NUMBER||是|超勤実績警告値(月)03|
|TGR_OT_MONTLY_04|NUMBER||是|超勤実績警告値(月)04|
|TGR_OT_MONTLY_05|NUMBER||是|超勤実績警告値(月)05|
|TGR_OT_YEARLY_01|NUMBER||是|超勤実績警告値(年)01|
|TGR_OT_YEARLY_02|NUMBER||是|超勤実績警告値(年)02|
|TGR_OT_YEARLY_03|NUMBER||是|超勤実績警告値(年)03|
|TGR_OT_YEARLY_04|NUMBER||是|超勤実績警告値(年)04|
|TGR_OT_YEARLY_05|NUMBER||是|超勤実績警告値(年)05|
|TGR_OT_MONTHLY_COUNT|NUMBER||是|月次警告値超過回数|
|TGR_CBUREAUID|VARCHAR2||是|null|
|TGR_CCREATE|VARCHAR2||否|null|
|TGR_HT_MONTLY_01|NUMBER||是|休日勤務日数警告値(月)01|
|TGR_HT_MONTLY_02|NUMBER||是|休日勤務日数警告値(月)02|
|TGR_HT_MONTLY_03|NUMBER||是|休日勤務日数警告値(月)03|
|TGR_HT_MONTLY_04|NUMBER||是|休日勤務日数警告値(月)04|
|TGR_HT_MONTLY_05|NUMBER||是|休日勤務日数警告値(月)05|
|TGR_OT_DAILY_01|NUMBER||是|勤務時間警告値(日)01|
|TGR_OT_MONTHLY_AVG|NUMBER||是|複数月平均時間|
