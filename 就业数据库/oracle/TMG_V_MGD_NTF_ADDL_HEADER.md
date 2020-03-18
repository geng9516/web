# [勤怠/名称マスタ]追加ヘッダ：申請区分(TMG_V_MGD_NTF_ADDL_HEADER)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_CCUSTOMERID|VARCHAR2||是|顧客コード|
|MGD_CCOMPANYID_CK_FK|VARCHAR2||是|法人コード|
|MGD_DSTART_CK|DATE||否|開始日|
|MGD_DEND|DATE||否|終了日|
|MGD_CLANGUAGE_CK|VARCHAR2||是|言語区分|
|MGD_CCONTENTID|NVARCHAR2||是|コンテンツＩＤ|
|MGD_CNTFTYPE|NVARCHAR2||是|申請区分|
|MGD_CGROUPINGID|NVARCHAR2||是|グルーピングＩＤ|
|MGD_CMESSAGE|NVARCHAR2||是|表示文言|
