# 役割関係定義 テンポラリテーブル(TEMP_RELATIONSHIP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TR_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TR_CMODIFIERUSERID|VARCHAR2||否|最終更新者|
|TR_DMODIFIEDDATE|DATE||是|最終更新日|
|TR_NEVALUATION|NUMBER||是|評価レベル|
|TR_CREPORTLINE|VARCHAR2||是|レポートライン|
|TR_CUSERID_FROM|VARCHAR2||是|評価者ユーザID|
|TR_CUSERID_TO|VARCHAR2||是|評価対象者ユーザID|
