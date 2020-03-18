# [勤怠/名称マスタ]申請区分サマリー（TMG_NTFTYPE, TMG_NTFTYPE2, TMG_NTFGROUOP）(TMG_V_MGD_NTFTYPES)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CNTFGROUPID|VARCHAR2||是|カテゴリコード|
|MGD_CNTFGROUPNAME|NVARCHAR2||是|カテゴリ名称|
|MGD_NNTFGROUPSEQ|NUMBER||是|カテゴリ表示順|
|MGD_CNTFTYPE|VARCHAR2||是|申請区分コード|
|MGD_CNTFTYPENAME|NVARCHAR2||是|申請区分名称|
|MGD_NNTFTYPESEQ|NUMBER||是|申請区分表示順|
|MGD_CUSABLE_SITES|NVARCHAR2||是|申請可能サイト|
|MGD_NINPUTCTL|NUMBER||是|表示項目タイプ|
|MGD_NREFRECTTYPE|NUMBER||是|反映タイプ 1:終日,2:休憩45分,3:半休,4:相対,5:絶対,6:出張,7:勤務時間指定,8:予定シフト,9:振替|
|MGD_CDESTID|NVARCHAR2||是|反映先の区分|
|MGD_NMINIMUM_UNITINP|NUMBER||是|取得最小単位|
|MGD_CREQUIRED_COMMENT|NVARCHAR2||是|申請区分必須 1:必須,それ以外：非必須|
|MGD_CNOTES|NVARCHAR2||是|注釈|
|MGD_CGROUPINGID|NVARCHAR2||是|申請種類グルーピングコード|
|MGD_CREQUIRED_ATTACHEDFILE|NUMBER||是|添付ファイル必須 1:必須,それ以外:非必須|
|MGD_NALLOWED_TERM|NUMBER||是|申請期間区分 0：月次確定前～勤怠シートが存在する期間,1：月次確定前～カレンダーが存在する期間|
|MGD_NIF4IMSCHEDULE|NUMBER||是|阪大限定・Imartスケジュール連携区分 0：連携しない,1：連携する,2：振替先を連携する|
