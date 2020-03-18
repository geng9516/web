# ＦＲＯＭ＿ＨＲエラーリストテーブル(TMG_ERRORLIST_FROMHR)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TEFH_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TEFH_CCOMPANYID|VARCHAR2||否|法人コード|
|TEFH_CEMPLOYEEID|VARCHAR2||否|社員番号|
|TEFH_CMODIFIERUSERID|VARCHAR2||是|最終更新者|
|TEFH_DMODIFIEDDATE|DATE||是|最終更新日|
|TEFH_CMODIFIERPROGRAMID|VARCHAR2||是|最終更新プログラムID|
|TEFH_CERR_MSG|VARCHAR2||是|エラーメッセージ|
|TEFH_NRETRY_FLG|NUMBER||否|再実行フラグ ０：未実施 １：実施済|
