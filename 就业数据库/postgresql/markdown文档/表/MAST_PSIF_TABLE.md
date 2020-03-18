# 汎用インターフェース　処理対象テーブル(MAST_PSIF_TABLE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MPIT_ID|numeric||否|IDカラム|
|MPIT_CTABLEID|varchar||是|テーブルID|
|MPIT_CUSEFLG|varchar||否|使用フラグ|
|MPIT_CSEQUENCE|varchar||是|シーケンスID|
|MPIT_CSEQUENCECOLUMN|varchar||是|シーケンス対応カラム|
|MPIT_CSEQUENCEMODE|varchar||是|シーケンスモード|
|MPIT_CMODIFIERUSERID|varchar||是|最終更新者|
|MPIT_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
