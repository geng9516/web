# 掲示板データ(HIST_BULLETINBOARD)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HB_ID|numeric||否|ID|
|HB_CCUSTOMERID|varchar||是|顧客コード|
|HB_CCOMPANYID|varchar||是|法人コード|
|HB_DDATEOFANNOUNCEMENT|date||是|掲示開始日|
|HB_DDATEOFEXPIRE|date||是|掲示終了日|
|HB_CTITLE|varchar||是|タイトル|
|HB_CCONTENTS|varchar||是|掲示内容|
|HB_CFILENAME|varchar||是|添付ファイル名|
|HB_CLINK|varchar||是|関連情報リンク|
|HB_CMNUSER|varchar||是|掲示者ユーザID|
|HB_CMNUSERNAME|varchar||是|掲示者氏名|
|HB_BATTACH|bytea||是|添付ファイル|
|HB_CHEADDISP|varchar||是|先頭表示フラグ|
|HB_CFIX|varchar||是|確定フラグ|
|HB_CMODIFIERUSERID|varchar||是|最終更新者|
|HB_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
