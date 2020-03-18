# 私傷病⇔業務災害変換マスタ(TMG_V_MGD_CONV_DISASTER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_CBEFORETYPE|NVARCHAR2||是|更新前の申請区分|
|MGD_CAFTERTYPE|NVARCHAR2||是|更新後の申請区分|
|MGD_CDISASTER_ACTION|NVARCHAR2||是|労災申請更新アクションのマスタコード|
