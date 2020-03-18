# エラーチェック用・グループ属性テーブル(TMG_GROUP_ATTRIBUTE_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGRA_CCUSTOMERID|varchar||否|顧客コード|
|TGRA_CCOMPANYID|varchar||否|法人コード|
|TGRA_CSECTIONID|varchar||否|組織コード|
|TGRA_CGROUPID|varchar||否|グループコード|
|TGRA_DSTARTDATE|date||否|開始日|
|TGRA_DENDDATE|date||是|終了日|
|TGRA_CMODIFIERUSERID|varchar||是|更新者|
|TGRA_DMODIFIEDDATE|date||是|更新日|
|TGRA_CAUTOSET_EVA|varchar||否|デフォルト承認者自動設定フラグ|
|TGRA_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TGRA_OT_MONTLY_01|numeric||是|超勤実績警告値(月)01|
|TGRA_OT_MONTLY_02|numeric||是|超勤実績警告値(月)02|
|TGRA_OT_MONTLY_03|numeric||是|超勤実績警告値(月)03|
|TGRA_OT_MONTLY_04|numeric||是|超勤実績警告値(月)04|
|TGRA_OT_MONTLY_05|numeric||是|超勤実績警告値(月)05|
|TGRA_OT_YEARLY_01|numeric||是|超勤実績警告値(年)01|
|TGRA_OT_YEARLY_02|numeric||是|超勤実績警告値(年)02|
|TGRA_OT_YEARLY_03|numeric||是|超勤実績警告値(年)03|
|TGRA_OT_YEARLY_04|numeric||是|超勤実績警告値(年)04|
|TGRA_OT_YEARLY_05|numeric||是|超勤実績警告値(年)05|
|TGRA_OT_MONTHLY_COUNT|numeric||是|月次警告値超過回数|
|TGRA_HT_MONTLY_01|numeric||是|休暇勤務日数警告値(月)01|
|TGRA_HT_MONTLY_02|numeric||是|休暇勤務日数警告値(月)02|
|TGRA_HT_MONTLY_03|numeric||是|休暇勤務日数警告値(月)03|
|TGRA_HT_MONTLY_04|numeric||是|休暇勤務日数警告値(月)04|
|TGRA_HT_MONTLY_05|numeric||是|休暇勤務日数警告値(月)05|
|TGRA_OT_DAILY_01|numeric||是|勤務時間警告値(日)01|
|TGRA_OT_MONTHLY_AVG|numeric||是|複数月平均時間|
