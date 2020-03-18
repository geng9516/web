# [勤怠]月次集計データ作成・対象者一覧ワークテーブル(TMG_WORK_MONTHLYOUTPUTLIST)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TWMO_CCUSTOMERID|varchar||否|顧客コード                    固定：01|
|TWMO_CCOMPANYID|varchar||否|法人コード|
|TWMO_CEMPLOYEEID|varchar||否|職員番号|
|TWMO_DYYYYMM|date||否|該当年月|
|TWMO_DSTARTDATE|date||否|異動歴のデータ開始日|
|TWMO_CMODIFIERUSERID|varchar||是|更新者|
|TWMO_DMODIFIEDDATE|date||是|更新日|
|TWMO_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TWMO_CSECTIONID|varchar||是|所属コード|
|TWMO_CPOSTID|varchar||是|役職コード|
|TWMO_CBASESECTION|varchar||是|部局コード|
|TWMO_NSALARYEXCEPT|numeric||是|給与除外対象フラグ|
