# [勤怠]月別情報                      2007/2/23 年休調整日数のカラムを追加。年休(時間)(TMG_MONTHLY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMO_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMO_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMO_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                      |
|TMO_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                       固定：1900/01/01                                               |
|TMO_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                       固定：2222/12/31                                               |
|TMO_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMO_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMO_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMO_NYYYY|NUMBER||否|該当年                           YYYY                                                        |
|TMO_DYYYYMM|DATE||否|該当年月                          YYYY/MM/01                                                  |
|TMO_CSTATUSFLG|VARCHAR2||是|ステータスフラグ                                                    MGD:TMG_DATASTATUS            |
|TMO_NPAID_BEF_BEGINING_DAYS|NUMBER||是|付与前：年休月初残日数|
|TMO_NPAID_BEF_BEGINING_HOURS|NUMBER||是|付与前：年休月初残時間数|
|TMO_NPAID_BEF_USED_DAYS|NUMBER||是|付与前：年休(終日)取得回数|
|TMO_NPAID_BEF_USED_HALFDAYS|NUMBER||是|付与前：年休(半休)取得回数|
|TMO_NPAID_BEF_USED_HOURS|NUMBER||是|付与前：年休(時間)取得時間数|
|TMO_NPAID_CARRYFORWARD|NUMBER||是|付与後：年休繰越日数|
|TMO_NPAID_ADD|NUMBER||是|付与後：年休付与日数|
|TMG_NPAID_AJUST|NUMBER||是|付与後：年休調整付与日数|
|TMO_NPAID_CR_AJUST|NUMBER||是|付与後：年休調整繰越日数|
|TMO_NPAID_ADD_AJUST_HOURS|NUMBER||是|付与後：年休調整付与時間|
|TMO_NPAID_CR_AJUST_HOURS|NUMBER||是|付与後：年休調整繰越時間|
|TMO_NPAID_BEGINING_DAYS|NUMBER||是|付与後：年休月初残日数/付与前：年休残日数|
|TMO_NPAID_BEGINING_HOURS|NUMBER||是|付与後：年休月初残時間数/付与前：年休残時間数|
|TMO_NPAID_USED_DAYS|NUMBER||是|付与後：年休(終日)取得回数|
|TMO_NPAID_USED_HALFDAYS|NUMBER||是|付与後：年休(半休)取得回数|
|TMO_NPAID_USED_HOURS|NUMBER||是|付与後：年休(時間)取得時間数|
|TMO_NPAID_REST_DAYS|NUMBER||是|付与後：年休残日数|
|TMO_NPAID_REST_HOURS|NUMBER||是|付与後：年休残時間数|
|TMO_NSICHNESS_USED_DAYS|NUMBER||是|病休取得日数                                                                                    |
|TMO_NSICHNESS_USED_HOURS|NUMBER||是|病休取得時間数                       分単位で格納                                                      |
|TMO_NSPECIAL_USED_DAYS|NUMBER||是|特休取得日数                                                                                    |
|TMO_NSPECIAL_USED_HOURS|NUMBER||是|特休取得時間数                       分単位で格納                                                      |
|TMO_NCALC001|NUMBER||是|勤怠月別項目001                                                                                 |
|TMO_NCALC002|NUMBER||是|勤怠月別項目002                                                                                 |
|TMO_NCALC003|NUMBER||是|勤怠月別項目003                                                                                 |
|TMO_NCALC004|NUMBER||是|勤怠月別項目004                                                                                 |
|TMO_NCALC005|NUMBER||是|勤怠月別項目005                                                                                 |
|TMO_NCALC006|NUMBER||是|勤怠月別項目006                                                                                 |
|TMO_NCALC007|NUMBER||是|勤怠月別項目007                                                                                 |
|TMO_NCALC008|NUMBER||是|勤怠月別項目008                                                                                 |
|TMO_NCALC009|NUMBER||是|勤怠月別項目009                                                                                 |
|TMO_NCALC010|NUMBER||是|勤怠月別項目010                                                                                 |
|TMO_NCALC011|NUMBER||是|勤怠月別項目011                                                                                 |
|TMO_NCALC012|NUMBER||是|勤怠月別項目012                                                                                 |
|TMO_NCALC013|NUMBER||是|勤怠月別項目013                                                                                 |
|TMO_NCALC014|NUMBER||是|勤怠月別項目014                                                                                 |
|TMO_NCALC015|NUMBER||是|勤怠月別項目015                                                                                 |
|TMO_NCALC016|NUMBER||是|勤怠月別項目016                                                                                 |
|TMO_NCALC017|NUMBER||是|勤怠月別項目017                                                                                 |
|TMO_NCALC018|NUMBER||是|勤怠月別項目018                                                                                 |
|TMO_NCALC019|NUMBER||是|勤怠月別項目019                                                                                 |
|TMO_NCALC020|NUMBER||是|勤怠月別項目020                                                                                 |
|TMO_NCALC021|NUMBER||是|勤怠月別項目021                                                                                 |
|TMO_NCALC022|NUMBER||是|勤怠月別項目022                                                                                 |
|TMO_NCALC023|NUMBER||是|勤怠月別項目023                                                                                 |
|TMO_NCALC024|NUMBER||是|勤怠月別項目024                                                                                 |
|TMO_NCALC025|NUMBER||是|勤怠月別項目025                                                                                 |
|TMO_NCALC026|NUMBER||是|勤怠月別項目026                                                                                 |
|TMO_NCALC027|NUMBER||是|勤怠月別項目027                                                                                 |
|TMO_NCALC028|NUMBER||是|勤怠月別項目028                                                                                 |
|TMO_NCALC029|NUMBER||是|勤怠月別項目029                                                                                 |
|TMO_NCALC030|NUMBER||是|勤怠月別項目030                                                                                 |
|TMO_NCALC031|NUMBER||是|勤怠月別項目031                                                                                 |
|TMO_NCALC032|NUMBER||是|勤怠月別項目032                                                                                 |
|TMO_NCALC033|NUMBER||是|勤怠月別項目033                                                                                 |
|TMO_NCALC034|NUMBER||是|勤怠月別項目034                                                                                 |
|TMO_NCALC035|NUMBER||是|勤怠月別項目035                                                                                 |
|TMO_NCALC036|NUMBER||是|勤怠月別項目036                                                                                 |
|TMO_NCALC037|NUMBER||是|勤怠月別項目037                                                                                 |
|TMO_NCALC038|NUMBER||是|勤怠月別項目038                                                                                 |
|TMO_NCALC039|NUMBER||是|勤怠月別項目039                                                                                 |
|TMO_NCALC040|NUMBER||是|勤怠月別項目040                                                                                 |
|TMO_NCALC041|NUMBER||是|勤怠月別項目041                                                                                 |
|TMO_NCALC042|NUMBER||是|勤怠月別項目042                                                                                 |
|TMO_NCALC043|NUMBER||是|勤怠月別項目043                                                                                 |
|TMO_NCALC044|NUMBER||是|勤怠月別項目044                                                                                 |
|TMO_NCALC045|NUMBER||是|勤怠月別項目045                                                                                 |
|TMO_NCALC046|NUMBER||是|勤怠月別項目046                                                                                 |
|TMO_NCALC047|NUMBER||是|勤怠月別項目047                                                                                 |
|TMO_NCALC048|NUMBER||是|勤怠月別項目048                                                                                 |
|TMO_NCALC049|NUMBER||是|勤怠月別項目049                                                                                 |
|TMO_NCALC050|NUMBER||是|勤怠月別項目050                                                                                 |
|TMO_NCALC051|NUMBER||是|勤怠月別項目051                                                                                 |
|TMO_NCALC052|NUMBER||是|勤怠月別項目052                                                                                 |
|TMO_NCALC053|NUMBER||是|勤怠月別項目053                                                                                 |
|TMO_NCALC054|NUMBER||是|勤怠月別項目054                                                                                 |
|TMO_NCALC055|NUMBER||是|勤怠月別項目055                                                                                 |
|TMO_NCALC056|NUMBER||是|勤怠月別項目056                                                                                 |
|TMO_NCALC057|NUMBER||是|勤怠月別項目057                                                                                 |
|TMO_NCALC058|NUMBER||是|勤怠月別項目058                                                                                 |
|TMO_NCALC059|NUMBER||是|勤怠月別項目059                                                                                 |
|TMO_NCALC060|NUMBER||是|勤怠月別項目060                                                                                 |
|TMO_NCALC061|NUMBER||是|勤怠月別項目061                                                                                 |
|TMO_NCALC062|NUMBER||是|勤怠月別項目062                                                                                 |
|TMO_NCALC063|NUMBER||是|勤怠月別項目063                                                                                 |
|TMO_NCALC064|NUMBER||是|勤怠月別項目064                                                                                 |
|TMO_NCALC065|NUMBER||是|勤怠月別項目065                                                                                 |
|TMO_NCALC066|NUMBER||是|勤怠月別項目066                                                                                 |
|TMO_NCALC067|NUMBER||是|勤怠月別項目067                                                                                 |
|TMO_NCALC068|NUMBER||是|勤怠月別項目068                                                                                 |
|TMO_NCALC069|NUMBER||是|勤怠月別項目069                                                                                 |
|TMO_NCALC070|NUMBER||是|勤怠月別項目070                                                                                 |
|TMO_NCALC071|NUMBER||是|勤怠月別項目071                                                                                 |
|TMO_NCALC072|NUMBER||是|勤怠月別項目072                                                                                 |
|TMO_NCALC073|NUMBER||是|勤怠月別項目073                                                                                 |
|TMO_NCALC074|NUMBER||是|勤怠月別項目074                                                                                 |
|TMO_NCALC075|NUMBER||是|勤怠月別項目075                                                                                 |
|TMO_NCALC076|NUMBER||是|勤怠月別項目076                                                                                 |
|TMO_NCALC077|NUMBER||是|勤怠月別項目077                                                                                 |
|TMO_NCALC078|NUMBER||是|勤怠月別項目078                                                                                 |
|TMO_NCALC079|NUMBER||是|勤怠月別項目079                                                                                 |
|TMO_NCALC080|NUMBER||是|勤怠月別項目080                                                                                 |
|TMO_NCALC081|NUMBER||是|勤怠月別項目081                                                                                 |
|TMO_NCALC082|NUMBER||是|勤怠月別項目082                                                                                 |
|TMO_NCALC083|NUMBER||是|勤怠月別項目083                                                                                 |
|TMO_NCALC084|NUMBER||是|勤怠月別項目084                                                                                 |
|TMO_NCALC085|NUMBER||是|勤怠月別項目085                                                                                 |
|TMO_NCALC086|NUMBER||是|勤怠月別項目086                                                                                 |
|TMO_NCALC087|NUMBER||是|勤怠月別項目087                                                                                 |
|TMO_NCALC088|NUMBER||是|勤怠月別項目088                                                                                 |
|TMO_NCALC089|NUMBER||是|勤怠月別項目089                                                                                 |
|TMO_NCALC090|NUMBER||是|勤怠月別項目090                                                                                 |
|TMO_NCALC091|NUMBER||是|勤怠月別項目091                                                                                 |
|TMO_NCALC092|NUMBER||是|勤怠月別項目092                                                                                 |
|TMO_NCALC093|NUMBER||是|勤怠月別項目093                                                                                 |
|TMO_NCALC094|NUMBER||是|勤怠月別項目094                                                                                 |
|TMO_NCALC095|NUMBER||是|勤怠月別項目095                                                                                 |
|TMO_NCALC096|NUMBER||是|勤怠月別項目096                                                                                 |
|TMO_NCALC097|NUMBER||是|勤怠月別項目097                                                                                 |
|TMO_NCALC098|NUMBER||是|勤怠月別項目098                                                                                 |
|TMO_NCALC099|NUMBER||是|勤怠月別項目099                                                                                 |
|TMO_NCALC100|NUMBER||是|勤怠月別項目100                                                                                 |
|TMO_NMONTH_CHK_FLG|NUMBER||是|同月内振替フラグ  1:同月内で振替  null:同週内で振替       |
|TMO_NCALC101|NUMBER||是|勤怠月別項目101|
|TMO_NCALC102|NUMBER||是|勤怠月別項目102|
|TMO_NCALC103|NUMBER||是|勤怠月別項目103|
|TMO_NCALC104|NUMBER||是|勤怠月別項目104|
|TMO_NCALC105|NUMBER||是|勤怠月別項目105|
|TMO_NCALC106|NUMBER||是|勤怠月別項目106|
|TMO_NCALC107|NUMBER||是|勤怠月別項目107|
|TMO_NCALC108|NUMBER||是|勤怠月別項目108|
|TMO_NCALC109|NUMBER||是|勤怠月別項目109|
|TMO_NCALC110|NUMBER||是|勤怠月別項目110|
|TMO_NCALC111|NUMBER||是|勤怠月別項目111|
|TMO_NCALC112|NUMBER||是|勤怠月別項目112|
|TMO_NCALC113|NUMBER||是|勤怠月別項目113|
|TMO_NCALC114|NUMBER||是|勤怠月別項目114|
|TMO_NCALC115|NUMBER||是|勤怠月別項目115|
|TMO_NCALC116|NUMBER||是|勤怠月別項目116|
|TMO_NCALC117|NUMBER||是|勤怠月別項目117|
|TMO_NCALC118|NUMBER||是|勤怠月別項目118|
|TMO_NCALC119|NUMBER||是|勤怠月別項目119|
|TMO_NCALC120|NUMBER||是|勤怠月別項目120|
|TMO_NCALC121|NUMBER||是|勤怠月別項目121|
|TMO_NCALC122|NUMBER||是|勤怠月別項目122|
|TMO_NCALC123|NUMBER||是|勤怠月別項目123|
|TMO_NCALC124|NUMBER||是|勤怠月別項目124|
|TMO_NCALC125|NUMBER||是|勤怠月別項目125|
|TMO_NCALC126|NUMBER||是|勤怠月別項目126|
|TMO_NCALC127|NUMBER||是|勤怠月別項目127|
|TMO_NCALC128|NUMBER||是|勤怠月別項目128|
|TMO_NCALC129|NUMBER||是|勤怠月別項目129|
|TMO_NCALC130|NUMBER||是|勤怠月別項目130|
|TMO_NCALC131|NUMBER||是|勤怠月別項目131|
|TMO_NCALC132|NUMBER||是|勤怠月別項目132|
|TMO_NCALC133|NUMBER||是|勤怠月別項目133|
|TMO_NCALC134|NUMBER||是|勤怠月別項目134|
|TMO_NCALC135|NUMBER||是|勤怠月別項目135|
|TMO_NCALC136|NUMBER||是|勤怠月別項目136|
|TMO_NCALC137|NUMBER||是|勤怠月別項目137|
|TMO_NCALC138|NUMBER||是|勤怠月別項目138|
|TMO_NCALC139|NUMBER||是|勤怠月別項目139|
|TMO_NCALC140|NUMBER||是|勤怠月別項目140|
|TMO_NCALC141|NUMBER||是|勤怠月別項目141|
|TMO_NCALC142|NUMBER||是|勤怠月別項目142|
|TMO_NCALC143|NUMBER||是|勤怠月別項目143|
|TMO_NCALC144|NUMBER||是|勤怠月別項目144|
|TMO_NCALC145|NUMBER||是|勤怠月別項目145|
|TMO_NCALC146|NUMBER||是|勤怠月別項目146|
|TMO_NCALC147|NUMBER||是|勤怠月別項目147|
|TMO_NCALC148|NUMBER||是|勤怠月別項目148|
|TMO_NCALC149|NUMBER||是|勤怠月別項目149|
|TMO_NCALC150|NUMBER||是|勤怠月別項目150|
|TMO_NCALC151|NUMBER||是|勤怠月別項目151|
|TMO_NCALC152|NUMBER||是|勤怠月別項目152|
|TMO_NCALC153|NUMBER||是|勤怠月別項目153|
|TMO_NCALC154|NUMBER||是|勤怠月別項目154|
|TMO_NCALC155|NUMBER||是|勤怠月別項目155|
|TMO_NCALC156|NUMBER||是|勤怠月別項目156|
|TMO_NCALC157|NUMBER||是|勤怠月別項目157|
|TMO_NCALC158|NUMBER||是|勤怠月別項目158|
|TMO_NCALC159|NUMBER||是|勤怠月別項目159|
|TMO_NCALC160|NUMBER||是|勤怠月別項目160|
|TMO_NCALC161|NUMBER||是|勤怠月別項目161|
|TMO_NCALC162|NUMBER||是|勤怠月別項目162|
|TMO_NCALC163|NUMBER||是|勤怠月別項目163|
|TMO_NCALC164|NUMBER||是|勤怠月別項目164|
|TMO_NCALC165|NUMBER||是|勤怠月別項目165|
|TMO_NCALC166|NUMBER||是|勤怠月別項目166|
|TMO_NCALC167|NUMBER||是|勤怠月別項目167|
|TMO_NCALC168|NUMBER||是|勤怠月別項目168|
|TMO_NCALC169|NUMBER||是|勤怠月別項目169|
|TMO_NCALC170|NUMBER||是|勤怠月別項目170|
|TMO_NCALC171|NUMBER||是|勤怠月別項目171|
|TMO_NCALC172|NUMBER||是|勤怠月別項目172|
|TMO_NCALC173|NUMBER||是|勤怠月別項目173|
|TMO_NCALC174|NUMBER||是|勤怠月別項目174|
|TMO_NCALC175|NUMBER||是|勤怠月別項目175|
|TMO_NCALC176|NUMBER||是|勤怠月別項目176|
|TMO_NCALC177|NUMBER||是|勤怠月別項目177|
|TMO_NCALC178|NUMBER||是|勤怠月別項目178|
|TMO_NCALC179|NUMBER||是|勤怠月別項目179|
|TMO_NCALC180|NUMBER||是|勤怠月別項目180|
|TMO_NCALC181|NUMBER||是|勤怠月別項目181|
|TMO_NCALC182|NUMBER||是|勤怠月別項目182|
|TMO_NCALC183|NUMBER||是|勤怠月別項目183|
|TMO_NCALC184|NUMBER||是|勤怠月別項目184|
|TMO_NCALC185|NUMBER||是|勤怠月別項目185|
|TMO_NCALC186|NUMBER||是|勤怠月別項目186|
|TMO_NCALC187|NUMBER||是|勤怠月別項目187|
|TMO_NCALC188|NUMBER||是|勤怠月別項目188|
|TMO_NCALC189|NUMBER||是|勤怠月別項目189|
|TMO_NCALC190|NUMBER||是|勤怠月別項目190|
|TMO_NCALC191|NUMBER||是|勤怠月別項目191|
|TMO_NCALC192|NUMBER||是|勤怠月別項目192|
|TMO_NCALC193|NUMBER||是|勤怠月別項目193|
|TMO_NCALC194|NUMBER||是|勤怠月別項目194|
|TMO_NCALC195|NUMBER||是|勤怠月別項目195|
|TMO_NCALC196|NUMBER||是|勤怠月別項目196|
|TMO_NCALC197|NUMBER||是|勤怠月別項目197|
|TMO_NCALC198|NUMBER||是|勤怠月別項目198|
|TMO_NCALC199|NUMBER||是|勤怠月別項目199|
|TMO_NCALC200|NUMBER||是|勤怠月別項目200|
|TMO_CSTS_WORK|VARCHAR2||是|裁量労働：勤務状態|
|TMO_CSTS_HEALTH|VARCHAR2||是|裁量労働：健康状態|
|TMO_CSTS_PLAN|VARCHAR2||是|予定確定|
|TMO_NPAID_CR_HOURS|NUMBER||是|付与後：年休繰越時間数|
|TMO_NPAID_BEF_REST_DAYS|NUMBER||是|付与前：付与前日残日数|
|TMO_NPAID_BEF_REST_HOURS|NUMBER||是|付与前：付与前日残時間|
|TMO_NLOSE_DAYS|NUMBER||是|喪失日数|
|TMO_NLOSE_HOURS|NUMBER||是|喪失時間数|
