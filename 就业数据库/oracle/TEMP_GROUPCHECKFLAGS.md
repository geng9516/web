# グループ判定処理制御データ(TEMP_GROUPCHECKFLAGS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGCF_ID|NUMBER||否|IDカラム|
|TGCF_DBEFOREDATE|DATE||是|基準日(変更元)|
|TGCF_DAFTERDATE|DATE||是|基準日(変更先)|
|TGCF_CERRORMESSAGE|VARCHAR2||是|エラーメッセージ|
|TGCF_CSTATUS|VARCHAR2||是|ステータス|
|TGCF_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|TGCF_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
