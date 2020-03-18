# ログテーブル(TPSLOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|NID|numeric||否|IDカラム|
|DDATE|date||是|発生日時|
|NLEVEL|numeric||是|エラーレベル|
|MODULE|varchar||是|発生モジュール|
|MESSAGE|varchar||是|エラーメッセージ|
