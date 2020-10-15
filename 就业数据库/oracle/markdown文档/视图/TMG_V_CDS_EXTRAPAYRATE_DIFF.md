# 割増率修正対象情報(TMG_V_CDS_EXTRAPAYRATE_DIFF)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TVCEP_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TVCEP_CCOMPANYID|VARCHAR2||否|法人コード|
|TVCEP_CEMPLOYEEID|VARCHAR2||否|職員番号|
|TVCEP_CUSERID|VARCHAR2||否|ユーザーID|
|TVCEP_DSTARTDATE|DATE||是|データ開始日|
|TVCEP_DENDDATE|DATE||是|データ終了日|
|TVCEP_DYYYYMMDD|DATE||否|[割増率修正対象情報]年月日|
|TVCEP_CEXTRAPAYRATE|NVARCHAR2||是|[割増率修正対象情報]割増率(変換前)|
|TVCEP_CEXTRAPAYRATE_CONV|NVARCHAR2||是|[割増率修正対象情報]割増率(変換後)|
|TVCEP_NVALUE|NUMBER||是|[割増率修正対象情報]時間|
