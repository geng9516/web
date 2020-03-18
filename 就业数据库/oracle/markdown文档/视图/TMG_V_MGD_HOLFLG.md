# [勤怠/名称マスタ]休日フラグマスタ(TMG_V_MGD_HOLFLG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CHOLFLG|VARCHAR2||是|休日フラグコード|
|MGD_CHOLFLGNM|NVARCHAR2||是|休日フラグ名称|
|MGD_CWORKID|NVARCHAR2||是|対応する就業区分|
|MGD_CCSSCOLOR4ATTENDANCEBOOK|NVARCHAR2||是|[出勤簿]背景色のCSSクラスID|
|MGD_NPUBLICHOLFLG|NUMBER||是|祝日フラグ|
