# [勤怠]エラーチェック用グループ割付情報          データ開始日、終了日は親となる異動歴のデータ開始日、終了日と(TMG_GROUP_MEMBER_CHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGRM_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TGRM_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TGRM_CEMPLOYEEID|varchar||否|社員番号                                                                                      |
|TGRM_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TGRM_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TGRM_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TGRM_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TGRM_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TGRM_CSECTIONID|varchar||是|部署コード                                                       MO:MO_CSECTIONID_CK           |
|TGRM_CGROUPID|varchar||是|グループコード                       グループでなく部署に対する設定の場合、null       TMG_GROUP：TGR_CSECTIONID      |
|TGRM_CCHAR01|varchar||是|null|
|TGRM_CBASE_SECTIONID|varchar||是|組織.部署コード                                                       MO:MO_CSECTIONID_CK           |
|TGRM_CBASE_GROUPID|varchar||是|組織.グループコード                       グループでなく部署に対する設定の場合、null       TMG_GROUP：TGR_CSECTIONID      |
