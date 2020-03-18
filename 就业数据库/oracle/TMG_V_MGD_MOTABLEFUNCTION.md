# [勤怠/名称マスタ]CSVデータ取得表関数マスタ(TMG_V_MGD_MOTABLEFUNCTION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CFUNCTIONID|NVARCHAR2||是|表関数名|
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_CGENERICDETAILID_CK|VARCHAR2||否|明細コード|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CFILETYPENAME|NVARCHAR2||是|CSVファイルタイプ名|
