# [勤怠/名称マスタ]表示項目制御マスタ(TMG_V_MGD_NTFHOLRESTTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_CDISPNAME|NVARCHAR2||是|表示名称|
|MGD_CTYPICAL_NTFTYPE|NVARCHAR2||是|代表申請区分|
|MGD_CGROUPINGID|NVARCHAR2||是|申請種類グルーピングコード|
|MGD_CPROGRAM_NAME|NVARCHAR2||是|プログラム名称|
|MGD_NSORT|NUMBER||是|ソート順|
