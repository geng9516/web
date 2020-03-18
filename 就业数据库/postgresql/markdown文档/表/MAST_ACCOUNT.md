# アカウントマスタ(MAST_ACCOUNT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MA_ID|numeric||否|IDカラム|
|MA_CCUSTOMERID|varchar||是|顧客コード|
|MA_CUSERID|varchar||是|ユーザID|
|MA_CACCOUNT|varchar||是|アカウント|
|MA_DSTART|date||是|有効期間開始日|
|MA_DEND|date||是|有効期間終了日|
|MA_NRETRYCOUNTER|numeric||是|パスワード間違い回数|
|MA_NPASSWORDLOCK|numeric||是|ロックアウトフラグ|
|MA_CADMINUSER|varchar||是|管理ツールユーザフラグ|
|MA_DCREATE|date||是|アカウント作成日|
|MA_CMODIFIERUSERID|varchar||是|最終更新者|
|MA_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
