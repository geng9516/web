# (非職)勤務予定情報(DHJKINMU)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|varchar||否|会社区分|
|CSHAINNO|varchar||否|職員番号|
|CNAMEKNA|varchar||是|カナ氏名|
|CNAMEKNJ|varchar||是|漢字氏名|
|NINYO_DTE|varchar||是|任用年月日|
|DNINYO_DTE|date||否|任用年月日（西暦）|
|NNKI_MR_DTE|varchar||是|任期満了年月日|
|DNNKI_MR_DTE|date||是|任期満了年月日（西暦）|
|SZK_CDE|varchar||是|所属コード|
|SZK_NME|varchar||是|所属|
|BKYK_CDE|varchar||是|部局コード|
|BKYK_NME|varchar||是|部局|
|SORT_JYN|numeric||是|ソート順|
|TEIKI_CDE|varchar||是|定期／不定期区分|
|TEIKI_NME|varchar||是|定期／不定期名称|
|JIKAN_CDE|varchar||是|勤務時間帯区分|
|JIKAN_NME|varchar||是|勤務時間帯名称|
|NUM_W1|numeric||是|勤務時間(月)|
|NUM_W2|numeric||是|勤務時間(火)|
|NUM_W3|numeric||是|勤務時間(水)|
|NUM_W4|numeric||是|勤務時間(木)|
|NUM_W5|numeric||是|勤務時間(金)|
|NUM_W6|numeric||是|勤務時間(土)|
|NUM_W7|numeric||是|勤務時間(日)|
|KINMU_NUM1|numeric||是|勤務時間1                         以下、最大５行分|
|KINMU_START11|varchar||是|勤務開始時刻11|
|KINMU_END11|varchar||是|勤務終了時刻11|
|KINMU_START12|varchar||是|勤務開始時刻12|
|KINMU_END12|varchar||是|勤務終了時刻12|
|KYUKEI_START1|varchar||是|休憩開始時刻1|
|KYUKEI_END1|varchar||是|休憩終了時刻1|
|YOBI1|varchar||是|曜日1                           各桁で月～日とし、対象曜日"1|
|KINMU_NUM2|numeric||是|勤務時間2|
|KINMU_START21|varchar||是|勤務開始時刻21|
|KINMU_END21|varchar||是|勤務終了時刻21|
|KINMU_START22|varchar||是|勤務開始時刻22|
|KINMU_END22|varchar||是|勤務終了時刻22|
|KYUKEI_START2|varchar||是|休憩開始時刻2|
|KYUKEI_END2|varchar||是|休憩終了時刻2|
|YOBI2|varchar||是|曜日2                           各桁で月～日とし、対象曜日"1|
|KINMU_NUM3|numeric||是|勤務時間3|
|KINMU_START31|varchar||是|勤務開始時刻31|
|KINMU_END31|varchar||是|勤務終了時刻31|
|KINMU_START32|varchar||是|勤務開始時刻32|
|KINMU_END32|varchar||是|勤務終了時刻32|
|KYUKEI_START3|varchar||是|休憩開始時刻3|
|KYUKEI_END3|varchar||是|休憩終了時刻3|
|YOBI3|varchar||是|曜日3                           各桁で月～日とし、対象曜日"1|
|KINMU_NUM4|numeric||是|勤務時間4|
|KINMU_START41|varchar||是|勤務開始時刻41|
|KINMU_END41|varchar||是|勤務終了時刻41|
|KINMU_START42|varchar||是|勤務開始時刻42|
|KINMU_END42|varchar||是|勤務終了時刻42|
|KYUKEI_START4|varchar||是|休憩開始時刻4|
|KYUKEI_END4|varchar||是|休憩終了時刻4|
|YOBI4|varchar||是|曜日4                           各桁で月～日とし、対象曜日"1|
|KINMU_NUM5|numeric||是|勤務時間5|
|KINMU_START51|varchar||是|勤務開始時刻51|
|KINMU_END51|varchar||是|勤務終了時刻51|
|KINMU_START52|varchar||是|勤務開始時刻52|
|KINMU_END52|varchar||是|勤務終了時刻52|
|KYUKEI_START5|varchar||是|休憩開始時刻5|
|KYUKEI_END5|varchar||是|休憩終了時刻5|
|YOBI5|varchar||是|曜日5                           各桁で月～日とし、対象曜日"1|
|NUM_WEEK|numeric||是|週勤務時間数|
|DAYS_YEAR|numeric||是|年勤務日数|
|NUM_YEAR|numeric||是|年勤務時間数|
|LAST_PGM|varchar||是|更新PGM|
|CMNUSER|varchar||是|ﾕｰｻﾞｰ|
|DMNDATE|date||是|更新日|
