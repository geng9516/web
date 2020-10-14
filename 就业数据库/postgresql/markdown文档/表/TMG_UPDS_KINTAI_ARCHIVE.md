# [勤怠]月次集計出力イメージ(UPDS連携用、過去データ退避                              (TMG_UPDS_KINTAI_ARCHIVE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TUK_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TUK_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TUK_CEMPLOYEEID|varchar||否|職員番号                                                                                      |
|TUK_DSTARTDATE|date||否|開始日                                                                                       |
|TUK_DENDDATE|date||否|終了日                                                                                       |
|TUK_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TUK_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TUK_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TUK_NSEQ|numeric||否|履歴ｼｰｹﾝｽ                       遡及計算用、CSV出力時の連番とは異なるので注意                                    |
|TUK_CDATA1|varchar||是|連番                            CSV出力時に計算、実データとしては登録しない                                     |
|TUK_CDATA2|varchar||是|年月                            YYYYMM形式                                                    |
|TUK_CDATA3|varchar||是|（将来拡張用）                                                                                   |
|TUK_CDATA4|varchar||是|個人番号                                                                                      |
|TUK_CDATA5|varchar||是|超勤１ 予算項                                                                                   |
|TUK_CDATA6|varchar||是|超125                                                                                      |
|TUK_CDATA7|varchar||是|超135                                                                                      |
|TUK_CDATA8|varchar||是|超150                                                                                      |
|TUK_CDATA9|varchar||是|超160                                                                                      |
|TUK_CDATA10|varchar||是|超夜                                                                                        |
|TUK_CDATA11|varchar||是|超休                                                                                        |
|TUK_CDATA12|varchar||是|超勤２ 予算項                                                                                   |
|TUK_CDATA13|varchar||是|超125                                                                                      |
|TUK_CDATA14|varchar||是|超135                                                                                      |
|TUK_CDATA15|varchar||是|超150                                                                                      |
|TUK_CDATA16|varchar||是|超160                                                                                      |
|TUK_CDATA17|varchar||是|超夜                                                                                        |
|TUK_CDATA18|varchar||是|超休                                                                                        |
|TUK_CDATA19|varchar||是|超勤３ 予算項                                                                                   |
|TUK_CDATA20|varchar||是|超125                                                                                      |
|TUK_CDATA21|varchar||是|超135                                                                                      |
|TUK_CDATA22|varchar||是|超150                                                                                      |
|TUK_CDATA23|varchar||是|超160                                                                                      |
|TUK_CDATA24|varchar||是|超夜                                                                                        |
|TUK_CDATA25|varchar||是|超休                                                                                        |
|TUK_CDATA26|varchar||是|宿直１ 予算項                                                                                   |
|TUK_CDATA27|varchar||是|区分                                                                                        |
|TUK_CDATA28|varchar||是|回数                                                                                        |
|TUK_CDATA29|varchar||是|宿直２ 予算項                                                                                   |
|TUK_CDATA30|varchar||是|区分                                                                                        |
|TUK_CDATA31|varchar||是|回数                                                                                        |
|TUK_CDATA32|varchar||是|宿直３ 予算項                                                                                   |
|TUK_CDATA33|varchar||是|区分                                                                                        |
|TUK_CDATA34|varchar||是|回数                                                                                        |
|TUK_CDATA35|varchar||是|宿直４ 予算項                                                                                   |
|TUK_CDATA36|varchar||是|区分                                                                                        |
|TUK_CDATA37|varchar||是|回数                                                                                        |
|TUK_CDATA38|varchar||是|特勤１ 予算項                                                                                   |
|TUK_CDATA39|varchar||是|区分                                                                                        |
|TUK_CDATA40|varchar||是|回数                                                                                        |
|TUK_CDATA41|varchar||是|特勤２ 予算項                                                                                   |
|TUK_CDATA42|varchar||是|区分                                                                                        |
|TUK_CDATA43|varchar||是|回数                                                                                        |
|TUK_CDATA44|varchar||是|特勤３ 予算項                                                                                   |
|TUK_CDATA45|varchar||是|区分                                                                                        |
|TUK_CDATA46|varchar||是|回数                                                                                        |
|TUK_CDATA47|varchar||是|特勤４ 予算項                                                                                   |
|TUK_CDATA48|varchar||是|区分                                                                                        |
|TUK_CDATA49|varchar||是|回数                                                                                        |
|TUK_CDATA50|varchar||是|特勤５ 予算項                                                                                   |
|TUK_CDATA51|varchar||是|区分                                                                                        |
|TUK_CDATA52|varchar||是|回数                                                                                        |
|TUK_CDATA53|varchar||是|特勤６ 予算項                                                                                   |
|TUK_CDATA54|varchar||是|区分                                                                                        |
|TUK_CDATA55|varchar||是|回数                                                                                        |
|TUK_CDATA56|varchar||是|特勤７ 予算項                                                                                   |
|TUK_CDATA57|varchar||是|区分                                                                                        |
|TUK_CDATA58|varchar||是|回数                                                                                        |
|TUK_CDATA59|varchar||是|特勤８ 予算項                                                                                   |
|TUK_CDATA60|varchar||是|区分                                                                                        |
|TUK_CDATA61|varchar||是|回数                                                                                        |
|TUK_CDATA62|varchar||是|勤務 日数                                                                                     |
|TUK_CDATA63|varchar||是|勤務 時数                                                                                     |
|TUK_CDATA64|varchar||是|超100 時間                                                                                   |
|TUK_CDATA65|varchar||是|時給者 勤務日数                                                                                  |
|TUK_CDATA66|varchar||是|欠勤日数                                                                                      |
|TUK_CDATA67|varchar||是|減額時間                                                                                      |
|TUK_CDATA68|varchar||是|超勤１ 予算項                                                                                   |
|TUK_CDATA69|varchar||是|超125                                                                                      |
|TUK_CDATA70|varchar||是|超135                                                                                      |
|TUK_CDATA71|varchar||是|超150                                                                                      |
|TUK_CDATA72|varchar||是|超160                                                                                      |
|TUK_CDATA73|varchar||是|超夜                                                                                        |
|TUK_CDATA74|varchar||是|超休                                                                                        |
|TUK_CDATA75|varchar||是|超勤２ 予算項                                                                                   |
|TUK_CDATA76|varchar||是|超125                                                                                      |
|TUK_CDATA77|varchar||是|超135                                                                                      |
|TUK_CDATA78|varchar||是|超150                                                                                      |
|TUK_CDATA79|varchar||是|超160                                                                                      |
|TUK_CDATA80|varchar||是|超夜                                                                                        |
|TUK_CDATA81|varchar||是|超休                                                                                        |
|TUK_CDATA82|varchar||是|超勤３ 予算項                                                                                   |
|TUK_CDATA83|varchar||是|超125                                                                                      |
|TUK_CDATA84|varchar||是|超135                                                                                      |
|TUK_CDATA85|varchar||是|超150                                                                                      |
|TUK_CDATA86|varchar||是|超160                                                                                      |
|TUK_CDATA87|varchar||是|超夜                                                                                        |
|TUK_CDATA88|varchar||是|超休                                                                                        |
|TUK_CDATA89|varchar||是|勤務 日数                                                                                     |
|TUK_CDATA90|varchar||是|勤務 時数                                                                                     |
|TUK_CDATA91|varchar||是|超100 時間                                                                                   |
|TUK_CDATA92|varchar||是|欠勤日数                                                                                      |
|TUK_CDATA93|varchar||是|減額時間                                                                                      |
|TUK_CDATA94|varchar||是|拡張 超勤2 100                                                                                |
|TUK_CDATA95|varchar||是|超勤3 100                                                                                   |
|TUK_CDATA96|varchar||是|日割前超勤1 予算項                                                                                |
|TUK_CDATA97|varchar||是|超125                                                                                      |
|TUK_CDATA98|varchar||是|超135                                                                                      |
|TUK_CDATA99|varchar||是|超150                                                                                      |
|TUK_CDATA100|varchar||是|超160                                                                                      |
|TUK_CDATA101|varchar||是|超夜                                                                                        |
|TUK_CDATA102|varchar||是|超休                                                                                        |
|TUK_CDATA103|varchar||是|日割前超勤2 予算項                                                                                |
|TUK_CDATA104|varchar||是|超125                                                                                      |
|TUK_CDATA105|varchar||是|超135                                                                                      |
|TUK_CDATA106|varchar||是|超150                                                                                      |
|TUK_CDATA107|varchar||是|超160                                                                                      |
|TUK_CDATA108|varchar||是|超夜                                                                                        |
|TUK_CDATA109|varchar||是|超休                                                                                        |
|TUK_CDATA110|varchar||是|日割前超勤3 予算項                                                                                |
|TUK_CDATA111|varchar||是|超125                                                                                      |
|TUK_CDATA112|varchar||是|超135                                                                                      |
|TUK_CDATA113|varchar||是|超150                                                                                      |
|TUK_CDATA114|varchar||是|超160                                                                                      |
|TUK_CDATA115|varchar||是|超夜                                                                                        |
|TUK_CDATA116|varchar||是|超休                                                                                        |
|TUK_CDATA117|varchar||是|その他 勤務日数                                                                                  |
|TUK_CDATA118|varchar||是|勤務 時数                                                                                     |
|TUK_CDATA119|varchar||是|超100 時間                                                                                   |
|TUK_CDATA120|varchar||是|欠勤日数                                                                                      |
|TUK_CDATA121|varchar||是|減額時間                                                                                      |
|TUK_CDATA122|varchar||是|日割前（単2）超勤1 予算項                                                                            |
|TUK_CDATA123|varchar||是|超125                                                                                      |
|TUK_CDATA124|varchar||是|超135                                                                                      |
|TUK_CDATA125|varchar||是|超150                                                                                      |
|TUK_CDATA126|varchar||是|超160                                                                                      |
|TUK_CDATA127|varchar||是|超夜                                                                                        |
|TUK_CDATA128|varchar||是|超休                                                                                        |
|TUK_CDATA129|varchar||是|日割前（単2）超勤2 予算項                                                                            |
|TUK_CDATA130|varchar||是|超125                                                                                      |
|TUK_CDATA131|varchar||是|超135                                                                                      |
|TUK_CDATA132|varchar||是|超150                                                                                      |
|TUK_CDATA133|varchar||是|超160                                                                                      |
|TUK_CDATA134|varchar||是|超夜                                                                                        |
|TUK_CDATA135|varchar||是|超休                                                                                        |
|TUK_CDATA136|varchar||是|日割前（単2）超勤3 予算項                                                                            |
|TUK_CDATA137|varchar||是|超125                                                                                      |
|TUK_CDATA138|varchar||是|超135                                                                                      |
|TUK_CDATA139|varchar||是|超150                                                                                      |
|TUK_CDATA140|varchar||是|超160                                                                                      |
|TUK_CDATA141|varchar||是|超夜                                                                                        |
|TUK_CDATA142|varchar||是|超休                                                                                        |
|TUK_CDATA143|varchar||是|その他 勤務 日数                                                                                 |
|TUK_CDATA144|varchar||是|勤務 時数                                                                                     |
|TUK_CDATA145|varchar||是|超100 時間                                                                                   |
|TUK_CDATA146|varchar||是|欠勤日数                                                                                      |
|TUK_CDATA147|varchar||是|減額時間                                                                                      |
|TUK_CDATA148|varchar||是|日割前拡張 超勤2 100                                                                             |
|TUK_CDATA149|varchar||是|超勤3 100                                                                                   |
|TUK_CDATA150|varchar||是|超勤175(単価1/予算項1)                                                                                 |
|TUK_CDATA151|varchar||是|超勤175(単価1/予算項2)                                                                                 |
|TUK_CDATA152|varchar||是|超勤175(単価1/予算項3)                                                                                 |
|TUK_CDATA153|varchar||是|超勤175(単価2/予算項1)                                                                                 |
|TUK_CDATA154|varchar||是|超勤175(単価2/予算項2)                                                                                 |
|TUK_CDATA155|varchar||是|超勤175(単価2/予算項3)                                                                                 |
|TUK_CDATA156|varchar||是|日割前・超勤175(単価1/予算項1)                                                                             |
|TUK_CDATA157|varchar||是|日割前・超勤175(単価1/予算項2)                                                                             |
|TUK_CDATA158|varchar||是|日割前・超勤175(単価1/予算項3)                                                                             |
|TUK_CDATA159|varchar||是|日割前・超勤175(単価2/予算項1)                                                                             |
|TUK_CDATA160|varchar||是|日割前・超勤175(単価2/予算項2)                                                                             |
|TUK_CDATA161|varchar||是|日割前・超勤175(単価2/予算項3)                                                                             |
|TUK_CDATA162|varchar||是|null|
|TUK_CDATA163|varchar||是|null|
|TUK_CDATA164|varchar||是|null|
|TUK_CDATA165|varchar||是|null|
|TUK_CDATA166|varchar||是|null|
|TUK_CDATA167|varchar||是|null|
|TUK_CDATA168|varchar||是|null|
|TUK_CDATA169|varchar||是|null|
|TUK_CDATA170|varchar||是|null|
|TUK_CDATA171|varchar||是|null|
|TUK_CDATA172|varchar||是|null|
|TUK_CDATA173|varchar||是|null|
|TUK_CDATA174|varchar||是|null|
|TUK_CDATA175|varchar||是|null|
|TUK_CDATA176|varchar||是|null|
|TUK_CDATA177|varchar||是|null|
|TUK_CDATA178|varchar||是|null|
|TUK_CDATA179|varchar||是|null|
|TUK_CDATA180|varchar||是|null|
|TUK_CDATA181|varchar||是|null|
|TUK_CDATA182|varchar||是|null|
|TUK_CDATA183|varchar||是|null|
|TUK_CDATA184|varchar||是|null|
|TUK_CDATA185|varchar||是|null|
|TUK_CDATA186|varchar||是|null|
|TUK_CDATA187|varchar||是|null|
|TUK_CDATA188|varchar||是|null|
|TUK_CDATA189|varchar||是|null|
|TUK_CDATA190|varchar||是|null|
|TUK_CDATA191|varchar||是|null|
|TUK_CDATA192|varchar||是|null|
|TUK_CDATA193|varchar||是|null|
|TUK_CDATA194|varchar||是|null|
|TUK_CDATA195|varchar||是|null|
|TUK_CDATA196|varchar||是|null|
|TUK_CDATA197|varchar||是|null|
|TUK_CDATA198|varchar||是|null|
|TUK_CDATA199|varchar||是|null|
|TUK_CDATA200|varchar||是|null|
