# ロギング(固定文字)マスタ(TMG_V_MGD_TDCL_FIXEDSTR)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_FIXEDSTRID|VARCHAR2||是|固定文字コード|
|MGD_FIXEDSTR_DESC|NVARCHAR2||是|固定文字の説明|
|MGD_FIXEDSTR_VALUE|NVARCHAR2||是|固定文字の値|
