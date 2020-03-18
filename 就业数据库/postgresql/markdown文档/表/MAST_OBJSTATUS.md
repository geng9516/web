# ステータスコントロールマスタ(MAST_OBJSTATUS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MOS_ID|numeric||否|IDカラム|
|MOS_CCUSTOMERID|varchar||是|顧客コード  |
|MOS_CCOMPANYID|varchar||是|法人コード  |
|MOS_CAPPID|varchar||是|アプリケーションID  |
|MOS_CSITEID|varchar||是|サイトID  |
|MOS_CSTATUSTYPEID|varchar||是|ステータスタイプ  |
|MOS_CSTATUSID|varchar||是|ステータスID  |
|MOS_COBJECTID|varchar||是|オブジェクトID  |
|MOS_NFLG|numeric||是|表示可否区分|
|MOS_CEVALLEVELID|varchar||是|評価レベル識別    |
|MOS_DSTARTDATE|date||是|開始日    |
|MOS_DENDDATE|date||是|終了日    |
|MOS_CMODIFIER|varchar||是|最終更新者    |
|MOS_DMODIFY|date||是|最終更新日    |
|VERSIONNO|numeric||否|バージョンNo|
