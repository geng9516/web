# [勤怠]月次集計データ作成・年度状況一覧ワークテーブル(TMG_WORK_MO_YEARLIST)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TWMY_CCUSTOMERID|varchar||否|顧客コード                    固定：01|
|TWMY_CCOMPANYID|varchar||否|法人コード|
|TWMY_CEMPLOYEEID|varchar||否|職員番号|
|TWMY_DYYYYMM|date||否|該当年月|
|TWMY_CMODIFIERUSERID|varchar||是|更新者|
|TWMY_DMODIFIEDDATE|date||是|更新日|
|TWMY_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TWMY_NFIXEDMONTHLY|numeric||是|月次締め状態|
|TWMY_NFIXEDSALARY|numeric||是|給与確定状態|
|TWMY_NALERTMSGCNT|numeric||是|集計時の問題|
|TWMY_NSALARYDATA|numeric||是|月例給与データの有無|
|TWMY_NRETROACTION|numeric||是|月例遡及データの有無|
