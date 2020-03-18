# [勤怠/名称マスタ]勤怠種別(TMG_V_MGD_WORKERTYPE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CWORKERTYPENAME|NVARCHAR2||是|勤怠種別名称|
|MGD_CDEFAULT_WORK_PATTERN|NVARCHAR2||是|デフォルト勤務パターン|
|MGD_CDISP_KBN|NVARCHAR2||是|常勤者の「予定勤務パターン」選択欄の表示・非表示区分|
|MGD_NFTPT|NUMBER||是|常勤/非常勤の区別|
|MGD_NLONG_HOURS_LIMIT|NUMBER||是|長時間勤務の閾値|
|MGD_NINIT_WORKHOURS4SAI|NUMBER||是|裁量労働制適用者の1日あたり労働時間の初期値(分)|
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CWORKERTYPE|VARCHAR2||是|勤怠種別|
