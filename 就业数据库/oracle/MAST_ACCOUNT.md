# アカウントマスタ(MAST_ACCOUNT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MA_ID|NUMBER||否|IDカラム|
|MA_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MA_CUSERID|VARCHAR2||是|ユーザID|
|MA_CACCOUNT|VARCHAR2||是|アカウント|
|MA_DSTART|DATE||是|有効期間開始日|
|MA_DEND|DATE||是|有効期間終了日|
|MA_NRETRYCOUNTER|NUMBER||是|パスワード間違い回数|
|MA_NPASSWORDLOCK|NUMBER||是|ロックアウトフラグ|
|MA_CADMINUSER|VARCHAR2||是|管理ツールユーザフラグ|
|MA_DCREATE|DATE||是|アカウント作成日|
|MA_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MA_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
