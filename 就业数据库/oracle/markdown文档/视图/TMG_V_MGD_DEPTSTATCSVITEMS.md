# [勤怠/名称マスタ]部署別統計情報CSVレイアウト(TMG_V_MGD_DEPTSTATCSVITEMS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CHEADER|NVARCHAR2||是|ヘッダ名称|
|MGD_CSQL|NVARCHAR2||是|Select句|
|MGD_CCOLUMNID|NVARCHAR2||是|カラム名|
|MGD_NSEQ|NUMBER||是|表示順|
