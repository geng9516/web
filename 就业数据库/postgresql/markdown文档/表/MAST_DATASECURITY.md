# データセキュリティマスタ(MAST_DATASECURITY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MDS_ID|numeric||否|IDカラム|
|MDS_COBJECTID|varchar||否|オブジェクトID|
|MDS_CSITEID|varchar||是|サイトID  |
|MDS_CAPPID|varchar||是|アプリケーションID  |
|MDS_CBEHAVIOR_FLG|varchar||否|ビヘイビア適用フラグ|
|MDS_CUNDER_FLG|varchar||是|下位適用フラグ|
|MDS_CKINDDATESELECT|varchar||否|日付種類選択|
|MDS_CDATEMODE|varchar||否|年月日モード|
|MDS_NASPECT_START|numeric||是|相対過去対象|
|MDS_NASPECT_END|numeric||是|相対未来対象|
|MDS_CASPECT_CLOSE|varchar||是|相対締め日|
|MDS_DABSOLUTE_START|date||是|絶対開始日|
|MDS_DABSOLUTE_END|date||是|絶対終了日|
|MDS_CMODIFIERUSERID|varchar||是|最終更新者|
|MDS_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
