# 日次集計実行結果(TMG_V_CDS_DAILY_TOTALIZATION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TVCDT_CCUSTOMERID|VARCHAR2||否|[日次集計結果]顧客コード|
|TVCDT_CCOMPANYID|VARCHAR2||否|[日次集計結果]法人コード|
|TVCDT_CEMPLOYEEID|VARCHAR2||否|[日次集計結果]社員番号|
|TVCDT_CUSERID|VARCHAR2||否|[日次集計結果]ユーザーID|
|TVCDT_DSTARTDATE|DATE||否|[日次集計結果]データ開始日|
|TVCDT_DENDDATE|DATE||否|[日次集計結果]データ終了日|
|TVCDT_CMODIFIERUSERID|VARCHAR2||是|[日次集計結果]更新者|
|TVCDT_DMODIFIEDDATE|DATE||是|[日次集計結果]更新日|
|TVCDT_CMODIFIERPROGRAMID|VARCHAR2||是|[日次集計結果]更新プログラムID|
|TVCDT_DYYYYMMDD|DATE||否|[日次集計結果]年月日|
|TVCDT_DTARGETDATE|DATE||否|[日次集計結果]集計先年月日|
|TVCDT_CTOTALIZATIONID|VARCHAR2||否|[日次集計結果]集計コード|
|TVCDT_CTOTALIZATIONNM|VARCHAR2||是|[日次集計結果]集計名称|
|TVCDT_CJOURNALIZINGID|VARCHAR2||是|[日次集計結果]仕訳コード|
|TVCDT_CJOURNALIZINGNM|VARCHAR2||是|[日次集計結果]仕訳名称|
|TVCDT_NVALUE|NUMBER||否|[日次集計結果]集計値|
