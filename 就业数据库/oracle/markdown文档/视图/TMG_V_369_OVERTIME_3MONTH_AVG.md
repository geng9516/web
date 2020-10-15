# 超過勤務命令（月／教職員）(TMG_V_369_OVERTIME_3MONTH_AVG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TV369_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TV369_CCOMPANYID|VARCHAR2||否|法人コード|
|TV369_CEMPLOYEEID|VARCHAR2||否|職員番号|
|TV369_CUSERID|VARCHAR2||否|ユーザーID|
|TV369_DSTARTDATE|DATE||是|データ開始日|
|TV369_DENDDATE|DATE||是|データ終了日|
|TV369_DYYYYMM|DATE||否|対象年月|
|TV369_NAVG_OVERTIME|NUMBER||是|直近3ヶ月平均超過時間|
