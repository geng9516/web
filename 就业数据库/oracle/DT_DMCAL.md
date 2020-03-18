# (普通昇給)カレンダーマスタ                (DT_DMCAL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|NVARCHAR2||是|会社区分                          |
|DSTART|DATE||是|開始日                           |
|SSTART|NVARCHAR2||是|開始日（和暦）                       |
|DEND|DATE||是|終了日                           |
|SEND|NVARCHAR2||是|終了日（和暦）                       |
|YEAR|NVARCHAR2||否|年                             GYY|
|MONTH|NUMBER||否|月                             |
|ZOKUSEI|NVARCHAR2||是|日付属性                          各桁が日に該当／属性区分0～3|
|KOSIN_USR|NVARCHAR2||否|更新者                           |
|KOSIN_PGM|NVARCHAR2||否|更新プログラム                       |
|LAST_DTE|DATE||否|更新日時                          |
|KOSIN_KBN|NVARCHAR2||否|更新区分                          |
|CMNUSER|NVARCHAR2||是|ﾕｰｻﾞｰ                         |
|DMNDATE|DATE||是|更新日                           |
|DGAITONG|DATE||是|null|
