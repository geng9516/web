# 名称マスタ(MAST_GENERIC)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MG_ID|numeric||否|IDカラム|
|MG_CCUSTOMERID_CK_FK|varchar||是|顧客コード|
|MG_CCOMPANYID_CK_FK|varchar||是|法人コード|
|MG_CGENERICGROUPID_CK|varchar||否|名称マスタコード|
|MG_CLANGUAGE_CK|varchar||是|言語区分|
|MG_CGENERICGROUPDESC|varchar||否|名称マスタ名称|
|MG_CIFHISTORICAL|varchar||是|履歴作成区分|
|MG_CIFEDITABLE|varchar||是|編集可否区分|
|MG_CIFCOMPANYTYPE|varchar||是|法人個別区分|
|MG_DCREATEDDATE|date||是|作成日|
|MG_CDELFLAG|varchar||是|廃止フラグ|
|MG_DDELDATE|date||是|廃止日|
|MG_CMODIFIERUSERID|varchar||是|最終更新者|
|MG_DMODIFIEDDATE|date||是|最終更新日|
|MG_CCATEGORYID|varchar||是|カテゴリ区分|
|VERSIONNO|numeric||否|バージョンNo|
