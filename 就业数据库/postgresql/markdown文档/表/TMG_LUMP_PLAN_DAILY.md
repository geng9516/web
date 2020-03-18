# 一括予定作成適用履歴(TMG_LUMP_PLAN_DAILY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TLP_NID|numeric||否|ID|
|TLP_CCUSTOMERID|varchar||否|顧客コード|
|TLP_CCOMPANYID|varchar||否|法人コード|
|TLP_CSECTIONID|varchar||否|部署コード|
|TLP_CGROUPID|varchar||是|グループコード|
|TLP_DSTARTDATE|date||否|開始日|
|TLP_DENDDATE|date||否|終了日|
|TLP_CMODIFIERUSERID|varchar||是|更新者|
|TLP_DMODIFIEDDATE|date||是|更新日|
|TLP_CMODIFIERPROGRAMID|varchar||是|更新ﾌﾟﾛｸﾞﾗﾑID|
|TLP_NYYYY|numeric||否|年|
|TLP_DYYYYMM|date||否|年月|
|TLP_DYYYYMMDD|date||否|年月日|
|TLP_CWORKINGID|varchar||是|就業区分|
|TLP_CBUSINESSTRIPID|varchar||是|出張区分|
|TLP_NOPEN|numeric||是|始業|
|TLP_NCLOSE|numeric||是|終業|
|TLP_NRESTOPEN|varchar||是|休憩開始時刻|
|TLP_NRESTCLOSE|varchar||是|休憩終了時刻|
|TLP_CCOMMENT|varchar||是|コメント|
