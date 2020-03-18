# システムマスタ(MAST_SYSTEM)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MS_ID|NUMBER||否|IDカラム|
|MS_CSYSTEMID_PK|VARCHAR2||否|システムコード|
|MS_CSYSTEMNAME|NVARCHAR2||是|システム名称|
|MS_CSYSTEMNAMEJA|NVARCHAR2||是|システム名称（日本語）|
|MS_CSYSTEMNAMEEN|NVARCHAR2||是|システム名称（英語）|
|MS_CSYSTEMNAMECH|NVARCHAR2||是|システム名称（中国語）|
|MS_CSYSTEMNAME01|NVARCHAR2||是|システム名称（予備１）|
|MS_CSYSTEMNAME02|NVARCHAR2||是|システム名称（予備２）|
|MS_CLANGUAGE|VARCHAR2||是|言語区分|
|MS_DCREATEDDATE|DATE||否|作成日付|
|MS_CDELFLAG|VARCHAR2||否|削除フラグ|
|MS_DDELDATE|DATE||是|削除日付|
|MS_NTYPE|NUMBER||否|システム種別|
|MS_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MS_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
