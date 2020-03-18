# [勤怠/名称マスタ]年休残時間の按分処理設定マスタ(TMG_V_MGD_NYKREST_ADUST)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_NYKREST_ADJUST|VARCHAR2||是|年休残時間按分処理コード|
|MGD_NYKREST_ADJUST_NAME|NVARCHAR2||是|年休残時間按分処理名称|
|MGD_NYKREST_ADJUST_ONOFF|NVARCHAR2||是|年休残時間按分処理ON/OFF|
