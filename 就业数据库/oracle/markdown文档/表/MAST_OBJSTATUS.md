# ステータスコントロールマスタ(MAST_OBJSTATUS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MOS_ID|NUMBER||否|IDカラム|
|MOS_CCUSTOMERID|VARCHAR2||是|顧客コード  |
|MOS_CCOMPANYID|VARCHAR2||是|法人コード  |
|MOS_CAPPID|VARCHAR2||是|アプリケーションID  |
|MOS_CSITEID|VARCHAR2||是|サイトID  |
|MOS_CSTATUSTYPEID|VARCHAR2||是|ステータスタイプ  |
|MOS_CSTATUSID|VARCHAR2||是|ステータスID  |
|MOS_COBJECTID|VARCHAR2||是|オブジェクトID  |
|MOS_NFLG|NUMBER||是|表示可否区分|
|MOS_CEVALLEVELID|VARCHAR2||是|評価レベル識別    |
|MOS_DSTARTDATE|DATE||是|開始日    |
|MOS_DENDDATE|DATE||是|終了日    |
|MOS_CMODIFIER|VARCHAR2||是|最終更新者    |
|MOS_DMODIFY|DATE||是|最終更新日    |
|VERSIONNO|NUMBER||否|バージョンNo|
