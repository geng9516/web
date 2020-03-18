# (非職)勤務予定情報(DHJKINMU)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|VARCHAR2||否|会社区分|
|CSHAINNO|VARCHAR2||否|職員番号|
|CNAMEKNA|VARCHAR2||是|カナ氏名|
|CNAMEKNJ|VARCHAR2||是|漢字氏名|
|NINYO_DTE|VARCHAR2||是|任用年月日|
|DNINYO_DTE|DATE||否|任用年月日（西暦）|
|NNKI_MR_DTE|VARCHAR2||是|任期満了年月日|
|DNNKI_MR_DTE|DATE||是|任期満了年月日（西暦）|
|SZK_CDE|VARCHAR2||是|所属コード|
|SZK_NME|VARCHAR2||是|所属|
|BKYK_CDE|VARCHAR2||是|部局コード|
|BKYK_NME|VARCHAR2||是|部局|
|SORT_JYN|NUMBER||是|ソート順|
|TEIKI_CDE|VARCHAR2||是|定期／不定期区分|
|TEIKI_NME|VARCHAR2||是|定期／不定期名称|
|JIKAN_CDE|VARCHAR2||是|勤務時間帯区分|
|JIKAN_NME|VARCHAR2||是|勤務時間帯名称|
|NUM_W1|NUMBER||是|勤務時間(月)|
|NUM_W2|NUMBER||是|勤務時間(火)|
|NUM_W3|NUMBER||是|勤務時間(水)|
|NUM_W4|NUMBER||是|勤務時間(木)|
|NUM_W5|NUMBER||是|勤務時間(金)|
|NUM_W6|NUMBER||是|勤務時間(土)|
|NUM_W7|NUMBER||是|勤務時間(日)|
|KINMU_NUM1|NUMBER||是|勤務時間1                         以下、最大５行分|
|KINMU_START11|VARCHAR2||是|勤務開始時刻11|
|KINMU_END11|VARCHAR2||是|勤務終了時刻11|
|KINMU_START12|VARCHAR2||是|勤務開始時刻12|
|KINMU_END12|VARCHAR2||是|勤務終了時刻12|
|KYUKEI_START1|VARCHAR2||是|休憩開始時刻1|
|KYUKEI_END1|VARCHAR2||是|休憩終了時刻1|
|YOBI1|VARCHAR2||是|曜日1                           各桁で月～日とし、対象曜日"1|
|KINMU_NUM2|NUMBER||是|勤務時間2|
|KINMU_START21|VARCHAR2||是|勤務開始時刻21|
|KINMU_END21|VARCHAR2||是|勤務終了時刻21|
|KINMU_START22|VARCHAR2||是|勤務開始時刻22|
|KINMU_END22|VARCHAR2||是|勤務終了時刻22|
|KYUKEI_START2|VARCHAR2||是|休憩開始時刻2|
|KYUKEI_END2|VARCHAR2||是|休憩終了時刻2|
|YOBI2|VARCHAR2||是|曜日2                           各桁で月～日とし、対象曜日"1|
|KINMU_NUM3|NUMBER||是|勤務時間3|
|KINMU_START31|VARCHAR2||是|勤務開始時刻31|
|KINMU_END31|VARCHAR2||是|勤務終了時刻31|
|KINMU_START32|VARCHAR2||是|勤務開始時刻32|
|KINMU_END32|VARCHAR2||是|勤務終了時刻32|
|KYUKEI_START3|VARCHAR2||是|休憩開始時刻3|
|KYUKEI_END3|VARCHAR2||是|休憩終了時刻3|
|YOBI3|VARCHAR2||是|曜日3                           各桁で月～日とし、対象曜日"1|
|KINMU_NUM4|NUMBER||是|勤務時間4|
|KINMU_START41|VARCHAR2||是|勤務開始時刻41|
|KINMU_END41|VARCHAR2||是|勤務終了時刻41|
|KINMU_START42|VARCHAR2||是|勤務開始時刻42|
|KINMU_END42|VARCHAR2||是|勤務終了時刻42|
|KYUKEI_START4|VARCHAR2||是|休憩開始時刻4|
|KYUKEI_END4|VARCHAR2||是|休憩終了時刻4|
|YOBI4|VARCHAR2||是|曜日4                           各桁で月～日とし、対象曜日"1|
|KINMU_NUM5|NUMBER||是|勤務時間5|
|KINMU_START51|VARCHAR2||是|勤務開始時刻51|
|KINMU_END51|VARCHAR2||是|勤務終了時刻51|
|KINMU_START52|VARCHAR2||是|勤務開始時刻52|
|KINMU_END52|VARCHAR2||是|勤務終了時刻52|
|KYUKEI_START5|VARCHAR2||是|休憩開始時刻5|
|KYUKEI_END5|VARCHAR2||是|休憩終了時刻5|
|YOBI5|VARCHAR2||是|曜日5                           各桁で月～日とし、対象曜日"1|
|NUM_WEEK|NUMBER||是|週勤務時間数|
|DAYS_YEAR|NUMBER||是|年勤務日数|
|NUM_YEAR|NUMBER||是|年勤務時間数|
|LAST_PGM|VARCHAR2||是|更新PGM|
|CMNUSER|VARCHAR2||是|ﾕｰｻﾞｰ|
|DMNDATE|DATE||是|更新日|
