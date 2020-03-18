# システムプロパティマスタ(CONF_SYSCONTROL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CS_ID|NUMBER||否|IDカラム|
|CS_CCUSTOMERID|VARCHAR2||否|顧客ID|
|CS_CPROPERTYNAME|NVARCHAR2||否|プロパティ名称|
|CS_CPROPERTYVALUE|NVARCHAR2||是|プロパティ値|
|CS_CPROPERTYDESC|NVARCHAR2||是|プロパティ説明|
|CS_CCATEGORY|VARCHAR2||是|カテゴリ|
|CS_CDELFLG|VARCHAR2||否|削除可否フラグ|
|CS_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|CS_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
