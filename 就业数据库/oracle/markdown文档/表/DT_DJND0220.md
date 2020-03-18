# 個人調整手当情報DB                    (DT_DJND0220)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|NVARCHAR2||是|会社区分                          |
|DSTART|DATE||是|開始日                           |
|SSTART|NVARCHAR2||是|開始日（和暦）                       |
|DEND|DATE||是|終了日                           |
|SEND|NVARCHAR2||是|終了日（和暦）                       |
|CSHAINNO|NVARCHAR2||否|職員番号                          |
|START_DTE|NVARCHAR2||否|（調整手当）開始年月日                   |
|DSTART_DTE|DATE||是|（調整手当）開始年月日                   |
|END_DTE|NVARCHAR2||是| 終了年月日                        |
|DEND_DTE|DATE||是| 終了年月日（西暦）                    |
|SIKY_RITU|NUMBER||是| 支給率                          2001.06.16|
|ZIKN_CDE|NVARCHAR2||是| 在勤官署市町村コード                   |
|ZIKN_NME|NVARCHAR2||是| 在勤官署市町村                      |
|KNSYZI_CDE|NVARCHAR2||是| 官署所在地区分コード                   |
|KNSYZI_NME|NVARCHAR2||是| 官署所在地区分                      |
|KEY_DTE|NVARCHAR2||否|基準年月日                         |
|DKEY_DTE|DATE||是|基準年月日（西暦）                     |
|KOSIN_USE|NVARCHAR2||否|更新者                           |
|LAST_PGM|NVARCHAR2||否|更新PGM                         |
|LAST_DTE|DATE||否|更新日時                          |
|KOSIN_KBN|NVARCHAR2||否|更新区分                          |
|DEL_DTE|NVARCHAR2||是|削除年月日                         2002.03.15 削除or修正時基準日|
|DDEL_DTE|DATE||是|削除年月日（西暦）                     |
|CMNUSER|NVARCHAR2||是|ﾕｰｻﾞｰ                         |
|DMNDATE|DATE||是|更新日                           |
