# [勤怠/名称マスタ]年休繰越マスタ(TMG_V_MGD_HOLINNER_CALC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CHOLINNER_CALC|VARCHAR2||是|祝日の法定内労働時間計上コード|
|MGD_CHOLINNER_CALC_NAME|NVARCHAR2||是|祝日の法定内労働時間計上名称|
|MGD_CWORKERTYPE|NVARCHAR2||是|勤怠種別|
|MGD_NHOLINNER_CALC_MODE|NUMBER||是|祝日の法定内労働時間計上設定(0:計上しない、1:計上する)|
