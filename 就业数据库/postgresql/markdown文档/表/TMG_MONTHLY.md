# [勤怠]月別情報                      2007/2/23 年休調整日数のカラムを追加。年休(時間)(TMG_MONTHLY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMO_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMO_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMO_CEMPLOYEEID|varchar||否|職員番号                                                                                      |
|TMO_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMO_DENDDATE|date||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMO_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMO_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMO_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMO_NYYYY|numeric||否|該当年                           YYYY                                                        |
|TMO_DYYYYMM|date||否|該当年月                          YYYY/MM/01                                                  |
|TMO_CSTATUSFLG|varchar||是|ステータスフラグ                                                    MGD:TMG_DATASTATUS            |
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
|TMO_NSICHNESS_USED_DAYS|numeric||是|病休取得日数                                                                                    |
|TMO_NSICHNESS_USED_HOURS|numeric||是|病休取得時間数                       分単位で格納                                                      |
|TMO_NSPECIAL_USED_DAYS|numeric||是|特休取得日数                                                                                    |
|TMO_NSPECIAL_USED_HOURS|numeric||是|特休取得時間数                       分単位で格納                                                      |
|TMO_NCALC001|numeric||是|勤怠月別項目001                                                                                 |
|TMO_NCALC002|numeric||是|勤怠月別項目002                                                                                 |
|TMO_NCALC003|numeric||是|勤怠月別項目003                                                                                 |
|TMO_NCALC004|numeric||是|勤怠月別項目004                                                                                 |
|TMO_NCALC005|numeric||是|勤怠月別項目005                                                                                 |
|TMO_NCALC006|numeric||是|勤怠月別項目006                                                                                 |
|TMO_NCALC007|numeric||是|勤怠月別項目007                                                                                 |
|TMO_NCALC008|numeric||是|勤怠月別項目008                                                                                 |
|TMO_NCALC009|numeric||是|勤怠月別項目009                                                                                 |
|TMO_NCALC010|numeric||是|勤怠月別項目010                                                                                 |
|TMO_NCALC011|numeric||是|勤怠月別項目011                                                                                 |
|TMO_NCALC012|numeric||是|勤怠月別項目012                                                                                 |
|TMO_NCALC013|numeric||是|勤怠月別項目013                                                                                 |
|TMO_NCALC014|numeric||是|勤怠月別項目014                                                                                 |
|TMO_NCALC015|numeric||是|勤怠月別項目015                                                                                 |
|TMO_NCALC016|numeric||是|勤怠月別項目016                                                                                 |
|TMO_NCALC017|numeric||是|勤怠月別項目017                                                                                 |
|TMO_NCALC018|numeric||是|勤怠月別項目018                                                                                 |
|TMO_NCALC019|numeric||是|勤怠月別項目019                                                                                 |
|TMO_NCALC020|numeric||是|勤怠月別項目020                                                                                 |
|TMO_NCALC021|numeric||是|勤怠月別項目021                                                                                 |
|TMO_NCALC022|numeric||是|勤怠月別項目022                                                                                 |
|TMO_NCALC023|numeric||是|勤怠月別項目023                                                                                 |
|TMO_NCALC024|numeric||是|勤怠月別項目024                                                                                 |
|TMO_NCALC025|numeric||是|勤怠月別項目025                                                                                 |
|TMO_NCALC026|numeric||是|勤怠月別項目026                                                                                 |
|TMO_NCALC027|numeric||是|勤怠月別項目027                                                                                 |
|TMO_NCALC028|numeric||是|勤怠月別項目028                                                                                 |
|TMO_NCALC029|numeric||是|勤怠月別項目029                                                                                 |
|TMO_NCALC030|numeric||是|勤怠月別項目030                                                                                 |
|TMO_NCALC031|numeric||是|勤怠月別項目031                                                                                 |
|TMO_NCALC032|numeric||是|勤怠月別項目032                                                                                 |
|TMO_NCALC033|numeric||是|勤怠月別項目033                                                                                 |
|TMO_NCALC034|numeric||是|勤怠月別項目034                                                                                 |
|TMO_NCALC035|numeric||是|勤怠月別項目035                                                                                 |
|TMO_NCALC036|numeric||是|勤怠月別項目036                                                                                 |
|TMO_NCALC037|numeric||是|勤怠月別項目037                                                                                 |
|TMO_NCALC038|numeric||是|勤怠月別項目038                                                                                 |
|TMO_NCALC039|numeric||是|勤怠月別項目039                                                                                 |
|TMO_NCALC040|numeric||是|勤怠月別項目040                                                                                 |
|TMO_NCALC041|numeric||是|勤怠月別項目041                                                                                 |
|TMO_NCALC042|numeric||是|勤怠月別項目042                                                                                 |
|TMO_NCALC043|numeric||是|勤怠月別項目043                                                                                 |
|TMO_NCALC044|numeric||是|勤怠月別項目044                                                                                 |
|TMO_NCALC045|numeric||是|勤怠月別項目045                                                                                 |
|TMO_NCALC046|numeric||是|勤怠月別項目046                                                                                 |
|TMO_NCALC047|numeric||是|勤怠月別項目047                                                                                 |
|TMO_NCALC048|numeric||是|勤怠月別項目048                                                                                 |
|TMO_NCALC049|numeric||是|勤怠月別項目049                                                                                 |
|TMO_NCALC050|numeric||是|勤怠月別項目050                                                                                 |
|TMO_NCALC051|numeric||是|勤怠月別項目051                                                                                 |
|TMO_NCALC052|numeric||是|勤怠月別項目052                                                                                 |
|TMO_NCALC053|numeric||是|勤怠月別項目053                                                                                 |
|TMO_NCALC054|numeric||是|勤怠月別項目054                                                                                 |
|TMO_NCALC055|numeric||是|勤怠月別項目055                                                                                 |
|TMO_NCALC056|numeric||是|勤怠月別項目056                                                                                 |
|TMO_NCALC057|numeric||是|勤怠月別項目057                                                                                 |
|TMO_NCALC058|numeric||是|勤怠月別項目058                                                                                 |
|TMO_NCALC059|numeric||是|勤怠月別項目059                                                                                 |
|TMO_NCALC060|numeric||是|勤怠月別項目060                                                                                 |
|TMO_NCALC061|numeric||是|勤怠月別項目061                                                                                 |
|TMO_NCALC062|numeric||是|勤怠月別項目062                                                                                 |
|TMO_NCALC063|numeric||是|勤怠月別項目063                                                                                 |
|TMO_NCALC064|numeric||是|勤怠月別項目064                                                                                 |
|TMO_NCALC065|numeric||是|勤怠月別項目065                                                                                 |
|TMO_NCALC066|numeric||是|勤怠月別項目066                                                                                 |
|TMO_NCALC067|numeric||是|勤怠月別項目067                                                                                 |
|TMO_NCALC068|numeric||是|勤怠月別項目068                                                                                 |
|TMO_NCALC069|numeric||是|勤怠月別項目069                                                                                 |
|TMO_NCALC070|numeric||是|勤怠月別項目070                                                                                 |
|TMO_NCALC071|numeric||是|勤怠月別項目071                                                                                 |
|TMO_NCALC072|numeric||是|勤怠月別項目072                                                                                 |
|TMO_NCALC073|numeric||是|勤怠月別項目073                                                                                 |
|TMO_NCALC074|numeric||是|勤怠月別項目074                                                                                 |
|TMO_NCALC075|numeric||是|勤怠月別項目075                                                                                 |
|TMO_NCALC076|numeric||是|勤怠月別項目076                                                                                 |
|TMO_NCALC077|numeric||是|勤怠月別項目077                                                                                 |
|TMO_NCALC078|numeric||是|勤怠月別項目078                                                                                 |
|TMO_NCALC079|numeric||是|勤怠月別項目079                                                                                 |
|TMO_NCALC080|numeric||是|勤怠月別項目080                                                                                 |
|TMO_NCALC081|numeric||是|勤怠月別項目081                                                                                 |
|TMO_NCALC082|numeric||是|勤怠月別項目082                                                                                 |
|TMO_NCALC083|numeric||是|勤怠月別項目083                                                                                 |
|TMO_NCALC084|numeric||是|勤怠月別項目084                                                                                 |
|TMO_NCALC085|numeric||是|勤怠月別項目085                                                                                 |
|TMO_NCALC086|numeric||是|勤怠月別項目086                                                                                 |
|TMO_NCALC087|numeric||是|勤怠月別項目087                                                                                 |
|TMO_NCALC088|numeric||是|勤怠月別項目088                                                                                 |
|TMO_NCALC089|numeric||是|勤怠月別項目089                                                                                 |
|TMO_NCALC090|numeric||是|勤怠月別項目090                                                                                 |
|TMO_NCALC091|numeric||是|勤怠月別項目091                                                                                 |
|TMO_NCALC092|numeric||是|勤怠月別項目092                                                                                 |
|TMO_NCALC093|numeric||是|勤怠月別項目093                                                                                 |
|TMO_NCALC094|numeric||是|勤怠月別項目094                                                                                 |
|TMO_NCALC095|numeric||是|勤怠月別項目095                                                                                 |
|TMO_NCALC096|numeric||是|勤怠月別項目096                                                                                 |
|TMO_NCALC097|numeric||是|勤怠月別項目097                                                                                 |
|TMO_NCALC098|numeric||是|勤怠月別項目098                                                                                 |
|TMO_NCALC099|numeric||是|勤怠月別項目099                                                                                 |
|TMO_NCALC100|numeric||是|勤怠月別項目100                                                                                 |
|TMO_NMONTH_CHK_FLG|numeric||是|同月内振替フラグ  1:同月内で振替  null:同週内で振替       |
|TMO_NCALC101|numeric||是|勤怠月別項目101|
|TMO_NCALC102|numeric||是|勤怠月別項目102|
|TMO_NCALC103|numeric||是|勤怠月別項目103|
|TMO_NCALC104|numeric||是|勤怠月別項目104|
|TMO_NCALC105|numeric||是|勤怠月別項目105|
|TMO_NCALC106|numeric||是|勤怠月別項目106|
|TMO_NCALC107|numeric||是|勤怠月別項目107|
|TMO_NCALC108|numeric||是|勤怠月別項目108|
|TMO_NCALC109|numeric||是|勤怠月別項目109|
|TMO_NCALC110|numeric||是|勤怠月別項目110|
|TMO_NCALC111|numeric||是|勤怠月別項目111|
|TMO_NCALC112|numeric||是|勤怠月別項目112|
|TMO_NCALC113|numeric||是|勤怠月別項目113|
|TMO_NCALC114|numeric||是|勤怠月別項目114|
|TMO_NCALC115|numeric||是|勤怠月別項目115|
|TMO_NCALC116|numeric||是|勤怠月別項目116|
|TMO_NCALC117|numeric||是|勤怠月別項目117|
|TMO_NCALC118|numeric||是|勤怠月別項目118|
|TMO_NCALC119|numeric||是|勤怠月別項目119|
|TMO_NCALC120|numeric||是|勤怠月別項目120|
|TMO_NCALC121|numeric||是|勤怠月別項目121|
|TMO_NCALC122|numeric||是|勤怠月別項目122|
|TMO_NCALC123|numeric||是|勤怠月別項目123|
|TMO_NCALC124|numeric||是|勤怠月別項目124|
|TMO_NCALC125|numeric||是|勤怠月別項目125|
|TMO_NCALC126|numeric||是|勤怠月別項目126|
|TMO_NCALC127|numeric||是|勤怠月別項目127|
|TMO_NCALC128|numeric||是|勤怠月別項目128|
|TMO_NCALC129|numeric||是|勤怠月別項目129|
|TMO_NCALC130|numeric||是|勤怠月別項目130|
|TMO_NCALC131|numeric||是|勤怠月別項目131|
|TMO_NCALC132|numeric||是|勤怠月別項目132|
|TMO_NCALC133|numeric||是|勤怠月別項目133|
|TMO_NCALC134|numeric||是|勤怠月別項目134|
|TMO_NCALC135|numeric||是|勤怠月別項目135|
|TMO_NCALC136|numeric||是|勤怠月別項目136|
|TMO_NCALC137|numeric||是|勤怠月別項目137|
|TMO_NCALC138|numeric||是|勤怠月別項目138|
|TMO_NCALC139|numeric||是|勤怠月別項目139|
|TMO_NCALC140|numeric||是|勤怠月別項目140|
|TMO_NCALC141|numeric||是|勤怠月別項目141|
|TMO_NCALC142|numeric||是|勤怠月別項目142|
|TMO_NCALC143|numeric||是|勤怠月別項目143|
|TMO_NCALC144|numeric||是|勤怠月別項目144|
|TMO_NCALC145|numeric||是|勤怠月別項目145|
|TMO_NCALC146|numeric||是|勤怠月別項目146|
|TMO_NCALC147|numeric||是|勤怠月別項目147|
|TMO_NCALC148|numeric||是|勤怠月別項目148|
|TMO_NCALC149|numeric||是|勤怠月別項目149|
|TMO_NCALC150|numeric||是|勤怠月別項目150|
|TMO_NCALC151|numeric||是|勤怠月別項目151|
|TMO_NCALC152|numeric||是|勤怠月別項目152|
|TMO_NCALC153|numeric||是|勤怠月別項目153|
|TMO_NCALC154|numeric||是|勤怠月別項目154|
|TMO_NCALC155|numeric||是|勤怠月別項目155|
|TMO_NCALC156|numeric||是|勤怠月別項目156|
|TMO_NCALC157|numeric||是|勤怠月別項目157|
|TMO_NCALC158|numeric||是|勤怠月別項目158|
|TMO_NCALC159|numeric||是|勤怠月別項目159|
|TMO_NCALC160|numeric||是|勤怠月別項目160|
|TMO_NCALC161|numeric||是|勤怠月別項目161|
|TMO_NCALC162|numeric||是|勤怠月別項目162|
|TMO_NCALC163|numeric||是|勤怠月別項目163|
|TMO_NCALC164|numeric||是|勤怠月別項目164|
|TMO_NCALC165|numeric||是|勤怠月別項目165|
|TMO_NCALC166|numeric||是|勤怠月別項目166|
|TMO_NCALC167|numeric||是|勤怠月別項目167|
|TMO_NCALC168|numeric||是|勤怠月別項目168|
|TMO_NCALC169|numeric||是|勤怠月別項目169|
|TMO_NCALC170|numeric||是|勤怠月別項目170|
|TMO_NCALC171|numeric||是|勤怠月別項目171|
|TMO_NCALC172|numeric||是|勤怠月別項目172|
|TMO_NCALC173|numeric||是|勤怠月別項目173|
|TMO_NCALC174|numeric||是|勤怠月別項目174|
|TMO_NCALC175|numeric||是|勤怠月別項目175|
|TMO_NCALC176|numeric||是|勤怠月別項目176|
|TMO_NCALC177|numeric||是|勤怠月別項目177|
|TMO_NCALC178|numeric||是|勤怠月別項目178|
|TMO_NCALC179|numeric||是|勤怠月別項目179|
|TMO_NCALC180|numeric||是|勤怠月別項目180|
|TMO_NCALC181|numeric||是|勤怠月別項目181|
|TMO_NCALC182|numeric||是|勤怠月別項目182|
|TMO_NCALC183|numeric||是|勤怠月別項目183|
|TMO_NCALC184|numeric||是|勤怠月別項目184|
|TMO_NCALC185|numeric||是|勤怠月別項目185|
|TMO_NCALC186|numeric||是|勤怠月別項目186|
|TMO_NCALC187|numeric||是|勤怠月別項目187|
|TMO_NCALC188|numeric||是|勤怠月別項目188|
|TMO_NCALC189|numeric||是|勤怠月別項目189|
|TMO_NCALC190|numeric||是|勤怠月別項目190|
|TMO_NCALC191|numeric||是|勤怠月別項目191|
|TMO_NCALC192|numeric||是|勤怠月別項目192|
|TMO_NCALC193|numeric||是|勤怠月別項目193|
|TMO_NCALC194|numeric||是|勤怠月別項目194|
|TMO_NCALC195|numeric||是|勤怠月別項目195|
|TMO_NCALC196|numeric||是|勤怠月別項目196|
|TMO_NCALC197|numeric||是|勤怠月別項目197|
|TMO_NCALC198|numeric||是|勤怠月別項目198|
|TMO_NCALC199|numeric||是|勤怠月別項目199|
|TMO_NCALC200|numeric||是|勤怠月別項目200|
|TMO_CSTS_WORK|varchar||是|裁量労働：勤務状態|
|TMO_CSTS_HEALTH|varchar||是|裁量労働：健康状態|
|TMO_CSTS_PLAN|varchar||是|予定確定|
|TMO_NPAID_CR_HOURS|numeric||是|付与後：年休繰越時間数|
|TMO_NPAID_BEF_REST_DAYS|numeric||是|付与前：付与前日残日数|
|TMO_NPAID_BEF_REST_HOURS|numeric||是|付与前：付与前日残時間|
|TMO_NLOSE_DAYS|numeric||是|喪失日数|
|TMO_NLOSE_HOURS|numeric||是|喪失時間数|
