# 汎用インターフェース　処理対象テーブル(MAST_PSIF_TABLE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MPIT_CTABLEID|VARCHAR2||是|テーブルID|
|MPIT_CUSEFLG|VARCHAR2||否|使用フラグ|
|MPIT_CSEQUENCE|VARCHAR2||是|シーケンスID|
|MPIT_CSEQUENCECOLUMN|VARCHAR2||是|シーケンス対応カラム|
|MPIT_CSEQUENCEMODE|VARCHAR2||是|シーケンスモード|
|MPIT_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MPIT_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
|MPIT_ID|NUMBER||否|IDカラム|
