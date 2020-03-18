# [勤怠/名称マスタ]出勤簿デフォルト表示開始月(TMG_V_MGD_FIRST_MONTH)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID|VARCHAR2||是|法人コード|
|MGD_DSTART|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_CCAPTION|NVARCHAR2||是|説明|
|MGD_CWORKERTYPE|NVARCHAR2||是|身分コード|
|MGD_NDEFAULT_MONTH|NUMBER||是|デフォルト表示開始月|
