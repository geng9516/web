# システムマスタ(MAST_SYSTEM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MS_ID|numeric||否|IDカラム|
|MS_CSYSTEMID_PK|varchar||否|システムコード|
|MS_CSYSTEMNAME|varchar||是|システム名称|
|MS_CSYSTEMNAMEJA|varchar||是|システム名称（日本語）|
|MS_CSYSTEMNAMEEN|varchar||是|システム名称（英語）|
|MS_CSYSTEMNAMECH|varchar||是|システム名称（中国語）|
|MS_CSYSTEMNAME01|varchar||是|システム名称（予備１）|
|MS_CSYSTEMNAME02|varchar||是|システム名称（予備２）|
|MS_CLANGUAGE|varchar||是|言語区分|
|MS_DCREATEDDATE|date||否|作成日付|
|MS_CDELFLAG|varchar||否|削除フラグ|
|MS_DDELDATE|date||是|削除日付|
|MS_NTYPE|numeric||否|システム種別|
|MS_CMODIFIERUSERID|varchar||是|最終更新者|
|MS_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
