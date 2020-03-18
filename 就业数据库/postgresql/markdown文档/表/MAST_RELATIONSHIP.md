# リレーションシップマスタ(MAST_RELATIONSHIP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MR_ID|numeric||否|IDカラム|
|MR_CDOMAINID_FK|varchar||是|ドメインID|
|MR_CRELATIONSHIPID_PK|varchar||是|リレーションシップID|
|MR_CRELATIONSHIPDESC|varchar||否|リレーションシップ名|
|MR_CLANGUAGE|varchar||是|言語|
|MR_DCREATEDDATE|date||否|作成日|
|MR_CDELFLAG|varchar||否|削除フラグ|
|MR_DDELDATE|date||是|削除日|
|MR_CEVALUATIONLEVELNAME|varchar||是|評価レベル名|
|MR_CEVALUATIONLEVELNAMEJA|varchar||是|評価レベル名（日本語）|
|MR_CEVALUATIONLEVELNAMEEN|varchar||是|評価レベル名（英語）|
|MR_CEVALUATIONLEVELNAMECH|varchar||是|評価レベル名（中国語）|
|MR_CEVALUATIONLEVELNAME01|varchar||是|評価レベル名（予備１）|
|MR_CEVALUATIONLEVELNAME02|varchar||是|評価レベル名（予備２）|
|MR_CEVALUATORLEVELNAME|varchar||是|評価レベル名（評価者用）|
|MR_CEVALUATORLEVELNAMEJA|varchar||是|評価レベル名（評価者用_日本語）|
|MR_CEVALUATORLEVELNAMEEN|varchar||是|評価レベル名（評価者用_英語）|
|MR_CEVALUATORLEVELNAMECH|varchar||是|評価レベル名（評価者用_中国語）|
|MR_CEVALUATORLEVELNAME01|varchar||是|評価レベル名（評価者用_予備１）|
|MR_CEVALUATORLEVELNAME02|varchar||是|評価レベル名（評価者用_予備２）|
|MR_CSYSTEMID|varchar||是|システムコード|
|MR_NWEIGHTAGE|numeric||否|優先順位|
|MR_CCLASSNAME|varchar||是|クラス名|
|MR_NEVALUATION|numeric||是|評価系区分|
|MR_CUSAGE|varchar||是|役割関係使用区分|
|MR_COTHERS|varchar||是|評価除外リレーション区分|
|MR_CMODIFIERUSERID|varchar||是|最終更新者|
|MR_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
