# [勤怠/名称マスタ]出勤簿月次集計情報表示項目(TMG_V_MGD_ATTENDANCEITEMS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_COUTLINE|NVARCHAR2||是|表示項目概要|
|MGD_CDISP_TEXT|NVARCHAR2||是|表示用タイトル名称、またはデータ取得SQL（SELECT句）|
|MGD_CDISP_OPTION|NVARCHAR2||是|表示タイトル名称の表示オプション、または表示データの表示オプション|
|MGD_NCOLUMN_NUM|NUMBER||是|列順|
|MGD_NROW_NUM|NUMBER||是|行順|
