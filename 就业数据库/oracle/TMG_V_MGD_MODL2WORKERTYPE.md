# [勤怠/名称マスタ]月次集計データ作成/DL種別→勤怠種別マッピング(TMG_V_MGD_MODL2WORKERTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CDLTYPEID|NVARCHAR2||是|DL種別コード|
|MGD_CWORKERTYPEID|NVARCHAR2||是|勤怠種別コード|
