# 非正規職員基本情報DB(DT_DJND6001)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|KOSIN_USE|VARCHAR2||否|更新者|
|LAST_PGM|VARCHAR2||否|更新PGM|
|LAST_DTE|DATE||否|更新日時|
|KOSIN_KBN|VARCHAR2||否|更新区分|
|CMNUSER|VARCHAR2||是|ﾕｰｻﾞ|
|DMNDATE|DATE||是|更新日|
|CCOMPKB|VARCHAR2||是|会社区分|
|DSTART|DATE||否|開始日|
|SSTART|VARCHAR2||是|開始日（和暦）|
|DEND|DATE||是|終了日|
|SEND|VARCHAR2||是|終了日（和暦）|
|CSHAINNO|VARCHAR2||否|職員番号|
|CNAMEKNA|VARCHAR2||是|カナ氏名|
|CNAMEKNJ|VARCHAR2||是|漢字氏名|
|CNAMEEIJI|VARCHAR2||是|アルファベット氏名|
|SEIBETU_KBN|VARCHAR2||是|性別区分|
|SEIBETU_NME|VARCHAR2||是|性別|
|BIRTH_DTE|VARCHAR2||是|生年月日|
|DBIRTH_DTE|DATE||是|生年月日（西暦）|
|DNINYO_START|DATE||是|任用期間開始日|
|SNINYO_START|VARCHAR2||是|任用期間開始日（和暦）|
|DNINYO_END|DATE||是|任用期間終了日|
|SNINYO_END|VARCHAR2||是|任用期間終了日（和暦）|
|SYKSY_CDE|VARCHAR2||是|職種コード|
|SYKSY_NME|VARCHAR2||是|職種|
|SZK_CDE|VARCHAR2||是|所属コード|
|SZK_NME|VARCHAR2||是|所属|
|BKYK_CDE|VARCHAR2||是|部局コード|
|BKYK_NME|VARCHAR2||是|部局名|
|SORT_JYN|NUMBER||是|ソート順|
|HKN_NME|VARCHAR2||是|派遣元|
|C01_CDE|VARCHAR2||是|予備項目_文字コード1|
|C01_NME|VARCHAR2||是|予備項目_文字名称1|
|C02_CDE|VARCHAR2||是|予備項目_文字コード2|
|C02_NME|VARCHAR2||是|予備項目_文字名称2|
|C03_CDE|VARCHAR2||是|予備項目_文字コード3|
|C03_NME|VARCHAR2||是|予備項目_文字名称3|
|C04_CDE|VARCHAR2||是|予備項目_文字コード4|
|C04_NME|VARCHAR2||是|予備項目_文字名称4|
|C05_CDE|VARCHAR2||是|予備項目_文字コード5|
|C05_NME|VARCHAR2||是|予備項目_文字名称5|
|N01_NUM|NUMBER||是|予備項目_数値1|
|N02_NUM|NUMBER||是|予備項目_数値2|
|N03_NUM|NUMBER||是|予備項目_数値3|
|N04_NUM|NUMBER||是|予備項目_数値4|
|N05_NUM|NUMBER||是|予備項目_数値5|
|C01_DTE|VARCHAR2||是|予備項目_日付和暦1|
|D01_DTE|DATE||是|予備項目_日付西暦1|
|C02_DTE|VARCHAR2||是|予備項目_日付和暦2|
|D02_DTE|DATE||是|予備項目_日付西暦2|
|C03_DTE|VARCHAR2||是|予備項目_日付和暦3|
|D03_DTE|DATE||是|予備項目_日付西暦3|
|C04_DTE|VARCHAR2||是|予備項目_日付和暦4|
|D04_DTE|DATE||是|予備項目_日付西暦4|
|C05_DTE|VARCHAR2||是|予備項目_日付和暦5|
|D05_DTE|DATE||是|予備項目_日付西暦5|
