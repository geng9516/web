# [勤怠/名称マスタ]ノー残業デーマスタ設定(TMG_V_MGD_NO_OVERTIME_DAYS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CSECTIONID|VARCHAR2||否|所属コード|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_CYOBI|NVARCHAR2||是|曜日（数値をカンマ区切り）|
|MGD_CTEXT_ENTRY|NVARCHAR2||是|表示文言（就業登録・日次画面用）|
|MGD_CTEXT_TP|NVARCHAR2||是|表示文言（打刻画面用）|
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
