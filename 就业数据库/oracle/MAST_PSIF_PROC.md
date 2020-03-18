# 汎用インターフェース　ユーザ個別アプリケーションテーブル(MAST_PSIF_PROC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MPIP_ID|NUMBER||否|IDカラム|
|MPIP_CPROCID|VARCHAR2||是|処理区分|
|MPIP_CTABLE|VARCHAR2||是|処理テーブル|
|MPIP_DSTART|DATE||是|開始日付|
|MPIP_DEND|DATE||是|終了日付|
|MPIP_NSEQ|NUMBER||是|処理順序|
|MPIP_CCOMMAND|VARCHAR2||否|PROCEDURE名|
|MPIP_CCOMMENTS|NVARCHAR2||是|備考|
|MPIP_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MPIP_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
