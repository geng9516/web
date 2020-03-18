# [勤怠]月次集計データ作成・年度状況一覧ワークテーブル(TMG_WORK_MO_YEARLIST)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TWMY_CCUSTOMERID|VARCHAR2||否|顧客コード                    固定：01|
|TWMY_CCOMPANYID|VARCHAR2||否|法人コード|
|TWMY_CEMPLOYEEID|VARCHAR2||否|職員番号|
|TWMY_DYYYYMM|DATE||否|該当年月|
|TWMY_CMODIFIERUSERID|VARCHAR2||是|更新者|
|TWMY_DMODIFIEDDATE|DATE||是|更新日|
|TWMY_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TWMY_NFIXEDMONTHLY|NUMBER||是|月次締め状態|
|TWMY_NFIXEDSALARY|NUMBER||是|給与確定状態|
|TWMY_NALERTMSGCNT|NUMBER||是|集計時の問題|
|TWMY_NSALARYDATA|NUMBER||是|月例給与データの有無|
|TWMY_NRETROACTION|NUMBER||是|月例遡及データの有無|
