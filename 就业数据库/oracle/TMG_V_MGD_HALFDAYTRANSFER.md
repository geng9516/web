# [勤怠/名称マスタ]半日振替の詳細設定マスタ(TMG_V_MGD_HALFDAYTRANSFER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CNTFTYPE|NVARCHAR2||是|申請種類|
|MGD_CNTFTYPE_OTHER_HALF|NVARCHAR2||是|ダブル半日振休の組み合わせ可能な申請種類|
|MGD_CWORK_W_HF_HOLIDAY|NVARCHAR2||是|半日振休をダブルで取得した場合の就業区分（CWORK DOUBLE HALF HOLIDAY）|
|MGD_ACTUAL_HOURS|NUMBER||是|指定時間数（分数）|
|MGD_PART_OF_DAY|NUMBER||是|始業・終業区分|
