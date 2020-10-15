# [勤怠]グループ割付情報                  データ開始日、終了日は親となる異動歴のデータ開始日、終了日と(TMG_GROUP_MEMBER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGRM_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TGRM_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TGRM_CEMPLOYEEID|VARCHAR2||否|職員番号                                                                                      |
|TGRM_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TGRM_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TGRM_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TGRM_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TGRM_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TGRM_CSECTIONID|VARCHAR2||是|部署コード                                                       MO:MO_CSECTIONID_CK           |
|TGRM_CGROUPID|VARCHAR2||是|グループコード                       グループでなく部署に対する設定の場合、null       TMG_GROUP：TGR_CSECTIONID      |
|TGRM_CCHAR01|VARCHAR2||是|部局コード(連携時の初期化用部局コード)|
|TGRM_CBASE_SECTIONID|VARCHAR2||是|組織.部署コード                                                       MO:MO_CSECTIONID_CK           |
|TGRM_CBASE_GROUPID|VARCHAR2||是|組織.グループコード                       グループでなく部署に対する設定の場合、null       TMG_GROUP：TGR_CSECTIONID      |
