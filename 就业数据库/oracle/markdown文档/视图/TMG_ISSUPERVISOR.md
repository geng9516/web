# 管理監督者[代理権限者]判定テーブル(TMG_ISSUPERVISOR)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TIS_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TIS_CCOMPANYID|VARCHAR2||否|法人コード|
|TIS_CEMPLOYEEID|VARCHAR2||否|職員番号|
|TIS_CUSERID|VARCHAR2||否|ユーザーID|
|TIS_CGROUPID|VARCHAR2||否|グループID|
|TIS_DSTARTDATE|DATE||是|開始年月日|
|TIS_DENDDATE|DATE||是|終了年月日|
|TIS_ISSUPERVISOR|NUMBER||是|代理権限者フラグ|
