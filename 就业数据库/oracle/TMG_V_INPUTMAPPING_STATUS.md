# [設計書もどき]入力データ→集計処理マッピング状況(TMG_V_INPUTMAPPING_STATUS)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MGD_IS_DEFINED|VARCHAR2||是|MAST_GENERIC_DETAILに定義されているか？|
|TMIM_IS_DEFINED|VARCHAR2||是|TMG_MAST_INPUTAMPPINGに定義されているか？|
|MGD_CMASTERCODE|VARCHAR2||是|入力源マスタコード|
|MGD_CGENERICDETAILDESC|NVARCHAR2||是|入力源マスタ名称 [参照元：MAST_GENERIC_DETAIL]|
|TMIM_CCATEGORYID|VARCHAR2||是|集計カテゴリ|
|TMIM_CTOTALTYPEID|VARCHAR2||是|集計タイプ|
