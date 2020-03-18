# 非常勤職員基本情報DB                   20030711 idx_2作成(DT_DJND3001)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TSJ_SKMEI_CDE|NVARCHAR2||是|定数上の職名コード                     2001.03.30(I)                 |
|TSJ_SKMEI_NME|NVARCHAR2||是|定数上の職名                        2001.03.30(I)                 |
|KKN_CDE|NVARCHAR2||是|機関コード                                                       |
|KKN_NME|NVARCHAR2||是|機関                                                          |
|HTREI_KKN_DTE|NVARCHAR2||是|発令年月日（機関）                                                   |
|DHTREI_KKN_DTE|DATE||是|発令年月日（機関） 西暦                                                |
|SZK_CDE|NVARCHAR2||是|所属コード                                                       |
|SZK_NME|NVARCHAR2||是|所属                                                          |
|BKYK_CDE|NVARCHAR2||是|部局コード                                                       |
|BKYK_NME|NVARCHAR2||是|部局                                                          |
|SORT_JYN|NUMBER||是|ソート順                                                        |
|HTREI_SYZK_DTE|NVARCHAR2||是|発令年月日（所属）                                                   |
|DHTREI_SYZK_DTE|DATE||是|発令年月日（所属） 西暦                                                |
|KKRKOZA_CDE|NVARCHAR2||是|係・講座コード                                                     |
|KKRKOZA_NME|NVARCHAR2||是|係・講座                                                        |
|HTREI_KK_DTE|NVARCHAR2||是|発令年月日（係・講座）                                                 |
|DHTREI_KK_DTE|DATE||是|発令年月日（係・講座）                                                 |
|BKU_SYZK|NVARCHAR2||是|備考欄用所属                                                      |
|ZISYK_NEN|NUMBER||是|在職年数                                                        |
|HJKSYK_KIH_CDE|NVARCHAR2||是|非常勤職員経費コード                                                  |
|HJKSYK_KIH_NME|NVARCHAR2||是|非常勤職員経費                                                     |
|HJKSYK_KMTKB_CDE|NVARCHAR2||是|期末勤勉区分コード                                                   |
|HJKSYK_KMTKB_NME|NVARCHAR2||是|期末勤勉区分                                                      |
|HKHY_CDE|NVARCHAR2||是|俸給表コード                                                      |
|HKHY_NME|NVARCHAR2||是|俸給表                                                         |
|KJYNST_KYU|NUMBER||是|基準相当級                                                       |
|KJYNST_GOHO|NUMBER||是|基準相当号俸                                                      |
|KJYNST_TANKA|NUMBER||是|基準相当単価(日給･時給)                                               |
|KNGKUTKR_KBN|NVARCHAR2||是|金額打切区分                                                      |
|TYSI_SUM|NUMBER||是|調整数合計                                                       |
|TSTA_SKR|NUMBER||是|調整手当支給率                                                     |
|SKYUST_KYU|NUMBER||是|支給相当級                                                       |
|SKYUST_GOHO|NUMBER||是|支給相当号俸                                                      |
|SKYUST_TANKA|NUMBER||是|支給相当単価(日給･時給)                                               |
|HTREINGB_DTE|NVARCHAR2||是|発令年月日                                                       |
|DHTREINGB_DTE|DATE||是|発令年月日（西暦）                                                   |
|GKRK_KYUY_CDE|NVARCHAR2||是|学歴区分ｺｰﾄﾞ（給与決定）                                              |
|GKRK_KYUY_NME|NVARCHAR2||是|学歴区分（給与決定）                                                  |
|GET_KYUY_DTE|NVARCHAR2||是|取得年月日（給与決定）                                                 |
|DGET_KYUY_DTE|DATE||是|取得年月日（給与決定）                                                 |
|GNKNSYK_1|NVARCHAR2||是|現（元）官職欄(1)                                                  |
|GNKNSYK_2|NVARCHAR2||是|現（元）官職欄(2)                                                  |
|GNKNSYK_3|NVARCHAR2||是|現（元）官職欄(3)                                                  |
|GNKNSYK_4|NVARCHAR2||是|現（元）官職欄(4)                                                  |
|IDOSK_CDE|NVARCHAR2||是|退職等後の異動先区分                    京都追加                          |
|IDOSK_NME|NVARCHAR2||是|退職等後の異動先名称                    京都追加                          |
|KJYNGKRK_CDE|NVARCHAR2||是|基準学歴区分コード                                                   |
|KJYNGKRK_NME|NVARCHAR2||是|基準学歴区分                                                      |
|NNYUJKKN_NEN|NUMBER||是|任用時経験年数                                                     |
|TYUSIKKN_NEN|NUMBER||是|調整経験年数                                                      |
|SYNKY_HOKYU_CDE|NVARCHAR2||是|(12条)俸給表コード                                                 |
|SYNKY_HOKYU|NVARCHAR2||是|(12条)俸給表                                                    |
|SYNKY_KYU|NUMBER||是|(12条)級                                                      |
|SYNKY_GOHO|NUMBER||是|(12条)号俸                                                     |
|TISYKTAT_GAKU|NUMBER||是|退職手当額                                                       |
|HKY_GGK|NUMBER||是|俸給月額                                                        |
|HKY_TYSI_GK|NUMBER||是|俸給の調整額                                                      |
|KNSYZI_CDE|NVARCHAR2||是|調整手当官署所在地区分                                                 |
|KNSYZI_NME|NVARCHAR2||是|調整手当官署所在地区分                                                 |
|TUKNTAT_GAKU|NUMBER||是|通勤手当支給額                                                     |
|JUKYTAT_GAKU|NUMBER||是|住居手当支給額                                                     |
|KOSIN_USE|NVARCHAR2||否|更新者                                                         |
|LAST_PGM|NVARCHAR2||否|更新PGM                                                       |
|LAST_DTE|DATE||否|更新日時                                                        |
|KOSIN_KBN|NVARCHAR2||否|更新区分                                                        |
|SYOGO_CDE|NVARCHAR2||是|称号区分                          2001.06.11追加                  |
|SYOGO_NME|NVARCHAR2||是|称号                            2001.06.11追加                  |
|GAKUSEKI_NO|NVARCHAR2||是|学籍番号                          2001.06.11追加                  |
|HONMU_CDE|NVARCHAR2||是|本務区分コード                                                     |
|HONMU_NME|NVARCHAR2||是|本務区分                                                        |
|HONMUSAKI_NME|NVARCHAR2||是|本務先                                                         |
|KINMU_CDE|NVARCHAR2||是|勤務形態区分コード                                                   |
|KINMU_NME|NVARCHAR2||是|勤務形態区分                                                      |
|KYU_KN_NME|NVARCHAR2||是|旧姓使用カナ氏名                      2001.10.22 様式変更               |
|KYU_KJ_NME|NVARCHAR2||是|旧姓使用漢字氏名                      2001.10.22 様式変更               |
|NME_PRT_FLG|NVARCHAR2||是|漢字氏名印字無フラグ                    2001.10.22 様式変更 (1:印字無)       |
|KYU_PRT_FLG|NVARCHAR2||是|旧姓印字無フラグ                      2001.11.16 追加                 |
|KYUYO_KOJIN_CDE|NVARCHAR2||是|給与個人番号                        2002.03.11 追加                 |
|YOSAN_CDE_2|NVARCHAR2||是|予算項コード２                                                     |
|YOSAN_NME_2|NVARCHAR2||是|予算項２                                                        |
|YOSAN_CDE_3|NVARCHAR2||是|予算項コード３                                                     |
|YOSAN_NME_3|NVARCHAR2||是|予算項３                                                        |
|CMNUSER|NVARCHAR2||是|ﾕｰｻﾞｰ                                                       |
|DMNDATE|DATE||是|更新日                                                         |
|MAE_NNYO_KSNEN|NUMBER||是|null|
|C01_CDE|NVARCHAR2||是|更新の有無コード|
|C01_NME|NVARCHAR2||是|更新の有無|
|C02_CDE|NVARCHAR2||是|就業の場所コード|
|C02_NME|NVARCHAR2||是|就業の場所|
|C03_CDE|NVARCHAR2||是|通勤手当相当給与該当コード|
|C03_NME|NVARCHAR2||是|通勤手当相当給与該当|
|C04_CDE|NVARCHAR2||是|規定改正による単価改訂コード|
|C04_NME|NVARCHAR2||是|規定改正による単価改訂|
|C05_CDE|NVARCHAR2||是|任期途中見直し該当コード|
|C05_NME|NVARCHAR2||是|任期途中見直し該当|
|C06_CDE|NVARCHAR2||是|null|
|C06_NME|NVARCHAR2||是|null|
|C07_CDE|NVARCHAR2||是|null|
|C07_NME|NVARCHAR2||是|null|
|C08_CDE|NVARCHAR2||是|null|
|C08_NME|NVARCHAR2||是|null|
|C09_CDE|NVARCHAR2||是|null|
|C09_NME|NVARCHAR2||是|null|
|C10_CDE|NVARCHAR2||是|null|
|C10_NME|NVARCHAR2||是|null|
|C01_DTE|NVARCHAR2||是|単価改訂日|
|D01_DTE|DATE||是|null|
|C02_DTE|NVARCHAR2||是|当初雇用年月日|
|D02_DTE|DATE||是|null|
|C03_DTE|NVARCHAR2||是|null|
|D03_DTE|DATE||是|null|
|C04_DTE|NVARCHAR2||是|null|
|D04_DTE|DATE||是|null|
|C05_DTE|NVARCHAR2||是|null|
|D05_DTE|DATE||是|null|
|ADRSS|NVARCHAR2||是|住所                                                           |
|CYUBIN|NVARCHAR2||是|郵便番号                                                       |
|ZAIRYU_CDE|NVARCHAR2||是|在留資格コード|
|ZAIRYU_NME|NVARCHAR2||是|在留資格|
|ZAIRYU_KGN_DTE|NVARCHAR2||是|在留期限|
|DZAIRYU_KGN_DTE|DATE||是|在留期限（西暦）|
|SIKAKUGAI_DTE|NVARCHAR2||是|資格外活動許可の期限日|
|DSIKAKUGAI_DTE|DATE||是|資格外活動許可の期限日（西暦）|
|CCOMPKB|VARCHAR2||是|会社区分                                                        |
|DSTART|DATE||否|開始日                                                         |
|SSTART|VARCHAR2||是|開始日（和暦）                                                     |
|DEND|DATE||是|終了日                                                         |
|SEND|VARCHAR2||是|終了日（和暦）                                                     |
|CSHAINNO|VARCHAR2||否|職員番号                                                        |
|KEY_DTE|VARCHAR2||是|基準年月日                                                       |
|DKEY_DTE|DATE||是|基準年月日（西暦）                                                   |
|CNAMEKNA|NVARCHAR2||是|カナ氏名                                                        |
|CNAMEKNJ|NVARCHAR2||是|漢字氏名                                                        |
|TSUXHI_KJ_NME|VARCHAR2||是|通知書用漢字氏名                                                    |
|INSEI_CDE|VARCHAR2||是|院生区分                          京都追加                          |
|INSEI_NME|VARCHAR2||是|院生区分名称                        京都追加                          |
|SEIBETU_KBN|VARCHAR2||是|性別区分                                                        |
|SEIBETU_NME|NVARCHAR2||是|性別                                                          |
|BIRTH_DTE|VARCHAR2||是|生年月日                                                        |
|DBIRTH_DTE|DATE||是|生年月日（西暦）                                                    |
|AGE|NUMBER||是|年齢                                                          |
|KYUSEI_NME|VARCHAR2||是|旧姓                                                          |
|KAISEI_DTE|VARCHAR2||是|改姓年月日                                                       |
|DKAISEI_DTE|DATE||是|改姓年月日（西暦）                                                   |
|HONSEKI_CDE|VARCHAR2||是|本籍コード                                                       |
|HONSEKI_NME|VARCHAR2||是|本籍                                                          |
|KOKUSEKI_CDE|NVARCHAR2||是|国籍区分コード                                                     |
|KOKUSEKI_NME|NVARCHAR2||是|国籍区分                                                        |
|HJKSYK_NNMN_CDE|NVARCHAR2||是|非常勤職員任免コード                                                  |
|HJKSYK_NNMN_NME|NVARCHAR2||是|非常勤職員任免                                                     |
|NINYO_DTE|NVARCHAR2||是|任用年月日                                                       |
|DNINYO_DTE|DATE||是|任用年月日（西暦）                                                   |
|NNKI_MR_DTE|NVARCHAR2||是|任期満了年月日                                                     |
|DNNKI_MR_DTE|DATE||是|任期満了年月日（西暦）                                                 |
|TSYK_DTE|NVARCHAR2||是|退職等年月日                                                      |
|DTSYK_DTE|DATE||是|退職等年月日（西暦）                                                  |
|HJKSYK_SKIN_CDE|NVARCHAR2||是|非常勤職員職員コード                                                  |
|HJKSYK_SKIN_NME|NVARCHAR2||是|非常勤職員職員                                                     |
|HTREI_SYKIN_DTE|NVARCHAR2||是|発令年月日（職員区分）                                                 |
|DHTREI_SYKIN_DTE|DATE||是|発令年月日（職員区分）                                                 |
|HJKSYK_MISY_CDE|NVARCHAR2||是|非常勤職員名称コード                                                  |
|HJKSYK_MISY_NME|VARCHAR2||是|非常勤職員名称                                                     |
|SYKSY_CDE|NVARCHAR2||是|職種コード                                                       |
|SYKSY_NME|NVARCHAR2||是|職種                                                          |
|HTREI_SYKSY_DTE|NVARCHAR2||是|発令年月日（職種）                                                   |
|DHTREI_SYKSY_DTE|DATE||是|発令年月日（職種） 西暦                                                |
