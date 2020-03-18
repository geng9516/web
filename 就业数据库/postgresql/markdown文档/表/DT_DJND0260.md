# 広域異動手当DB                                     (DT_DJND0260)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|varchar||是|会社区分                                           |
|DSTART|date||是|開始日                                             |
|SSTART|varchar||是|開始日（和暦）                                     |
|DEND|date||是|終了日                                             |
|SEND|varchar||是|終了日（和暦）                                     |
|CSHAINNO|varchar||否|職員番号                                           |
|START_DTE|varchar||否|開始年月日                                         |
|DSTART_DTE|date||是|開始年月日（西暦）                                 |
|END_DTE|varchar||是|終了年月日                                         |
|DEND_DTE|date||是|終了年月日（西暦）                                 |
|SIKY_CDE|varchar||是|支給率コード                                       |
|SIKY_NME|varchar||是|支給率名称                                         |
|SIKY_RITU|numeric||是|支給率                                             |
|KEY_DTE|varchar||否|基準日                                             |
|DKEY_DTE|date||是|基準日（西暦）                                     |
|KOSIN_USE|varchar||是|更新者                                             |
|LAST_PGM|varchar||是|更新PGM                                            |
|LAST_DTE|date||是|更新日時                                           |
|KOSIN_KBN|varchar||是|更新区分                                           |
|CMNUSER|varchar||是|ユーザー                                           |
|DMNDATE|date||是|更新日                                             |
