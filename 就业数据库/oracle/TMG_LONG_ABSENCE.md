# 長期休暇・出勤停止者データ(TMG_LONG_ABSENCE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TLA_ID|NUMBER||否|IDカラム|
|TLA_CCUSTOMERID|VARCHAR2||否|顧客コード |
|TLA_CCOMPANYID|VARCHAR2||否|法人コード |
|TLA_CEMPLOYEEID|VARCHAR2||否|個人コード |
|TLA_CUSERID|VARCHAR2||否|ユーザID |
|TLA_DSTARTDATE|DATE||否|データ開始日 |
|TLA_DENDDATE|DATE||是|データ終了日 |
|TLA_D20DAYS|DATE||是|20日を超える日(休日除く) |
|TLA_D90DAYS|DATE||是|90日経過後(休日含む)の最初の労働日 |
|VERSIONNO|NUMBER||否|バージョンNO V4互換用 |
