# テーブル名称マスタ(MAST_DATADICTBL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MDT_ID|numeric||否|IDカラム|
|MDT_CCUSTOMERID|varchar||否|顧客コード|
|MDT_CTABLENAME|varchar||否|テーブルID|
|MDT_NTABLESEQ|numeric||是|テーブル並び順|
|MDT_CTABLEDESC|varchar||是|テーブル名称（別名用ダミー）|
|MDT_CTABLEDESCJA|varchar||是|テーブル名称（日本語）|
|MDT_CTABLEDESCEN|varchar||是|テーブル名称（英語）|
|MDT_CTABLEDESCCH|varchar||是|テーブル名称（中国語）|
|MDT_CTABLEDESC01|varchar||是|テーブル名称（予備１）|
|MDT_CTABLEDESC02|varchar||是|テーブル名称（予備２）|
|MDT_CMODIFIERUSERID|varchar||是|更新者|
|MDT_DMODIFIEDDATE|date||是|更新日|
|VERSIONNO|numeric||否|バージョンNo  |
