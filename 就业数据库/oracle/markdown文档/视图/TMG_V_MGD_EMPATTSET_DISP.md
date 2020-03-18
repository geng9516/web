# 個人属性設定用一括編集用項目の制御マスタ(TMG_V_MGD_EMPATTSET_DISP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード(連番)|
|MGD_CITEMNAME|NVARCHAR2||是|項目名|
|MGD_NDISPFLG|NUMBER||是|表示制御フラグ|
|MGD_NEDITFLG|NUMBER||是|編集制御フラグ|
|MGD_NSEQ|NUMBER||是|並び順|
|MGD_NWIDTH|NUMBER||是|表示幅|
