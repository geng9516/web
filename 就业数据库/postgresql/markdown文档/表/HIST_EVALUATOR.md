# 評価者判定結果テーブル(HIST_EVALUATOR)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HEV_ID|numeric||否|IDカラム|
|HEV_CCUSTOMERID|varchar||否|顧客コード|
|HEV_CUSERID|varchar||否|評価対象者ユーザID|
|HEV_DSTARTDATE|date||否|開始日|
|HEV_DENDDATE|date||否|終了日|
|HEV_NLEVEL|numeric||否|評価レベル|
|HEV_CRELINETYPE|varchar||否|レポートラインタイプ|
|HEV_CHONKEN|varchar||否|本務/兼務区分|
|HEV_CDECISIONTYPE|varchar||否|判定区分|
|HEV_CEVALUATEE|varchar||否|評価者ユーザID|
|HEV_CKANJINAME|varchar||否|評価者ユーザ漢字氏名|
|HEV_CMODIFIERUSERID|varchar||是|最終更新者|
|HEV_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
