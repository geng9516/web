# 役割関係定義 空組織チェック テンポラリテーブル(TEMP_RELATIONSHIP_BLANK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TRB_CCUSTOMERID|varchar||否|顧客コード|
|TRB_CMODIFIERUSERID|varchar||否|最終更新者|
|TRB_DMODIFIEDDATE|date||是|最終更新日|
|TRB_CSECTIONID|varchar||是|空組織コード|
|TRB_CUSERID_TO|varchar||是|評価対象者ID|
|TRB_NEVALUATION|numeric||是|不在評価レベル|
|TRB_CREPORTLINE|varchar||是|レポートライン|
