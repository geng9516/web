# [勤怠/名称マスタ]申請エラーチェック対応申請種類(TMG_V_MGD_NTFCHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CPROGRAM_NAME|NVARCHAR2||是|プログラム名称|
|MGD_CNTFTYPE|NVARCHAR2||是|申請種類|
|MGD_CNTFGROUPINGCODE|NVARCHAR2||是|申請種類グルーピングコード|
|MGD_CCATEGORYCODE|NVARCHAR2||是|カテゴリーコード|
|MGD_NMAXIMUM_LIMIT_HOURS|NUMBER||是|取得上限値|
|MGD_NMINIMUM_UNITINP|NUMBER||是|取得単位|
|MGD_NMINIMUM_WORKHOURS_P|NUMBER||是|予定勤務時間下限値|
