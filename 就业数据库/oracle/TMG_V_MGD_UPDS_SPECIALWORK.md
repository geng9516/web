# [勤怠/名称マスタ]月次集計データ作成/U-PDS向け・特殊勤務→集計項目マッピング(TMG_V_MGD_UPDS_SPECIALWORK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CSPWORKID|VARCHAR2||否|特殊勤務区分|
|MGD_CSPWORKNAME|NVARCHAR2||是|特殊勤務名称|
|MGD_CTOTALIZATIONID|NVARCHAR2||是|集計項目コード|
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
