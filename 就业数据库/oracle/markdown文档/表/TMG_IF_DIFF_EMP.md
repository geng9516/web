# [勤怠]インターフェース  入退職職員差分データ(TMG_IF_DIFF_EMP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TIDE_DMODIFIERUSERID|VARCHAR2||是|更新者|
|TIDE_DMODIFIEDDATE|DATE||是|更新日時|
|TIDE_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TIDE_CDIFFTABLEID|VARCHAR2||是|テーブル名|
|TIDE_NDIFFTYPE|NUMBER||是|変更の種類(1：追加、2：削除)|
|TIDE_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TIDE_CCOMPANYID|VARCHAR2||否|法人コード|
|TIDE_CEMPLOYEEID|VARCHAR2||否|職員番号|
