# [勤怠]グループ                      データ開始日、終了日は親となる部署のデータ開始日、終了日とす(TMG_GROUP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGR_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TGR_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TGR_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TGR_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TGR_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TGR_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TGR_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TGR_CSECTIONID|varchar||否|部署コード                                                       MO:MO_CSECTIONID_CK           |
|TGR_CGROUPID|varchar||否|グループコード                       グループ作成時にIDを付番                                               |
|TGR_CGROUPNAME|varchar||是|グループ名称                                                                                    |
|TGR_NMAXSEQ|numeric||是|組織配下のグループ数 各組織のデフォルトグループにのみ保持|
|TGR_OT_MONTLY_01|numeric||是|超勤実績警告値(月)01|
|TGR_OT_MONTLY_02|numeric||是|超勤実績警告値(月)02|
|TGR_OT_MONTLY_03|numeric||是|超勤実績警告値(月)03|
|TGR_OT_MONTLY_04|numeric||是|超勤実績警告値(月)04|
|TGR_OT_MONTLY_05|numeric||是|超勤実績警告値(月)05|
|TGR_OT_YEARLY_01|numeric||是|超勤実績警告値(年)01|
|TGR_OT_YEARLY_02|numeric||是|超勤実績警告値(年)02|
|TGR_OT_YEARLY_03|numeric||是|超勤実績警告値(年)03|
|TGR_OT_YEARLY_04|numeric||是|超勤実績警告値(年)04|
|TGR_OT_YEARLY_05|numeric||是|超勤実績警告値(年)05|
|TGR_OT_MONTHLY_COUNT|numeric||是|月次警告値超過回数|
|TGR_CBUREAUID|varchar||是|null|
|TGR_CCREATE|varchar||是|null|
|TGR_HT_MONTLY_01|numeric||是|休日勤務日数警告値(月)01|
|TGR_HT_MONTLY_02|numeric||是|休日勤務日数警告値(月)02|
|TGR_HT_MONTLY_03|numeric||是|休日勤務日数警告値(月)03|
|TGR_HT_MONTLY_04|numeric||是|休日勤務日数警告値(月)04|
|TGR_HT_MONTLY_05|numeric||是|休日勤務日数警告値(月)05|
|TGR_OT_DAILY_01|numeric||是|勤務時間警告値(日)01|
|TGR_OT_MONTHLY_AVG|numeric||是|複数月平均時間|
