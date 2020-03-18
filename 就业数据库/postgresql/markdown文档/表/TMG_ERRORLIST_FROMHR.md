# ＦＲＯＭ＿ＨＲエラーリストテーブル(TMG_ERRORLIST_FROMHR)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TEFH_CCUSTOMERID|varchar||否|顧客コード|
|TEFH_CCOMPANYID|varchar||否|法人コード|
|TEFH_CEMPLOYEEID|varchar||否|社員番号|
|TEFH_CMODIFIERUSERID|varchar||是|最終更新者|
|TEFH_DMODIFIEDDATE|date||是|最終更新日|
|TEFH_CMODIFIERPROGRAMID|varchar||是|最終更新プログラムID|
|TEFH_CERR_MSG|varchar||是|エラーメッセージ|
|TEFH_NRETRY_FLG|numeric||否|再実行フラグ ０：未実施 １：実施済|
