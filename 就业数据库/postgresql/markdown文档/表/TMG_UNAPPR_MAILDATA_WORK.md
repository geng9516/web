# メール送信ワークテーブル(TMG_UNAPPR_MAILDATA_WORK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TUMW_ID|numeric||否|IDカラム|
|TUMW_CCUSTOMERID|varchar||否|顧客コード|
|TUMW_CCOMPANYID|varchar||否|法人コード|
|TUMW_CMODIFIERUSERID|varchar||是|最終更新者|
|TUMW_DMODIFIEDDATE|date||是|最終更新日|
|TUMW_CMODIFIERPROGRAMID|varchar||是|最終更新プログラムID|
|TUMW_CEVENTID|varchar||是|イベントID|
|TUMW_CID|varchar||是|メール定義ID|
|TUMW_CTOEMPLOYEEID|varchar||是|送信先の職員番号|
|THMW_CTOEMPLOYEE_NAME|varchar||是|送信先の職員名称|
|THMW_CTOADDRESS|varchar||是|送信先の職員メールアドレス|
|TUMW_CEMPLOYEEID|varchar||是|未承認の職員番号|
|TUMW_CEMPLOYEE_NAME|varchar||是|未承認の職員名称|
|TUMW_CSECTIONID|varchar||是|部局コード|
|TUMW_CGROUPID|varchar||是|グループコード|
|TUMW_CGROUPNAME|varchar||是|グループ名称|
|TUMW_CEVA_SECTIONID|varchar||是|承認部局コード|
|TUMW_NMP_WEIGHT|numeric||是|役職順位|
|TUMW_NSORT|numeric||是|出力順|
|TUMW_NADDRESS_FLG|numeric||是|超勤：宛先区分（1:本人 2:承認者）|
|TUMW_NOVERTIME_MAX_LIMIT|numeric||是|超勤：閾値（時間）|
|TUMW_NOVERTIME_VALUE|numeric||是|超勤した時間（分単位）|
