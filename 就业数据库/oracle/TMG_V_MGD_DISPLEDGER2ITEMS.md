# [勤怠/名称マスタ]出勤簿（月）表示項目(TMG_V_MGD_DISPLEDGER2ITEMS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_CMASTERNAME|NVARCHAR2||是|マスタ名称|
|MGD_CTITLE|NVARCHAR2||是|項目名|
|MGD_CCOLUMNID|NVARCHAR2||是|取得元カラムID|
|MGD_NSORT|NUMBER||是|表示位置|
|MGD_NFORMAT_TYPE|NUMBER||是|表示形式（0：日数項目→そのまま。1：時間項目→「hh:mi」に変換）|
