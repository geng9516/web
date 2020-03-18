# [勤怠/名称マスタ]就業区分マスタ(TMG_V_MGD_WORK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CWORKID|VARCHAR2||是|就業区分コード|
|MGD_CWORKNM|NVARCHAR2||是|就業区分名称|
|MGD_CNICKNM|NVARCHAR2||是|略称|
|MGD_CSELECTABLEWORKID4RESULTS|NVARCHAR2||是|[勤務実績登録/承認]選択可能な就業区分リスト(予定=MGD_CWORKID の場合)|
|MGD_NHOLFLG|NUMBER||是|[勤務実績登録/承認]実績登録時の休日フラグ(名称マスタTMG_HOLFLGの明細IDのみ指定)|
|MGD_CCSSCOLOR4ATTENDANCEBOOK|NVARCHAR2||是|[出勤簿]背景色のCSSクラスID|
|MGD_NUSABLE4SCHEDULE|NUMBER||是|[予定作成]編集画面での使用可否|
|MGD_NATTENDRATECD|NUMBER||是|[年休付与]出勤率算定区分(出勤とみなすかどうか)|
|MGD_NREFLECTNTFCD|NUMBER||是|[申請反映]反映可能な申請タイプ(タイプごとの反映可否をビット列で定義→10進形式で保持)|
