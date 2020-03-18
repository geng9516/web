# 月次集計実行結果(TMG_V_CDS_MONTHLY_TOTALIZATION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TVCMT_CCUSTOMERID|VARCHAR2||否|[月次集計結果]顧客コード|
|TVCMT_CCOMPANYID|VARCHAR2||否|[月次集計結果]法人コード|
|TVCMT_CEMPLOYEEID|VARCHAR2||否|[月次集計結果]社員番号|
|TVCMT_CUSERID|VARCHAR2||否|[月次集計結果]ユーザーID|
|TVCMT_DSTARTDATE|DATE||否|[月次集計結果]データ開始日|
|TVCMT_DENDDATE|DATE||是|[月次集計結果]データ終了日|
|TVCMT_CMODIFIERUSERID|VARCHAR2||是|[月次集計結果]更新者|
|TVCMT_DMODIFIEDDATE|DATE||是|[月次集計結果]更新日|
|TVCMT_CMODIFIERPROGRAMID|VARCHAR2||是|[月次集計結果]更新プログラムID|
|TVCMT_DYYYYMM|DATE||否|[月次集計結果]年月|
|TVCMT_CTOTALIZATIONID|VARCHAR2||否|[月次集計結果]集計コード|
|TVCMT_CTOTALIZATIONNM|VARCHAR2||是|[月次集計結果]集計名称|
|TVCMT_CJOURNALIZINGID|VARCHAR2||是|[月次集計結果]仕訳コード|
|TVCMT_CJOURNALIZINGNM|VARCHAR2||是|[月次集計結果]仕訳名称|
|TVCMT_NVALUE|NUMBER||否|[月次集計結果]集計値|
