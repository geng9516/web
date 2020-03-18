# コードDB                         (DT_DJND4001)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|VARCHAR2||是|会社区分                                                        |
|DSTART|DATE||否|開始日                                                         |
|SSTART|VARCHAR2||是|開始日（和暦）                                                     |
|DEND|DATE||是|終了日                                                         |
|SEND|VARCHAR2||是|終了日（和暦）                                                     |
|CDE_SYBT|VARCHAR2||否|コード種別                         コードを識別する種別                    |
|CDE_VAL|VARCHAR2||否|コード番号                         コード                           |
|DTA1|VARCHAR2||是|データ1                                                        |
|DTA2|VARCHAR2||是|データ2                                                        |
|DTA3|VARCHAR2||是|データ3                                                        |
|DTA4|VARCHAR2||是|データ4                                                        |
|DTA5|VARCHAR2||是|データ5                                                        |
|DTA6|VARCHAR2||是|データ6                                                        |
|DTA7|VARCHAR2||是|データ7                                                        |
|DTA8|VARCHAR2||是|データ8                                                        |
|DTA9|VARCHAR2||是|データ9                                                        |
|DTA10|VARCHAR2||是|データ10                                                       |
|DTA11|NVARCHAR2||是|データ11                                                       |
|DTA12|NVARCHAR2||是|データ12                                                       |
|CTRL_CDE1|NVARCHAR2||是|制御コード1                                                      |
|CTRL_CDE2|NVARCHAR2||是|制御コード2                                                      |
|CTRL_CDE3|NVARCHAR2||是|制御コード3                                                      |
|CTRL_CDE4|NVARCHAR2||是|制御コード4                                                      |
|CTRL_CDE5|NVARCHAR2||是|制御コード5                                                      |
|NAME|NVARCHAR2||是|画面用名称                         ★2000.12.12追加                 |
|DNAME|NVARCHAR2||是|ポップアップ用名称                     ★2000.12.12追加                 |
|SNAME|NVARCHAR2||是|正式名称                          ★2000.12.12追加                 |
|RNAME|NVARCHAR2||是|略称                            ★2000.12.12追加                 |
|KNAME01|NVARCHAR2||是|記録名称1                         ★2000.12.12追加                 |
|KNAME02|NVARCHAR2||是|記録名称2                         ★2000.12.12追加                 |
|KNAME03|NVARCHAR2||是|記録名称3                         ★2000.12.12追加                 |
|DNAME_COMMENT|NVARCHAR2||是|ポップアップ用名称ｺﾒﾝﾄ                 2001.03.30(I) 20ﾊﾞｲﾄまで利用      |
|START_DTE|NVARCHAR2||是|基準年月日                         ★2000.11.07(I)                |
|DSTART_DTE|DATE||是|基準年月日（西暦）                                                   |
|END_DTE|NVARCHAR2||是|終了年月日                         ★2000.11.07(I)                |
|DEND_DTE|DATE||是|終了年月日（西暦）                                                   |
|KOSIN_USR|NVARCHAR2||否|更新者                                                         |
|KOSIN_PGM|NVARCHAR2||否|更新プログラム                                                     |
|LAST_DTE|DATE||否|更新日時                                                        |
|KOSIN_KBN|NVARCHAR2||否|更新区分                                                        |
|CMNUSER|NVARCHAR2||是|ﾕｰｻﾞｰ                                                       |
|DMNDATE|DATE||是|更新日                                                         |
