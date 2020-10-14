# [勤怠]インターフェース  入退職職員差分データ(TMG_IF_DIFF_EMP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TIDE_CCUSTOMERID|varchar||否|顧客コード|
|TIDE_CCOMPANYID|varchar||否|法人コード|
|TIDE_CEMPLOYEEID|varchar||否|職員番号|
|TIDE_DMODIFIERUSERID|varchar||是|更新者|
|TIDE_DMODIFIEDDATE|date||是|更新日時|
|TIDE_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TIDE_CDIFFTABLEID|varchar||是|テーブル名|
|TIDE_NDIFFTYPE|numeric||是|変更の種類(1：追加、2：削除)|
