# 個人基本情報DB                      2000.12.08　以下のDBを統合
　・個人給与情報DB(HJHD0210)
　・給与システム情報DB(AKPAY)(DT_DJND0110)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|varchar||是|会社区分                          |
|DSTART|date||否|開始日                           |
|SSTART|varchar||是|開始日（和暦）                       |
|DEND|date||是|終了日                           |
|SEND|varchar||是|終了日（和暦）                       |
|CSHAINNO|varchar||否|職員番号                          |
|KEY_DTE|varchar||是|基準年月日                         |
|DKEY_DTE|date||是|基準年月日（西暦）                     |
|CNAMEKNA|varchar||是|カナ氏名                          ↓個人基本情報DB(HJHS0110)|
|CNAMEKNJ|varchar||是|漢字氏名                          |
|SEIBETU_KBN|varchar||是|性別区分                          |
|SEIBETU_NME|varchar||是|性別                            |
|BIRTH_DTE|varchar||是|生年月日                          |
|DBIRTH_DTE|date||是|生年月日（西暦）                      |
|AGE|numeric||是|年齢                            |
|NDM_AGE|numeric||是|年度末年齢                         |
|KYUSEI_NME|varchar||是|旧姓                            |
|KAISEI_DTE|varchar||是|改姓年月日                         |
|DKAISEI_DTE|date||是|改姓年月日（西暦）                     |
|SAIYO_DTE|varchar||是|国家公務員採用年月日                    |
|DSAIYO_DTE|date||是|国家公務員採用年月日西暦                  |
|SAIYO_R_DTE|varchar||是|国家公務員再採用年月日                   |
|DSAIYO_R_DTE|date||是|国家公務員再採用年月日                   |
|NWKKN_HTREI_DTE|varchar||是|現機関発令年月日                      |
|DNWKKN_HTREI_DTE|date||是|現機関発令年月日（西暦）                  |
|KINMSAKI_CDE|varchar||是|採用直前の勤務先コード                   |
|KINMSAKI_NME|varchar||是|採用直前の勤務先等                     |
|HONSEKI_CDE|varchar||是|本籍（国籍）コード                     |
|HONSEKI_NME|varchar||是|本籍（国籍）                        |
|KOKUSEKI_CDE|varchar||是|国籍区分コード                       |
|KOKUSEKI_NME|varchar||是|国籍                            |
|ZAIRYU_CDE|varchar||是|在留資格コード                       |
|ZAIRYU_NME|varchar||是|在留資格                          |
|NNMN_CDE|varchar||是|任免区分コード                       |
|NNMN_NME|varchar||是|任免区分                          |
|TSYK_IDO_CDE|varchar||是|退職等異動種目コード                    |
|TSYK_IDO_NME|varchar||是|退職等異動種目                       |
|TSYK_DTE|varchar||是|退職等年月日                        |
|DTSYK_DTE|date||是|退職等年月日（西暦）                    |
|TSYKGO_CDE|varchar||是|退職等後の就職先等コード                  |
|TSYKGO_NME|varchar||是|退職等後の就職先等                     |
|GYO2_CDE|varchar||是|行（二）区分コード                     |
|GYO2_NME|varchar||是|行（二）区分                        |
|TIKBN_CDE|varchar||是|定員区分コード                       |
|TIKBN_NME|varchar||是|定員区分                          |
|KMKBN_CDE|varchar||是|勤務区分コード                       |
|KMKBN_NME|varchar||是|勤務区分                          |
|EXP_NUM|numeric||是|経験年数                          |
|ZIKN_NUM|numeric||是|在官年数                          |
|TSYKTAT_NUM|numeric||是|退職手当法上の勤続年数                   |
|K_SKIN_CDE|varchar||是|教育職員コード                       |
|K_SKIN_NME|varchar||是|教育職員                          |
|NNKI_MR_DTE|varchar||是|任期満了年月日                       |
|DNNKI_MR_DTE|date||是|任期満了年月日（西暦）                   |
|RINJ_HIDITI_CDE|varchar||是|臨時的任用被代替者個人番                  ★2001.2.14(I)|
|RINJ_HIDITI_NME|varchar||是|臨時的任用被代替者                     |
|BG_ED_DTE|varchar||是|分限関係 終了予定年月日                  |
|DBG_ED_DTE|date||是|分限関係 終了予定年月日                  |
|BG_SK_RITU|varchar||是|分限関係 支給率                      |
|BG_KKN_CDE|varchar||是|分限関係 国際機関コード                  |
|BG_KKN_NME|varchar||是|分限関係 国際機関名                    |
|BG_HK_CDE|varchar||是|分限関係 派遣先コード                   |
|BG_HK_NME|varchar||是|分限関係 派遣先                      |
|GK_KNMEI_CDE|varchar||是|現官職 官名コード                     ★2000.12.11(U) 1→2ﾊﾞｲﾄ|
|GK_KNMEI_NME|varchar||是|現官職 官名                        |
|GK_HKYH_CDE|varchar||是|現官職 俸給表コード                    |
|GK_HKYH_NME|varchar||是|現官職 俸給表                       |
|GK_KYU|numeric||是|現官職 級                         |
|GK_SZK|varchar||是|現官職 所属等                       |
|NNMN_IDO_CDE|varchar||是|任免異動種目コード                     |
|NNMN_IDO_NME|varchar||是|任免異動種目                        |
|NNMN_HTREI_DTE|varchar||是|発令等年月日                        |
|DNNMN_HTREI_DTE|date||是|発令等年月日（西暦）                    |
|KKN_CDE|varchar||是|現機関コード                        |
|KKN_NME|varchar||是|現機関                           |
|KKN_IDO_CDE|varchar||是|機関任免異動種目コード                   ★2001.2.12(I)|
|KKN_IDO_NME|varchar||是|機関任免異動種目                      ★2001.2.12(I)|
|KNMEI_CDE|varchar||是|官名コード                         |
|KNMEI_NME|varchar||是|官名                            |
|SYKSY_CDE|varchar||是|職種コード                         |
|SYKSY_NME|varchar||是|職種                            |
|SYKSY_HTREI_DTE|varchar||是|(職種)発令年月日                     |
|DSYKSY_HTREI_DTE|date||是|(職種)発令年月日（西暦）                 |
|SYKSY_NUM|numeric||是|職種年数                          |
|SYKSY_IDO_CDE|varchar||是|職種任免異動種目コード                   ★2001.2.12(I)|
|SYKSY_IDO_NME|varchar||是|職種任免異動種目                      ★2001.2.12(I)|
|TSJ_SKMEI_CDE|varchar||是|定数上の職名コード                     ☆2001.01.17ADD|
|TSJ_SKMEI_NME|varchar||是|定数上の職名                        ☆2001.01.17ADD|
|YOSAN_CDE|varchar||是|予算項コード                        |
|YOSAN_NME|varchar||是|予算項                           |
|YOSAN_HTREI_DTE|varchar||是|予算項発令年月日                      ★2001.2.12(I)|
|DYOSAN_HTREI_DTE|date||是|予算項発令年月日（西暦）                  |
|YOSAN_IDO_CDE|varchar||是|予算項任免異動種目コード                  ★2001.2.12(I)|
|YOSAN_IDO_NME|varchar||是|予算項任免異動種目                     ★2001.2.12(I)|
|SZK_CDE|varchar||是|所属コード                         |
|SZK_NME|varchar||是|所属                            |
|BKYK_CDE|varchar||是|部局コード                         |
|BKYK_NME|varchar||是|部局                            |
|SORT_JYN|numeric||是|ソート順                          |
|SZK_HTREI_DTE|varchar||是|(所属)発令年月日                     |
|DSZK_HTREI_DTE|date||是|(所属)発令年月日（西暦）                 |
|SZK_NUM|numeric||是|所属年数                          |
|SZK_IDO_CDE|varchar||是|所属任免異動種目コード                   ★2001.2.12(I)|
|SZK_IDO_NME|varchar||是|所属任免異動種目                      ★2001.2.12(I)|
|KEI_CDE|varchar||是|系コード                          |
|KEI_NME|varchar||是|系                             |
|KKRKOZA_CDE|varchar||是|係・講座コード                       |
|KKRKOZA_NME|varchar||是|係・講座                          |
|KEI_HTREI_DTE|varchar||是|(係)発令年月日                      |
|DKEI_HTREI_DTE|date||是|(係)発令年月日（西暦）                  |
|KKR_NUM|numeric||是|係年数                           |
|KKRKOZA_IDO_CDE|varchar||是|係講座任免異動種目コード                  ★2001.2.12(I)|
|KKRKOZA_IDO_NME|varchar||是|係講座任免異動種目                     ★2001.2.12(I)|
|HKYH_CDE|varchar||是|俸給表コード                        |
|HKYH_NME|varchar||是|俸給表                           |
|KYU|numeric||是|級                             |
|KYU_IDO_CDE|varchar||是|級異動種目コード                      |
|KYU_IDO_NME|varchar||是|級異動種目                         |
|KYU_HTREI_DTE|varchar||是|(級)発令年月日                      |
|DKYU_HTREI_DTE|date||是|(級)発令年月日（西暦）                  |
|ZIKYU_NUM|numeric||是|在級年数                          |
|GOHO|numeric||是|号俸                            |
|GOHO_IDO_CDE|varchar||是|号俸異動種目コード                     |
|GOHO_IDO_NME|varchar||是|号俸異動種目                        |
|GOHO_HTREI_DTE|varchar||是|(号俸)発令年月日                     |
|DGOHO_HTREI_DTE|date||是|(号俸)発令年月日（西暦）                 |
|ST_HKYH_CDE|varchar||是|昇格直前の俸給表コード                   |
|ST_HKYH_NME|varchar||是|昇格直前の俸給表                      |
|ST_KYU|numeric||是|昇格直前の級                        |
|STKYU_HTREI_DTE|varchar||是|昇格直前の級発令年月日                   |
|DSTKYU_HTREI_DTE|date||是|昇格直前の級発令年月日                   |
|ST_GOHO|numeric||是|昇格直前の号俸                       |
|STGOH_HTREI_DTE|varchar||是|昇格直前の号俸発令年月日                  |
|DSTGOH_HTREI_DTE|date||是|昇格直前の号俸発令年月日                  |
|NXT_SYKY_DTE|varchar||是|次期昇給期                         |
|KYYO_GK_CDE|varchar||是|給与決定学歴区分コード                   |
|KYYO_GK_NME|varchar||是|給与決定学歴区分                      |
|GET_DTE|varchar||是|取得年月日                         ↑個人基本情報DB(HJHS0110)|
|DGET_DTE|date||是|取得年月日（西暦）                     |
|ENSN_FLG|varchar||是|延伸中フラグ                        1:延伸中(★2000.12.11追加)|
|LAST_TKSY_DTE|varchar||是|前回特昇年月日                       ↓個人給与情報DB(HJHS0210)|
|DLAST_TKSY_DTE|date||是|前回特昇年月日（西暦）                   |
|TKSY_ENKI_NUM|numeric||是|特昇延回数                         |
|HKTYS_SIKY_CDE|varchar||是|(俸給の特別調整額)　区分                 |
|HKTYS_SIKY_NME|varchar||是|　支給区分                         |
|HKTYS_SIKY_RITU|varchar||是|　支給率                          |
|HKTYS_HTREI_DTE|varchar||是|　発令年月日                        |
|DHKTYS_HTREI_DTE|date||是|　発令年月日（西暦）                    |
|HKTYS_YOSAN_CDE|varchar||是|　予算項コード                       |
|HKTYS_YOSAN_NME|varchar||是|　予算項                          |
|HKTYS_TEISI_DTE|varchar||是|　支給停止・再支給年月日                  |
|DHKTYS_TEISI_DTE|date||是|　支給停止・再支給年月日                  |
|TSYK_TAT|numeric||是|(支給額)退職手当                     2001.01.18|
|HKY_GGK|numeric||是|俸給月額                          |
|HKY_TYSI_GK|numeric||是|俸給の調整額                        |
|TYSI_NUM|numeric||是|俸給の調整額調整数                     ↑個人給与情報DB(HJHS0210)|
|DGKIN_TYSISUM|numeric||是|大学院担当 調整数                     ★2001.2.6 調整数合計要素|
|TYSISUM_NUM|numeric||是|調整数合計                         |
|ADRSS|varchar||是|現住所                           ↓給与システム情報DB(AKPAY)|
|PAI043|numeric||是|6月期役職段階別加算割合                  |
|PAI044|numeric||是|12月期役職段階別加算割合                 |
|PAI045|numeric||是|3月期役職段階別加算割合                  ↑給与システム情報DB(AKPAY)|
|KANRIKSN3_NUM|numeric||是|3月期管理加算割合                     |
|KANRIKSN6_NUM|numeric||是|6月期管理加算割合                     |
|KANRIKSN12_NUM|numeric||是|12月期管理加算割合                    |
|KOSIN_USE|varchar||否|更新者                           |
|LAST_PGM|varchar||否|更新PGM                         |
|LAST_DTE|date||否|更新日時                          |
|KOSIN_KBN|varchar||否|更新区分                          |
|KYU_KN_NME|varchar||是|旧姓使用カナ氏名                      2001.10.22 様式変更|
|KYU_KJ_NME|varchar||是|旧姓使用漢字氏名                      2001.10.22 様式変更|
|NME_PRT_FLG|varchar||是|漢字氏名印字無フラグ                    2001.10.22 様式変更 (1:印字無)|
|CHO_START_DTE|varchar||是|調整手当開始年月日                     2001.11.14 追加|
|DCHO_START_DTE|date||是|調整手当開始年月日 西暦                  |
|CHO_END_DTE|varchar||是|調整手当終了年月日                     2001.11.14 追加|
|DCHO_END_DTE|date||是|調整手当終了年月日 西暦                  |
|CHO_SIKY_RITU|numeric||是|調整手当支給率                       2001.11.14 追加|
|CHO_ZIKN_CDE|varchar||是|調整手当在勤官署コード                   2001.11.14 追加|
|CHO_ZIKN_NME|varchar||是|調整手当在勤官署                      2001.11.14 追加|
|CHO_KNSYZI_CDE|varchar||是|調整手当官署所在地コード                  2001.11.14 追加|
|CHO_KNSYZI_NME|varchar||是|調整手当官署所在地                     2001.11.14 追加|
|KYU_PRT_FLG|varchar||是|旧姓印字無フラグ                      2001.11.15 追加|
|CMNUSER|varchar||是|ﾕｰｻﾞｰ                         |
|DMNDATE|date||是|更新日                           |
|C01_CDE|varchar||是|null|
|C01_NME|varchar||是|長期組合員番号|
|C02_CDE|varchar||是|就業の場所コード|
|C02_NME|varchar||是|就業の場所|
|C03_CDE|varchar||是|昇給調整の有無コード|
|C03_NME|varchar||是|昇給調整の有無|
|C04_CDE|varchar||是|加算号俸区分|
|C04_NME|varchar||是|加算号俸名称|
|C05_CDE|varchar||是|null|
|C05_NME|varchar||是|null|
|C06_CDE|varchar||是|null|
|C06_NME|varchar||是|null|
|C07_CDE|varchar||是|null|
|C07_NME|varchar||是|null|
|C08_CDE|varchar||是|null|
|C08_NME|varchar||是|null|
|C09_CDE|varchar||是|null|
|C09_NME|varchar||是|null|
|C10_CDE|varchar||是|null|
|C10_NME|varchar||是|null|
|C01_DTE|varchar||是|昇給起算日(和暦)|
|D01_DTE|date||是|昇給起算日(西暦)|
|C02_DTE|varchar||是|null|
|D02_DTE|date||是|null|
|C03_DTE|varchar||是|null|
|D03_DTE|date||是|null|
|C04_DTE|varchar||是|null|
|D04_DTE|date||是|null|
|C05_DTE|varchar||是|null|
|D05_DTE|date||是|null|
|N01_NUM|numeric||是|予備数値1   20070820 分限関係 支給額|
|N02_NUM|numeric||是|予備数値2|
|N03_NUM|numeric||是|予備数値3|
|N04_NUM|numeric||是|予備数値4|
|N05_NUM|numeric||是|予備数値5|
|CYUBIN|varchar||是|郵便番号                                                       |
|ZAIRYU_KGN_DTE|varchar||是|在留期限|
|DZAIRYU_KGN_DTE|date||是|在留期限（西暦）|
|SIKAKUGAI_DTE|varchar||是|資格外活動許可の期限日|
|DSIKAKUGAI_DTE|date||是|資格外活動許可の期限日（西暦）|
