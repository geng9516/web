# [勤怠/名称マスタ]デフォルト決裁レベルマスタ(TMG_V_MGD_DEFAULT_APPLEVEL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード　　　　　 [参照元：名称マスタ.顧客コード]|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード　　　　　 [参照元：名称マスタ.法人コード]|
|MGD_DSTART_CK|DATE||否|開始日　　　　　　　 [参照元：名称マスタ.開始日]|
|MGD_DEND|DATE||否|終了日　　　　　　　 [参照元：名称マスタ.終了日]|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分　　　　　　 [参照元：名称マスタ.言語区分]|
|MGD_CPOSTID|NVARCHAR2||是|役職コード　　　　　 [参照元：名称マスタ.予備文字1]|
|MGD_CDEFAULT_APPLEVEL|NVARCHAR2||是|デフォルト決裁レベル [参照元：名称マスタ.予備文字2]|
