# 【条件検索】超過勤務時間 組織別週別(TMG_V_CDS_OVERTIME_SEC_WEEK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TVCOTSW_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TVCOTSW_CCOMPANYID|VARCHAR2||否|法人コード|
|TVCOTSW_CUSERID|VARCHAR2||是|ユーザーID|
|TVCOTSW_CSECTIONID|VARCHAR2||是|組織コード|
|TVCOTSW_DSTARTDATE|DATE||是|開始日(週の始まり)|
|TVCOTSW_DENDDATE|DATE||是|終了日(週の終わり)|
|TVCOTSW_NRUIKEI_WEEK|NUMBER||是|超過勤務時間|
