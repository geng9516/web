# 検索対象範囲条件定義マスタ(MAST_DATAPERMISSION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MDP_ID|NUMBER||否|IDカラム|
|MDP_CPERMISSIONID|VARCHAR2||否|定義ID|
|MDP_CPERMISSIONNAME|NVARCHAR2||否|定義名|
|MDP_CBASEFLAG|VARCHAR2||否|検索対象範囲定義種別|
|MDP_CEDITFLAG|VARCHAR2||否|検索対象範囲定義編集種別 |
|MDP_CPERMISSION|VARCHAR2||否|定義権限|
|MDP_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|MDP_DMODIFIEDDATE|DATE||是|最終更新日|
|VERSIONNO|NUMBER||否|バージョンNo|
