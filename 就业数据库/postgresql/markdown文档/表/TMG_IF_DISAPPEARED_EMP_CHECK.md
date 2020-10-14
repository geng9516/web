# [勤怠]エラーチェック用インターフェース  連携元消失データ(TMG_IF_DISAPPEARED_EMP_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TIDAE_ID|numeric||否|IDカラム|
|TIDAE_CCUSTOMERID|varchar||否|顧客コード|
|TIDAE_CCOMPANYID|varchar||否|法人コード|
|TIDAE_CEMPLOYEEID|varchar||否|職員番号|
|TIDAE_DSTARTDATE|date||否|データ開始日|
|TIDAE_DENDDATE|date||否|データ終了日|
|TIDAE_CMODIFIERUSERID|varchar||是|更新者|
|TIDAE_DMODIFIEDDATE|date||是|更新日時|
|TIDAE_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TIDAE_CKANJINAME|varchar||是|漢字指名|
|TIDAE_CSECTIONID|varchar||是|所属コード|
|TIDAE_CSECTIONNAME|varchar||是|所属名称|
|TIDAE_CDISAPPEAREDSTATUSFLG|varchar||是|処理区分 TMG_DISAPPEAREDSTATUSFLG（0:未処理、1:削除済み、9:無視）|
|TIDAE_DDISAPPEARED|date||是|消失日|
