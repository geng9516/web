# 【条件検索】超過勤務時間(組織)(TMG_V_CDS_OVERTIME_TOTAL_SEC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TVCOTTLS_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TVCOTTLS_CCOMPANYID|VARCHAR2||否|法人コード|
|TVCOTTLS_CUSERID|VARCHAR2||是|ユーザーID|
|TVCOTTLS_CSECTIONID|VARCHAR2||是|組織コード|
|TVCOTTLS_NYYYY|NUMBER||是|該当年|
|TVCOTTLS_DYYYYMM|DATE||是|該当年月|
|TVCOTTLS_DYYYYMMDD|DATE||是|該当年月日|
|TVCOTTLS_NCHOKIN|NUMBER||是|超過勤務時間|
|TVCOTTLS_NTOTAL_MONTH|NUMBER||是|超過勤務累計(月)|
|TVCOTTLS_NTOTAL_YEAR|NUMBER||是|超過勤務累計(年)|
|TVCOTTLS_DSTARTDATE|DATE||是|データ開始日|
|TVCOTTLS_DENDDATE|DATE||是|データ終了日|
