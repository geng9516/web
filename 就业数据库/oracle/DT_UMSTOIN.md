# [ﾏｽﾀ]出納員コードマスタ                (DT_UMSTOIN)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|NVARCHAR2||否|会社区分                                                        |
|CQTAIKEIKB|NVARCHAR2||否|給与体系区分                        00固定                          |
|DSTART|DATE||否|適用開始年月                        YYYY/MM/01                    |
|DEND|DATE||否|適用終了年月                        YYYY/MM/末日                    |
|CMNCLIENT|NVARCHAR2||是|更新ﾕｰｻﾞ)端末ID                                                 |
|CMNCOMP|NVARCHAR2||是|更新ﾕｰｻﾞ)会社区分                                                 |
|CMNUSER|NVARCHAR2||是|更新ﾕｰｻﾞ)ﾕｰｻﾞID                                               |
|DMNDATE|DATE||是|更新ﾕｰｻﾞ)更新日時                                                 |
|CODE|NVARCHAR2||否|区分                                                          |
|NAME|NVARCHAR2||是|名称                                                          |
|BKYK_CDE|NVARCHAR2||是|部局コード                                                    |
