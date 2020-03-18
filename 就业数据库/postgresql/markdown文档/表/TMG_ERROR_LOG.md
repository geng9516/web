# [勤怠]エラーログ(TMG_ERROR_LOG)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TEL_NID|numeric||否|IDカラム|
|TEL_DERRDATE|date||是|発生日時|
|TEL_CCHKSYBT|varchar||是|チェック種別|
|TEL_CERRMODULE|varchar||是|発生モジュール|
|TEL_CERRTABLEID|varchar||是|エラーテーブル名|
|TEL_CERRMESSAGE|varchar||是|エラーメッセージ|
|TEL_CERRDT_CCUSTOMERID_CK|varchar||是|データ判別用_顧客コード|
|TEL_CERRDT_CCOMPANYID|varchar||是|データ判別用_法人コード|
|TEL_DERRDT_DSTARTDATE|date||是|データ判別用_データ開始日|
|TEL_DERRDT_DENDDATE|date||是|データ判別用_データ終了日|
|TEL_CERRDT_CEMPLOYEEID_CK|varchar||是|データ判別用_社員番号|
|TEL_CERRDT_CUSERID|varchar||是|データ判別用_ユーザID|
|TEL_CERRDT_CSECTIONID_CK|varchar||是|データ判別用_組織コード|
|TEL_CERRDT_CPOSTID_CK|varchar||是|データ判別用_役職コード|
|TEL_CERRDT_CGENERICGROUPID|varchar||是|データ判別用_名称マスタコード|
|TEL_CERRDT_CGENERICDETAILID_CK|varchar||是|データ判別用_明細データコード|
|TEL_CERRDT_CMASTERCODE|varchar||是|データ判別用_マスタコード|
|TEL_DERRDT_DYYYYMM|date||是|データ判別用_該当年月|
