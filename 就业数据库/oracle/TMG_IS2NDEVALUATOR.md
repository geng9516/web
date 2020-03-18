# 管理監督者[評価者設定]判定テーブル(TMG_IS2NDEVALUATOR)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TI2E_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TI2E_CCOMPANYID|VARCHAR2||否|法人コード|
|TI2E_CEMPLOYEEID|VARCHAR2||否|社員番号|
|TI2E_CUSERID|VARCHAR2||否|ユーザーID|
|TI2E_DSTARTDATE|DATE||是|開始年月日|
|TI2E_DENDDATE|DATE||是|終了年月日|
|TI2E_ISEVALUATOR|NUMBER||是|権限者フラグ[二次評価者相当]|
