# [勤怠/名称マスタ]元号(TMG_V_MGD_GENGOU)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CGENGOU|NVARCHAR2||是|元号|
|MGD_CREGNAL_YEAR|NVARCHAR2||是|改元年（西暦）|
|MGD_CREGNAL_DATE|NVARCHAR2||是|改元日（和暦）|
|MGD_CINITIALS|NVARCHAR2||是|元号のイニシャル|
