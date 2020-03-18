# 個人履歴情報DB                      (DT_DJND0310)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|varchar||是|会社区分                          |
|DSTART|date||是|開始日                           |
|SSTART|varchar||是|開始日（和暦）                       |
|DEND|date||是|終了日                           |
|SEND|varchar||是|終了日（和暦）                       |
|CSHAINNO|varchar||否|職員番号                          |
|SYMK_KBN|varchar||否|種目区分                          |
|SEQ_NUM|numeric||否|同日SEQ                         |
|HTREINGB_DTE|varchar||否|発令年月日                         |
|DHTREINGB_DTE|date||是|発令年月日（西暦）                     |
|IDO_CDE|varchar||否|異動種目コード                       |
|IDO_NME|varchar||是|異動種目                          |
|REKI1_CDE|varchar||是|コード（１）                        |
|REKI1_NME|varchar||是|名称（１）                         2001.06.16|
|REKI2_CDE|varchar||是|コード（２）                        |
|REKI2_NME|varchar||是|名称（２）                         2001.06.16|
|REKI3_CDE|varchar||是|コード（３）                        |
|REKI3_NME|varchar||是|名称（３）                         2001.06.16|
|REKI4_CDE|varchar||是|コード（４）                        |
|REKI4_NME|varchar||是|名称（４）                         2001.06.16|
|REKI5_CDE|varchar||是|コード（５）                        |
|REKI5_NME|varchar||是|名称（５）                         2001.06.16|
|REKI1_NUM|numeric||是|数字（１）                         |
|REKI2_NUM|numeric||是|数字（２）                         |
|REKI3_NUM|numeric||是|数字（３）                         |
|REKI1_DTE|varchar||是|日付（１）                         |
|DREKI1_DTE|date||是|日付（１）（西暦）                     |
|REKI2_DTE|varchar||是|日付（２）                         |
|DREKI2_DTE|date||是|日付（２）（西暦）                     |
|KEY_DTE|varchar||否|基準年月日                         |
|DKEY_DTE|date||是|基準年月日（西暦）                     |
|KOSIN_USE|varchar||否|更新者                           |
|LAST_PGM|varchar||否|更新PGM                         |
|LAST_DTE|date||否|更新日時                          |
|KOSIN_KBN|varchar||否|更新区分                          |
|REKI1_FLG|varchar||是|フラグ（１）                        |
|REKI2_FLG|varchar||是|フラグ（２）                        |
|REKI6_CDE|varchar||是|コード（６）                        |
|REKI6_NME|varchar||是|名称（６）                         2001.06.16|
|REKI7_CDE|varchar||是|コード（７）                        2001.11.22 ADD|
|REKI7_NME|varchar||是|名称（７）                         2001.11.22 ADD|
|REKI8_CDE|varchar||是|コード（８）                        2001.11.22 ADD|
|REKI8_NME|varchar||是|名称（８）                         2001.11.22 ADD|
|REKI9_CDE|varchar||是|コード（９）                        2001.11.22 ADD|
|REKI9_NME|varchar||是|名称（９）                         2001.11.22 ADD|
|REKI4_NUM|numeric||是|数字（４）                         2001.11.22 ADD|
|REKI5_NUM|numeric||是|数字（５）                         2001.11.22 ADD|
|CMNUSER|varchar||是|ﾕｰｻﾞｰ                         |
|DMNDATE|date||是|更新日                           |
