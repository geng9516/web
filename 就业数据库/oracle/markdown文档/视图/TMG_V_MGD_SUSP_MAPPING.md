# [勤怠/名称マスタ]出勤簿：[出勤簿]休暇休業・就業区分(TMG_V_MGD_SUSP_MAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CWORKINGID_BASE|NVARCHAR2||是|元就業区分|
|MGD_CSUSPENSIONTYPE|NVARCHAR2||是|休暇休職区分|
|MGD_CWORKINGID_RESULT|NVARCHAR2||是|就業区分（結果）|
|MGD_NMIN_RATE|NUMBER||是|休職給率（ＦＲＯＭ）|
|MGD_NMAX_RATE|NUMBER||是|休職給率（ＴＯ）|
