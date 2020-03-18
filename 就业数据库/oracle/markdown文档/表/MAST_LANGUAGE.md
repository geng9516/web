# 言語区分マスタ(MAST_LANGUAGE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|ML_ID|NUMBER||否|IDカラム|
|ML_CLANGUAGECODE|VARCHAR2||否|言語区分|
|ML_CDBLANGUAGECODE|VARCHAR2||否|言語区分（DB検索用）|
|ML_CLANGUAGENAME|NVARCHAR2||否|言語名称|
|ML_CDSPDATEFORMAT|VARCHAR2||否|日付フォーマット（表示用）|
|ML_CINPDATEFORMAT|VARCHAR2||否|日付フォーマット（入力用）|
|ML_CDSPTIMEFORMAT|VARCHAR2||否|時刻フォーマット（表示用）|
|ML_CINPTIMEFORMAT|VARCHAR2||否|時刻フォーマット（入力用）|
|ML_CUSEFLG|VARCHAR2||否|使用可否フラグ|
|ML_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|ML_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|VARCHAR2||否|バージョンNo|
