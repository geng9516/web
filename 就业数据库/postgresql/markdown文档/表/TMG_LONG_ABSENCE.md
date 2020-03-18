# 長期休暇・出勤停止者データ(TMG_LONG_ABSENCE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TLA_ID|numeric||否|IDカラム|
|TLA_CCUSTOMERID|varchar||否|顧客コード |
|TLA_CCOMPANYID|varchar||否|法人コード |
|TLA_CEMPLOYEEID|varchar||否|個人コード |
|TLA_CUSERID|varchar||否|ユーザID |
|TLA_DSTARTDATE|date||否|データ開始日 |
|TLA_DENDDATE|date||是|データ終了日 |
|TLA_D20DAYS|date||是|20日を超える日(休日除く) |
|TLA_D90DAYS|date||是|90日経過後(休日含む)の最初の労働日 |
|VERSIONNO|numeric||否|バージョンNO V4互換用 |
