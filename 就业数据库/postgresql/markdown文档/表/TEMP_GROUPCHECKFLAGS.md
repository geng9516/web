# グループ判定処理制御データ(TEMP_GROUPCHECKFLAGS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGCF_ID|numeric||否|IDカラム|
|TGCF_DBEFOREDATE|date||是|基準日(変更元)|
|TGCF_DAFTERDATE|date||是|基準日(変更先)|
|TGCF_CERRORMESSAGE|varchar||是|エラーメッセージ|
|TGCF_CSTATUS|varchar||是|ステータス|
|TGCF_CMODIFIERUSERID|varchar||是|最終更新者|
|TGCF_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
