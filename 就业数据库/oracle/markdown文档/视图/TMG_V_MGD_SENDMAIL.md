# [勤怠/名称マスタ]メール通知設定マスタ(TMG_V_MGD_SENDMAIL)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CID|VARCHAR2||否|メール定義ＩＤ|
|MGD_CMASTERCODE|VARCHAR2||是|マスタコード|
|MGD_CMASTERNAME|NVARCHAR2||是|マスタコード名称|
|MGD_NSEND_FLG|NUMBER||是|実行有無（１：実行する　1以外：実行しない）|
|MGD_NACTION_DAY_FROM|NUMBER||是|未承認：実行日付ＦＲＯＭ|
|MGD_NACTION_DAY_TO|NUMBER||是|未承認：実行日付ＴＯ|
|MGD_OVERTIME_WARNING_LIMIT|NUMBER||是|超勤：閾値（時間）|
|MGD_NADDRESS_FLG|NUMBER||是|超勤：宛先区分|
|MGD_CACTION_YOBI|NVARCHAR2||是|未承認：実行曜日|
|MGD_CCOMMENT|NVARCHAR2||是|予備項目の説明|
