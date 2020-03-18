# [勤怠/名称マスタ]年5日の年休取得調査対象年休反映区分マスタ(TMG_V_MGD_ACQUIRED5DAY_TGT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CNTFNAME|NVARCHAR2||是|申請名称|
|MGD_NKIND|NUMBER||是|（1:終日、2:半日(前半)、3:半日(後半)、4:時間） |
|MGD_CWORKNOTWORKTYPE|NVARCHAR2||是|反映先の就業区分・非勤務区分|
