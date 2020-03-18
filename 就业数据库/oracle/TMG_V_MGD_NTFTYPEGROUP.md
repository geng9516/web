# [勤怠/名称マスタ]申請種類グルーピングマスタ(TMG_V_MGD_NTFTYPEGROUP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード(連番)|
|MGD_CNTFTYPEGROUPNAME|NVARCHAR2||是|申請種類グルーピング名|
|MGD_CAVGTIMECALCTYPE|NVARCHAR2||是|平均勤務時間の換算区分|
|MGD_CUSETIMECALCTYPE|NVARCHAR2||是|消化時間の換算区分|
|MGD_CDAYTYPE|NVARCHAR2||是|営業日・暦日区分|
|MGD_CCONTINUING|NVARCHAR2||是|連続チェック有無|
|MGD_NSTART_MONTH|NUMBER||是|年度開始月|
|MGD_NMARGIN4PERIOD|NUMBER||是|起算日グルーピングタイプのための猶予期間|
|MGD_NUSETIMEFRACTIONTYPE|NUMBER||是|消化時間に端数がある場合の換算区分|
