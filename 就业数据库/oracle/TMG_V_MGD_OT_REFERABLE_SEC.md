# [勤怠/名称マスタ]超勤申請表示組織設定マスタ(TMG_V_MGD_OT_REFERABLE_SEC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CSECTIONID|NVARCHAR2||是|設定対象組織コード|
|MGD_CREFERABLESECTIONID|NVARCHAR2||是|参照可能組織コード|
