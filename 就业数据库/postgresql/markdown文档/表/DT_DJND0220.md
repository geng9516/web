# 個人調整手当情報DB                    (DT_DJND0220)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|varchar||是|会社区分                          |
|DSTART|date||是|開始日                           |
|SSTART|varchar||是|開始日（和暦）                       |
|DEND|date||是|終了日                           |
|SEND|varchar||是|終了日（和暦）                       |
|CSHAINNO|varchar||否|職員番号                          |
|START_DTE|varchar||否|（調整手当）開始年月日                   |
|DSTART_DTE|date||是|（調整手当）開始年月日                   |
|END_DTE|varchar||是| 終了年月日                        |
|DEND_DTE|date||是| 終了年月日（西暦）                    |
|SIKY_RITU|numeric||是| 支給率                          2001.06.16|
|ZIKN_CDE|varchar||是| 在勤官署市町村コード                   |
|ZIKN_NME|varchar||是| 在勤官署市町村                      |
|KNSYZI_CDE|varchar||是| 官署所在地区分コード                   |
|KNSYZI_NME|varchar||是| 官署所在地区分                      |
|KEY_DTE|varchar||否|基準年月日                         |
|DKEY_DTE|date||是|基準年月日（西暦）                     |
|KOSIN_USE|varchar||否|更新者                           |
|LAST_PGM|varchar||否|更新PGM                         |
|LAST_DTE|date||否|更新日時                          |
|KOSIN_KBN|varchar||否|更新区分                          |
|DEL_DTE|varchar||是|削除年月日                         2002.03.15 削除or修正時基準日|
|DDEL_DTE|date||是|削除年月日（西暦）                     |
|CMNUSER|varchar||是|ﾕｰｻﾞｰ                         |
|DMNDATE|date||是|更新日                           |
