# グループ判定結果差分データ(TEMP_GROUPCHECK_DIFF)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGCD_ID|numeric||否|IDカラム|
|TGCD_CSYSTEMID|varchar||是|システムID|
|TGCD_CCUSTOMERID|varchar||是|顧客コード|
|TGCD_CCOMPANYID|varchar||是|法人コード|
|TGCD_CEMPLOYEEID|varchar||是|職員番号|
|TGCD_DBEFOREDATE|date||是|変更前日付|
|TGCD_DAFTERDATE|date||是|変更後日付|
|TGCD_CBEFOREGROUPID|varchar||是|変更前グループID|
|TGCD_CAFTERGROUPID|varchar||是|変更後グループID|
|TGCD_CMODIFIERUSERID|varchar||是|最終更新者|
|TGCD_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
