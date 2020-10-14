# [勤怠]入社取消用バックアップ  グループ割付情報(TMG_BAK_GROUP_MEMBER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGRM_DCREATE|date||是|作成日|
|TGRM_CCUSTOMERID|varchar||否|顧客コード                        固定：01|
|TGRM_CCOMPANYID|varchar||否|法人コード|
|TGRM_CEMPLOYEEID|varchar||否|職員番号|
|TGRM_DSTARTDATE|date||否|データ開始日|
|TGRM_DENDDATE|date||否|データ終了日|
|TGRM_CMODIFIERUSERID|varchar||是|更新者|
|TGRM_DMODIFIEDDATE|date||是|更新日|
|TGRM_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TGRM_CSECTIONID|varchar||是|部署コード  MO:MO_CSECTIONID_CK|
|TGRM_CGROUPID|varchar||是|グループコード  グループでなく部署に対する設定の場合、null  TMG_GROUP：TGR_CSECTIONID|
|TGRM_CCHAR01|varchar||是|部局コード(連携時の初期化用部局コード)|
|TGRM_CBASE_SECTIONID|varchar||是|組織.部署コード  MO:MO_CSECTIONID_CK|
|TGRM_CBASE_GROUPID|varchar||是|組織.グループコード  グループでなく部署に対する設定の場合、null  TMG_GROUP：TGR_CSECTIONID|
