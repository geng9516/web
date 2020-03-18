# 労災申請更新アクションマスタ(TMG_V_MGD_DISASTER_ACTION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_CVALUE|NVARCHAR2||是|ボタン名称|
|MGD_CBEFOREDISASTER|NVARCHAR2||是|申請データの労災申請ステータス|
|MGD_CAFTERDISASTER|NVARCHAR2||是|更新後の労災申請ステータス|
|MGD_SEQ|NUMBER||是|表示順|
