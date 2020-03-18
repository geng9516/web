# 評価者判定結果テーブル(HIST_EVALUATOR)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HEV_ID|NUMBER||否|IDカラム|
|HEV_CCUSTOMERID|VARCHAR2||否|顧客コード|
|HEV_CUSERID|VARCHAR2||否|評価対象者ユーザID|
|HEV_DSTARTDATE|DATE||否|開始日|
|HEV_DENDDATE|DATE||否|終了日|
|HEV_NLEVEL|NUMBER||否|評価レベル|
|HEV_CRELINETYPE|VARCHAR2||否|レポートラインタイプ|
|HEV_CHONKEN|VARCHAR2||否|本務/兼務区分|
|HEV_CDECISIONTYPE|VARCHAR2||否|判定区分|
|HEV_CEVALUATEE|VARCHAR2||否|評価者ユーザID|
|HEV_CKANJINAME|NVARCHAR2||否|評価者ユーザ漢字氏名|
|HEV_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|HEV_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
