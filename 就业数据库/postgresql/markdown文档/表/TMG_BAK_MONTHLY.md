# [勤怠]バックアップ・月別情報(TMG_BAK_MONTHLY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMO_DCREATE|date||是|作成日|
|TMO_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01|
|TMO_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TMO_CEMPLOYEEID|varchar||否|社員番号|
|TMO_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                      固定：1900/01/01|
|TMO_DENDDATE|date||否|ﾃﾞｰﾀ終了日                      固定：2222/12/31|
|TMO_CMODIFIERUSERID|varchar||是|更新者|
|TMO_DMODIFIEDDATE|date||是|更新日|
|TMO_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TMO_DYYYYMM|date||否|該当年月                        YYYY/MM/01|
|TMO_NPAID_BEF_BEGINING_DAYS|numeric||是|付与前：年休月初残日数|
|TMO_NPAID_BEF_BEGINING_HOURS|numeric||是|付与前：年休月初残時間数|
|TMO_NPAID_BEF_USED_DAYS|numeric||是|付与前：年休(終日)取得回数|
|TMO_NPAID_BEF_USED_HALFDAYS|numeric||是|付与前：年休(半休)取得回数|
|TMO_NPAID_BEF_USED_HOURS|numeric||是|付与前：年休(時間)取得時間数|
|TMO_NPAID_CARRYFORWARD|numeric||是|付与後：年休繰越日数|
|TMO_NPAID_ADD|numeric||是|付与後：年休付与日数|
|TMG_NPAID_AJUST|numeric||是|付与後：年休調整付与日数|
|TMO_NPAID_CR_AJUST|numeric||是|付与後：年休調整繰越日数|
|TMO_NPAID_ADD_AJUST_HOURS|numeric||是|付与後：年休調整付与時間|
|TMO_NPAID_CR_AJUST_HOURS|numeric||是|付与後：年休調整繰越時間|
|TMO_NPAID_BEGINING_DAYS|numeric||是|付与後：年休月初残日数/付与前：年休残日数|
|TMO_NPAID_BEGINING_HOURS|numeric||是|付与後：年休月初残時間数/付与前：年休残時間数|
|TMO_NPAID_USED_DAYS|numeric||是|付与後：年休(終日)取得回数|
|TMO_NPAID_USED_HALFDAYS|numeric||是|付与後：年休(半休)取得回数|
|TMO_NPAID_USED_HOURS|numeric||是|付与後：年休(時間)取得時間数|
|TMO_NPAID_REST_DAYS|numeric||是|付与後：年休残日数|
|TMO_NPAID_REST_HOURS|numeric||是|付与後：年休残時間数|
|TMO_NPAID_CR_HOURS|numeric||是|付与後：年休繰越時間数|
|TMO_NPAID_BEF_REST_DAYS|numeric||是|付与前：付与前日残日数|
|TMO_NPAID_BEF_REST_HOURS|numeric||是|付与前：付与前日残時間|
|TMO_NLOSE_DAYS|numeric||是|喪失日数|
|TMO_NLOSE_HOURS|numeric||是|喪失時間数|
