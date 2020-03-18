# テーブル名称マスタ(MAST_DATADICTBL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MDT_DMODIFIEDDATE|DATE||是|更新日|
|VERSIONNO|NUMBER||否|バージョンNo  |
|MDT_ID|NUMBER||否|IDカラム|
|MDT_CCUSTOMERID|VARCHAR2||否|顧客コード|
|MDT_CTABLENAME|VARCHAR2||否|テーブルID|
|MDT_NTABLESEQ|NUMBER||是|テーブル並び順|
|MDT_CTABLEDESC|NVARCHAR2||是|テーブル名称（別名用ダミー）|
|MDT_CTABLEDESCJA|NVARCHAR2||是|テーブル名称（日本語）|
|MDT_CTABLEDESCEN|NVARCHAR2||是|テーブル名称（英語）|
|MDT_CTABLEDESCCH|NVARCHAR2||是|テーブル名称（中国語）|
|MDT_CTABLEDESC01|NVARCHAR2||是|テーブル名称（予備１）|
|MDT_CTABLEDESC02|NVARCHAR2||是|テーブル名称（予備２）|
|MDT_CMODIFIERUSERID|VARCHAR2||是|更新者|
