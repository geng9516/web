# 役割関係定義 テンポラリテーブル(TEMP_RELATIONSHIP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TR_CCUSTOMERID|varchar||否|顧客コード|
|TR_CMODIFIERUSERID|varchar||否|最終更新者|
|TR_DMODIFIEDDATE|date||是|最終更新日|
|TR_NEVALUATION|numeric||是|評価レベル|
|TR_CREPORTLINE|varchar||是|レポートライン|
|TR_CUSERID_FROM|varchar||是|評価者ユーザID|
|TR_CUSERID_TO|varchar||是|評価対象者ユーザID|
