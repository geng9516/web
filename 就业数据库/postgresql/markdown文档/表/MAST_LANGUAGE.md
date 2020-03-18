# 言語区分マスタ(MAST_LANGUAGE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|ML_ID|numeric||否|IDカラム|
|ML_CLANGUAGECODE|varchar||否|言語区分|
|ML_CDBLANGUAGECODE|varchar||否|言語区分（DB検索用）|
|ML_CLANGUAGENAME|varchar||否|言語名称|
|ML_CDSPDATEFORMAT|varchar||否|日付フォーマット（表示用）|
|ML_CINPDATEFORMAT|varchar||否|日付フォーマット（入力用）|
|ML_CDSPTIMEFORMAT|varchar||否|時刻フォーマット（表示用）|
|ML_CINPTIMEFORMAT|varchar||否|時刻フォーマット（入力用）|
|ML_CUSEFLG|varchar||否|使用可否フラグ|
|ML_CMODIFIERUSERID|varchar||是|最終更新者|
|ML_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|varchar||否|バージョンNo|
