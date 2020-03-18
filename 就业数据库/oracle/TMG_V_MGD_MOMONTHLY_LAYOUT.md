# [勤怠/名称マスタ]月例CSVレイアウトマスタ(TMG_V_MGD_MOMONTHLY_LAYOUT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CDLTYPEID|NVARCHAR2||是|DL種別コード|
|MGD_CITEMNAME|NVARCHAR2||是|項目名|
|MGD_CSQL|NVARCHAR2||是|出力SQL|
|MGD_NSEQ|NUMBER||是|出力順|
