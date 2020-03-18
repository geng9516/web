# クロス集計検索設定保存データ（集計条件定義）(HIST_CROSSSEARCH_SELECT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|HICS_ID|numeric||否|IDカラム|
|HICS_NSETTINGID|numeric||否|設定ID|
|HICS_CXAXIS_COLUMN1|varchar||是|縦軸一次元カラムID|
|HICS_CXAXIS_DATETYPE1|varchar||是|縦軸一次元日付タイプ|
|HICS_NXAXIS_PITCH1|numeric||是|縦軸一次元ピッチ|
|HICS_CXAXIS_MIN1|varchar||是|縦軸一次元最小値|
|HICS_CXAXIS_MAX1|varchar||是|縦軸一次元最大値|
|HICS_CXAXIS_SUM|varchar||是|縦軸小計フラグ|
|HICS_CXAXIS_COLUMN2|varchar||是|縦軸二次元カラムID|
|HICS_CXAXIS_DATETYPE2|varchar||是|縦軸二次元日付タイプ|
|HICS_NXAXIS_PITCH2|numeric||是|縦軸二次元ピッチ|
|HICS_CXAXIS_MIN2|varchar||是|縦軸二次元最小値|
|HICS_CXAXIS_MAX2|varchar||是|縦軸二次元最大値|
|HICS_CYAXIS_COLUMN1|varchar||是|横軸一次元カラムID|
|HICS_CYAXIS_DATETYPE1|varchar||是|横軸一次元日付タイプ|
|HICS_NYAXIS_PITCH1|numeric||是|横軸一次元ピッチ|
|HICS_CYAXIS_MIN1|varchar||是|横軸一次元最小値|
|HICS_CYAXIS_MAX1|varchar||是|横軸一次元最大値|
|HICS_CYAXIS_SUM|varchar||是|横軸小計フラグ|
|HICS_CYAXIS_COLUMN2|varchar||是|横軸二次元カラムID|
|HICS_CYAXIS_DATETYPE2|varchar||是|横軸二次元日付タイプ|
|HICS_NYAXIS_PITCH2|numeric||是|横軸二次元ピッチ|
|HICS_CYAXIS_MIN2|varchar||是|横軸二次元最小値|
|HICS_CYAXIS_MAX2|varchar||是|横軸二次元最大値|
|HICS_CSUM_COLUMN|varchar||是|集計項目カラムID|
|HICS_CCOUNT|varchar||是|人数集計フラグ|
|HICS_CAVG|varchar||是|平均フラグ|
|HICS_CMODIFIERUSERID|varchar||是|最終更新者|
|HICS_DMODIFIEDDATE|date||是|最終更新日|
|VERSIONNO|numeric||否|バージョンNo|
