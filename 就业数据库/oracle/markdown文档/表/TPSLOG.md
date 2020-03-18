# ログテーブル(TPSLOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|NID|NUMBER||否|IDカラム|
|DDATE|DATE||是|発生日時|
|NLEVEL|NUMBER||是|エラーレベル|
|MODULE|VARCHAR2||是|発生モジュール|
|MESSAGE|VARCHAR2||是|エラーメッセージ|
