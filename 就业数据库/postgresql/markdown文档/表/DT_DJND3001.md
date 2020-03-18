# 非常勤職員基本情報DB                   20030711 idx_2作成(DT_DJND3001)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|CCOMPKB|varchar||是|会社区分                                                        |
|DSTART|date||否|開始日                                                         |
|SSTART|varchar||是|開始日（和暦）                                                     |
|DEND|date||是|終了日                                                         |
|SEND|varchar||是|終了日（和暦）                                                     |
|CSHAINNO|varchar||否|職員番号                                                        |
|KEY_DTE|varchar||是|基準年月日                                                       |
|DKEY_DTE|date||是|基準年月日（西暦）                                                   |
|CNAMEKNA|varchar||是|カナ氏名                                                        |
|CNAMEKNJ|varchar||是|漢字氏名                                                        |
|TSUXHI_KJ_NME|varchar||是|通知書用漢字氏名                                                    |
|INSEI_CDE|varchar||是|院生区分                          京都追加                          |
|INSEI_NME|varchar||是|院生区分名称                        京都追加                          |
|SEIBETU_KBN|varchar||是|性別区分                                                        |
|SEIBETU_NME|varchar||是|性別                                                          |
|BIRTH_DTE|varchar||是|生年月日                                                        |
|DBIRTH_DTE|date||是|生年月日（西暦）                                                    |
|AGE|numeric||是|年齢                                                          |
|KYUSEI_NME|varchar||是|旧姓                                                          |
|KAISEI_DTE|varchar||是|改姓年月日                                                       |
|DKAISEI_DTE|date||是|改姓年月日（西暦）                                                   |
|HONSEKI_CDE|varchar||是|本籍コード                                                       |
|HONSEKI_NME|varchar||是|本籍                                                          |
|KOKUSEKI_CDE|varchar||是|国籍区分コード                                                     |
|KOKUSEKI_NME|varchar||是|国籍区分                                                        |
|HJKSYK_NNMN_CDE|varchar||是|非常勤職員任免コード                                                  |
|HJKSYK_NNMN_NME|varchar||是|非常勤職員任免                                                     |
|NINYO_DTE|varchar||是|任用年月日                                                       |
|DNINYO_DTE|date||是|任用年月日（西暦）                                                   |
|NNKI_MR_DTE|varchar||是|任期満了年月日                                                     |
|DNNKI_MR_DTE|date||是|任期満了年月日（西暦）                                                 |
|TSYK_DTE|varchar||是|退職等年月日                                                      |
|DTSYK_DTE|date||是|退職等年月日（西暦）                                                  |
|HJKSYK_SKIN_CDE|varchar||是|非常勤職員職員コード                                                  |
|HJKSYK_SKIN_NME|varchar||是|非常勤職員職員                                                     |
|HTREI_SYKIN_DTE|varchar||是|発令年月日（職員区分）                                                 |
|DHTREI_SYKIN_DTE|date||是|発令年月日（職員区分）                                                 |
|HJKSYK_MISY_CDE|varchar||是|非常勤職員名称コード                                                  |
|HJKSYK_MISY_NME|varchar||是|非常勤職員名称                                                     |
|SYKSY_CDE|varchar||是|職種コード                                                       |
|SYKSY_NME|varchar||是|職種                                                          |
|HTREI_SYKSY_DTE|varchar||是|発令年月日（職種）                                                   |
|DHTREI_SYKSY_DTE|date||是|発令年月日（職種） 西暦                                                |
|TSJ_SKMEI_CDE|varchar||是|定数上の職名コード                     2001.03.30(I)                 |
|TSJ_SKMEI_NME|varchar||是|定数上の職名                        2001.03.30(I)                 |
|KKN_CDE|varchar||是|機関コード                                                       |
|KKN_NME|varchar||是|機関                                                          |
|HTREI_KKN_DTE|varchar||是|発令年月日（機関）                                                   |
|DHTREI_KKN_DTE|date||是|発令年月日（機関） 西暦                                                |
|SZK_CDE|varchar||是|所属コード                                                       |
|SZK_NME|varchar||是|所属                                                          |
|BKYK_CDE|varchar||是|部局コード                                                       |
|BKYK_NME|varchar||是|部局                                                          |
|SORT_JYN|numeric||是|ソート順                                                        |
|HTREI_SYZK_DTE|varchar||是|発令年月日（所属）                                                   |
|DHTREI_SYZK_DTE|date||是|発令年月日（所属） 西暦                                                |
|KKRKOZA_CDE|varchar||是|係・講座コード                                                     |
|KKRKOZA_NME|varchar||是|係・講座                                                        |
|HTREI_KK_DTE|varchar||是|発令年月日（係・講座）                                                 |
|DHTREI_KK_DTE|date||是|発令年月日（係・講座）                                                 |
|BKU_SYZK|varchar||是|備考欄用所属                                                      |
|ZISYK_NEN|numeric||是|在職年数                                                        |
|HJKSYK_KIH_CDE|varchar||是|非常勤職員経費コード                                                  |
|HJKSYK_KIH_NME|varchar||是|非常勤職員経費                                                     |
|HJKSYK_KMTKB_CDE|varchar||是|期末勤勉区分コード                                                   |
|HJKSYK_KMTKB_NME|varchar||是|期末勤勉区分                                                      |
|HKHY_CDE|varchar||是|俸給表コード                                                      |
|HKHY_NME|varchar||是|俸給表                                                         |
|KJYNST_KYU|numeric||是|基準相当級                                                       |
|KJYNST_GOHO|numeric||是|基準相当号俸                                                      |
|KJYNST_TANKA|numeric||是|基準相当単価(日給･時給)                                               |
|KNGKUTKR_KBN|varchar||是|金額打切区分                                                      |
|TYSI_SUM|numeric||是|調整数合計                                                       |
|TSTA_SKR|numeric||是|調整手当支給率                                                     |
|SKYUST_KYU|numeric||是|支給相当級                                                       |
|SKYUST_GOHO|numeric||是|支給相当号俸                                                      |
|SKYUST_TANKA|numeric||是|支給相当単価(日給･時給)                                               |
|HTREINGB_DTE|varchar||是|発令年月日                                                       |
|DHTREINGB_DTE|date||是|発令年月日（西暦）                                                   |
|GKRK_KYUY_CDE|varchar||是|学歴区分ｺｰﾄﾞ（給与決定）                                              |
|GKRK_KYUY_NME|varchar||是|学歴区分（給与決定）                                                  |
|GET_KYUY_DTE|varchar||是|取得年月日（給与決定）                                                 |
|DGET_KYUY_DTE|date||是|取得年月日（給与決定）                                                 |
|GNKNSYK_1|varchar||是|現（元）官職欄(1)                                                  |
|GNKNSYK_2|varchar||是|現（元）官職欄(2)                                                  |
|GNKNSYK_3|varchar||是|現（元）官職欄(3)                                                  |
|GNKNSYK_4|varchar||是|現（元）官職欄(4)                                                  |
|IDOSK_CDE|varchar||是|退職等後の異動先区分                    京都追加                          |
|IDOSK_NME|varchar||是|退職等後の異動先名称                    京都追加                          |
|KJYNGKRK_CDE|varchar||是|基準学歴区分コード                                                   |
|KJYNGKRK_NME|varchar||是|基準学歴区分                                                      |
|NNYUJKKN_NEN|numeric||是|任用時経験年数                                                     |
|TYUSIKKN_NEN|numeric||是|調整経験年数                                                      |
|SYNKY_HOKYU_CDE|varchar||是|(12条)俸給表コード                                                 |
|SYNKY_HOKYU|varchar||是|(12条)俸給表                                                    |
|SYNKY_KYU|numeric||是|(12条)級                                                      |
|SYNKY_GOHO|numeric||是|(12条)号俸                                                     |
|TISYKTAT_GAKU|numeric||是|退職手当額                                                       |
|HKY_GGK|numeric||是|俸給月額                                                        |
|HKY_TYSI_GK|numeric||是|俸給の調整額                                                      |
|KNSYZI_CDE|varchar||是|調整手当官署所在地区分                                                 |
|KNSYZI_NME|varchar||是|調整手当官署所在地区分                                                 |
|TUKNTAT_GAKU|numeric||是|通勤手当支給額                                                     |
|JUKYTAT_GAKU|numeric||是|住居手当支給額                                                     |
|KOSIN_USE|varchar||否|更新者                                                         |
|LAST_PGM|varchar||否|更新PGM                                                       |
|LAST_DTE|date||否|更新日時                                                        |
|KOSIN_KBN|varchar||否|更新区分                                                        |
|SYOGO_CDE|varchar||是|称号区分                          2001.06.11追加                  |
|SYOGO_NME|varchar||是|称号                            2001.06.11追加                  |
|GAKUSEKI_NO|varchar||是|学籍番号                          2001.06.11追加                  |
|HONMU_CDE|varchar||是|本務区分コード                                                     |
|HONMU_NME|varchar||是|本務区分                                                        |
|HONMUSAKI_NME|varchar||是|本務先                                                         |
|KINMU_CDE|varchar||是|勤務形態区分コード                                                   |
|KINMU_NME|varchar||是|勤務形態区分                                                      |
|KYU_KN_NME|varchar||是|旧姓使用カナ氏名                      2001.10.22 様式変更               |
|KYU_KJ_NME|varchar||是|旧姓使用漢字氏名                      2001.10.22 様式変更               |
|NME_PRT_FLG|varchar||是|漢字氏名印字無フラグ                    2001.10.22 様式変更 (1:印字無)       |
|KYU_PRT_FLG|varchar||是|旧姓印字無フラグ                      2001.11.16 追加                 |
|KYUYO_KOJIN_CDE|varchar||是|給与個人番号                        2002.03.11 追加                 |
|YOSAN_CDE_2|varchar||是|予算項コード２                                                     |
|YOSAN_NME_2|varchar||是|予算項２                                                        |
|YOSAN_CDE_3|varchar||是|予算項コード３                                                     |
|YOSAN_NME_3|varchar||是|予算項３                                                        |
|CMNUSER|varchar||是|ﾕｰｻﾞｰ                                                       |
|DMNDATE|date||是|更新日                                                         |
|MAE_NNYO_KSNEN|numeric||是|null|
|C01_CDE|varchar||是|更新の有無コード|
|C01_NME|varchar||是|更新の有無|
|C02_CDE|varchar||是|就業の場所コード|
|C02_NME|varchar||是|就業の場所|
|C03_CDE|varchar||是|通勤手当相当給与該当コード|
|C03_NME|varchar||是|通勤手当相当給与該当|
|C04_CDE|varchar||是|規定改正による単価改訂コード|
|C04_NME|varchar||是|規定改正による単価改訂|
|C05_CDE|varchar||是|任期途中見直し該当コード|
|C05_NME|varchar||是|任期途中見直し該当|
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
|C01_DTE|varchar||是|単価改訂日|
|D01_DTE|date||是|null|
|C02_DTE|varchar||是|当初雇用年月日|
|D02_DTE|date||是|null|
|C03_DTE|varchar||是|null|
|D03_DTE|date||是|null|
|C04_DTE|varchar||是|null|
|D04_DTE|date||是|null|
|C05_DTE|varchar||是|null|
|D05_DTE|date||是|null|
|ADRSS|varchar||是|住所                                                           |
|CYUBIN|varchar||是|郵便番号                                                       |
|ZAIRYU_CDE|varchar||是|在留資格コード|
|ZAIRYU_NME|varchar||是|在留資格|
|ZAIRYU_KGN_DTE|varchar||是|在留期限|
|DZAIRYU_KGN_DTE|date||是|在留期限（西暦）|
|SIKAKUGAI_DTE|varchar||是|資格外活動許可の期限日|
|DSIKAKUGAI_DTE|date||是|資格外活動許可の期限日（西暦）|
