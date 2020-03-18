# 出張テーブル(TMG_HIST_BIZTRIP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THB_CCUSTOMERID|varchar||否|顧客コード|
|THB_CCOMPANYID|varchar||否|法人コード|
|THB_DSTART|date||否|データ開始日|
|THB_DEND|date||否|データ終了日|
|THB_CEMPLOYEEID|varchar||是|職員番号|
|THB_CKANJINAME|varchar||是|氏名|
|THB_CSECTIONNAME|varchar||是|所属|
|THB_CBIZTRIPID|varchar||是|出張番号|
|THB_CBIZTRIPSTART_CHECK|varchar||是|出張開始日(チェック用)|
|THB_CBIZTRIPEND_CHECK|varchar||是|出張終了日(チェック用)|
|THB_CDELETE_FLG_CHECK|varchar||是|削除フラグ(チェック用)|
|THB_DBIZTRIPSTART|date||是|出張開始日|
|THB_DBIZTRIPEND|date||是|出張終了日|
|THB_CIMPORTID|varchar||否|取込番号|
|THB_NROWNO|numeric||否|取込行番号|
|THB_DIMPORT|date||是|取込日|
|THB_DCANCEL|date||是|取消日|
|THB_CBIZTRIPSTATUS|varchar||是|出張ステータス|
|THB_CMESSAGE|varchar||是|メッセージ|
