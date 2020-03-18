# 広域異動手当DB                                     (DT_DJND0260)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|NVARCHAR2||是|会社区分                                           |
|DSTART|DATE||是|開始日                                             |
|SSTART|NVARCHAR2||是|開始日（和暦）                                     |
|DEND|DATE||是|終了日                                             |
|SEND|NVARCHAR2||是|終了日（和暦）                                     |
|CSHAINNO|NVARCHAR2||否|職員番号                                           |
|START_DTE|NVARCHAR2||否|開始年月日                                         |
|DSTART_DTE|DATE||是|開始年月日（西暦）                                 |
|END_DTE|NVARCHAR2||是|終了年月日                                         |
|DEND_DTE|DATE||是|終了年月日（西暦）                                 |
|SIKY_CDE|NVARCHAR2||是|支給率コード                                       |
|SIKY_NME|NVARCHAR2||是|支給率名称                                         |
|SIKY_RITU|NUMBER||是|支給率                                             |
|KEY_DTE|NVARCHAR2||否|基準日                                             |
|DKEY_DTE|DATE||是|基準日（西暦）                                     |
|KOSIN_USE|NVARCHAR2||是|更新者                                             |
|LAST_PGM|NVARCHAR2||是|更新PGM                                            |
|LAST_DTE|DATE||是|更新日時                                           |
|KOSIN_KBN|NVARCHAR2||是|更新区分                                           |
|CMNUSER|NVARCHAR2||是|ユーザー                                           |
|DMNDATE|DATE||是|更新日                                             |
