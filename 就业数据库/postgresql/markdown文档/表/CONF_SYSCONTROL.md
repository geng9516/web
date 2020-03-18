# システムプロパティマスタ(CONF_SYSCONTROL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CS_ID|numeric||否|IDカラム|
|CS_CCUSTOMERID|varchar||否|顧客ID|
|CS_CPROPERTYNAME|varchar||否|プロパティ名称|
|CS_CPROPERTYVALUE|varchar||是|プロパティ値|
|CS_CPROPERTYDESC|varchar||是|プロパティ説明|
|CS_CCATEGORY|varchar||是|カテゴリ|
|CS_CDELFLG|varchar||否|削除可否フラグ|
|CS_CMODIFIERUSERID|varchar||是|最終更新者|
|CS_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
