# グループ定義マスタ(MAST_GROUP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MG_ID|numeric||否|ＩＤカラム|
|MG_CCUSTOMERID|varchar||是|顧客コード|
|MG_CSYSTEMID_CK_FK|varchar||是|システムコード|
|MG_CGROUPID_PK|varchar||是|グループID|
|MG_CLANGUAGE|varchar||是|言語区分|
|MG_DSTARTDATE|date||是|データ開始日|
|MG_DENDDATE|date||否|データ終了日|
|MG_CGROUPDESCRIPTION|varchar||是|グループ名称|
|MG_CGROUPDESCRIPTIONJA|varchar||是|グループ名称（日本語）|
|MG_CGROUPDESCRIPTIONEN|varchar||是|グループ名称（英語）|
|MG_CGROUPDESCRIPTIONCH|varchar||是|グループ名称（中国語）|
|MG_CGROUPDESCRIPTION01|varchar||是|グループ名称（予備01）|
|MG_CGROUPDESCRIPTION02|varchar||是|グループ名称（予備02）|
|MG_CCOMPANYID|varchar||是|法人コード（未使用）|
|MG_NPARTINENTNUMBER|numeric||是|適正数|
|MG_NWEIGHTAGE|numeric||是|優先順|
|MG_CTEXT|varchar||是|備考欄|
|MG_CMODIFIERUSERID|varchar||是|最終更新者|
|MG_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
