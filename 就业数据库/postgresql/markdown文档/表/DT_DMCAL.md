# (普通昇給)カレンダーマスタ                (DT_DMCAL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|varchar||是|会社区分                          |
|DSTART|date||是|開始日                           |
|SSTART|varchar||是|開始日（和暦）                       |
|DEND|date||是|終了日                           |
|SEND|varchar||是|終了日（和暦）                       |
|YEAR|varchar||否|年                             GYY|
|MONTH|numeric||否|月                             |
|ZOKUSEI|varchar||是|日付属性                          各桁が日に該当／属性区分0～3|
|KOSIN_USR|varchar||否|更新者                           |
|KOSIN_PGM|varchar||否|更新プログラム                       |
|LAST_DTE|date||否|更新日時                          |
|KOSIN_KBN|varchar||否|更新区分                          |
|CMNUSER|varchar||是|ﾕｰｻﾞｰ                         |
|DMNDATE|date||是|更新日                           |
|DGAITONG|date||是|null|
