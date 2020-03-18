# [勤怠]月次集計データ作成・対象者一覧ワークテーブル(TMG_WORK_MONTHLYOUTPUTLIST)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TWMO_CCUSTOMERID|VARCHAR2||否|顧客コード                    固定：01|
|TWMO_CCOMPANYID|VARCHAR2||否|法人コード|
|TWMO_CEMPLOYEEID|VARCHAR2||否|職員番号|
|TWMO_DYYYYMM|DATE||否|該当年月|
|TWMO_DSTARTDATE|DATE||否|異動歴のデータ開始日|
|TWMO_CMODIFIERUSERID|VARCHAR2||是|更新者|
|TWMO_DMODIFIEDDATE|DATE||是|更新日|
|TWMO_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TWMO_CSECTIONID|VARCHAR2||是|所属コード|
|TWMO_CPOSTID|VARCHAR2||是|役職コード|
|TWMO_CBASESECTION|VARCHAR2||是|部局コード|
|TWMO_NSALARYEXCEPT|NUMBER||是|給与除外対象フラグ|
