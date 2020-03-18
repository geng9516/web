# [勤怠/名称マスタ]年休申請種類マッピングマスタ(TMG_V_MGD_NYKTYPEMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CREFERENCENAME|NVARCHAR2||是|年休種類名称|
|MGD_CREFERENCECODE|NVARCHAR2||是|年休関連の区分値|
|MGD_CNYKTYPECODE|NVARCHAR2||是|年休種類コード|
|MGD_CEXEC_FUNC_NAME|NVARCHAR2||是|実行ファンクション名称|
|MGD_NPART_OF_DAY|NUMBER||是|半休区分（1：前半、2：後半、NULL：半休ではない）|
|MGD_ALL_DAY_TYPE|NUMBER||是|終日区分（０：通常、１：半日と時間の組合せ、NULL：未設定）|
