# 検索対象範囲条件定義マスタ(MAST_DATAPERMISSION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MDP_ID|numeric||否|IDカラム|
|MDP_CPERMISSIONID|varchar||否|定義ID|
|MDP_CPERMISSIONNAME|varchar||否|定義名|
|MDP_CBASEFLAG|varchar||否|検索対象範囲定義種別|
|MDP_CEDITFLAG|varchar||否|検索対象範囲定義編集種別 |
|MDP_CPERMISSION|varchar||否|定義権限|
|MDP_CMODIFIERUSERID|varchar||是|最終更新者|
|MDP_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
