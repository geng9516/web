# [勤怠/名称マスタ]月次集計データ作成/U-PDS向け・集計項目→月例給与カラムマッピング(TMG_V_MGD_UPDS_MAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CTOTALIZATIONID|NVARCHAR2||是|集計項目コード|
|MGD_CCOLUMNID|NVARCHAR2||是|カラムID|
|MGD_CWORKERTYPEID|NVARCHAR2||是|勤怠種別コード|
|MGD_NROUNDGROUP|NUMBER||是|丸めグループ|
|MGD_CROUNDTYPE|NVARCHAR2||是|丸め種別|
|MGD_NROUNDUNITS|NUMBER||是|丸め単位|
|MGD_NMARK|NUMBER||是|符号|
