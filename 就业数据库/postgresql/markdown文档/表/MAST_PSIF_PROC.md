# 汎用インターフェース　ユーザ個別アプリケーションテーブル(MAST_PSIF_PROC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MPIP_ID|numeric||否|IDカラム|
|MPIP_CPROCID|varchar||是|処理区分|
|MPIP_CTABLE|varchar||是|処理テーブル|
|MPIP_DSTART|date||是|開始日付|
|MPIP_DEND|date||是|終了日付|
|MPIP_NSEQ|numeric||是|処理順序|
|MPIP_CCOMMAND|varchar||否|PROCEDURE名|
|MPIP_CCOMMENTS|varchar||是|備考|
|MPIP_CMODIFIERUSERID|varchar||是|最終更新者|
|MPIP_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
