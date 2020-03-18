# [勤怠/名称マスタ]集計減算時間項目ID変換マスタ(TMG_V_MGD_REDUCEDTIME_CODE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_CBEFORETOTALIZATIONID|NVARCHAR2||是|変換前集計項目コード|
|MGD_CWORKERTYPEID|NVARCHAR2||是|勤怠種別|
|MGD_CAFTERTOTALIZATIONID|NVARCHAR2||是|変換後集計項目コード|
