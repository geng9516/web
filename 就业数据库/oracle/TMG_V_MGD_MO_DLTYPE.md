# [勤怠/名称マスタ]月次集計データ作成/CSVダウンロード種別マスタ(TMG_V_MGD_MO_DLTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CDLTYPEID|VARCHAR2||是|DL種別コード|
|MGD_CDLTYPENAME|NVARCHAR2||是|DL種別名称（リンク名）|
|MGD_NSEQ|NUMBER||是|表示順|
