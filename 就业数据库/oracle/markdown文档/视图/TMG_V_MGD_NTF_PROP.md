# [勤怠/名称マスタ]休暇休業申請ユーザ定義表示項目マスタ(TMG_V_MGD_NTF_PROP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_PROPID|NVARCHAR2||是|プロパティコード|
|MGD_NTFTYPEID|NVARCHAR2||是|申請区分マスタコード|
|MGD_DISPNAME|NVARCHAR2||是|表示名称|
