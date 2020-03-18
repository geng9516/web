# [勤怠/名称マスタ]自動補完対象マスタ(TMG_V_MGD_AUTO_INPUT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MSG_CATEGORYID|VARCHAR2||是|カテゴリID|
|MSG_TMG_ITEMSID|NVARCHAR2||是|就業実績表示種別|
|MSG_ROW|NVARCHAR2||是|自動補完行|
