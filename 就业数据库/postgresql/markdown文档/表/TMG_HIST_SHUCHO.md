# 取込出張データ(TMG_HIST_SHUCHO)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|THS_ID|numeric||否|IDカラム シーケンスTMG_HIST_SHUCHO_SEQを作る必要があります。 |
|THS_CCUSTOMERID|varchar||否|顧客コード  |
|THS_CCOMPANYID|varchar||否|法人コード  |
|THS_DSTART|date||否|データ開始日  |
|THS_DEND|date||否|データ終了日  |
|THS_CEMPLOYEEID|varchar||否|個人番号  |
|THS_CKANJINAME|varchar||是|氏名  |
|THS_CSECTION_NAME|varchar||是|所属  |
|THS_CSHUCHO_ID|varchar||是|処理番号  |
|THS_DTRIPSTART|date||是|旅行開始日  |
|THS_DTRIPEND|date||是|旅行終了日  |
|THS_CYOUMU1|varchar||是|用務先１  |
|THS_CYOUMU2|varchar||是|用務先２  |
|THS_CYOUMU3|varchar||是|用務先３  |
|THS_CKAIGAI_FLAG|varchar||是|海外出張 KAIGAI|0→国内出張、KAIGAI|1→海外出張 |
|THS_DTORIKOMI|date||是|取込日時  |
|THS_DSHORI|date||是|処理日時  |
|THS_CSHORI_FLAG|varchar||是|処理フラグ SHUCHOFLAG|0→未、SHUCHOFLAG|1→済、SHUCHOFLAG|9→無効 |
|THS_DCANCEL|date||是|取消日時  |
|THS_CCANCEL_FLAG|varchar||是|取消フラグ SHUCHOFLAG|0→未、SHUCHOFLAG|1→済 |
|THS_CMESSAGE|varchar||是|メッセージ  |
|VERSIONNO|numeric||否|バージョンNO V4互換用 |
