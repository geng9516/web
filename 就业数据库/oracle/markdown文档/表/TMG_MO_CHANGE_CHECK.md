# [勤怠]月次集計データ差異チェックテーブル 月次集計実行より後に勤務実績が修正されている職員・年月を登録(TMG_MO_CHANGE_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMCC_CCUSTOMERID|VARCHAR2||否|顧客コード     固定：01|
|TMCC_CCOMPANYID|VARCHAR2||否|法人コード|
|TMCC_CEMPLOYEEID|VARCHAR2||否|職員番号|
|TMCC_DSTARTDATE|DATE||否|データ開始日|
|TMCC_DENDDATE|DATE||否|データ終了日|
|TMCC_CMODIFIERUSERID|VARCHAR2||是|更新者|
|TMCC_DMODIFIEDDATE|DATE||是|更新日|
|TMCC_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
