# [勤怠]月次集計データ差異チェックテーブル 月次集計実行より後に勤務実績が修正されている職員・年月を登録(TMG_MO_CHANGE_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMCC_CCUSTOMERID|varchar||否|顧客コード     固定：01|
|TMCC_CCOMPANYID|varchar||否|法人コード|
|TMCC_CEMPLOYEEID|varchar||否|職員番号|
|TMCC_DSTARTDATE|date||否|データ開始日|
|TMCC_DENDDATE|date||否|データ終了日|
|TMCC_CMODIFIERUSERID|varchar||是|更新者|
|TMCC_DMODIFIEDDATE|date||是|更新日|
|TMCC_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
