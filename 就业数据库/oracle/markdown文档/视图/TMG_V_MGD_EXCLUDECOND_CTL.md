# [勤怠/名称マスタ]HR連携除外条件区分マスタ(TMG_V_MGD_EXCLUDECOND_CTL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_EXCLUDECOND_GID|VARCHAR2||否|明細データコード|
|MGD_EXCLUDECOND_TYPE|VARCHAR2||是|除外条件区分コード|
|MGD_EXCLUDECOND_TYPE_NAME|NVARCHAR2||是|除外条件区分名称|
|MGD_EXCLUDECOND_CTL_TYPE|NVARCHAR2||是|除外方法区分|
|MGD_EXCLUDECOND_EDIT_FLG|NVARCHAR2||是|編集可否区分|
|MGD_EXCLUDECOND_LENGTH|NUMBER||是|値の桁数|
