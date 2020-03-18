# [勤怠]月次集計出力イメージ(UPDS連携用、過去データ退避                              (TMG_UPDS_KINTAI_ARCHIVE)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TUK_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TUK_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TUK_CEMPLOYEEID|VARCHAR2||否|社員番号                                                                                      |
|TUK_DSTARTDATE|DATE||否|開始日                                                                                       |
|TUK_DENDDATE|DATE||否|終了日                                                                                       |
|TUK_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TUK_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TUK_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TUK_NSEQ|NUMBER||否|履歴ｼｰｹﾝｽ                       遡及計算用、CSV出力時の連番とは異なるので注意                                    |
|TUK_CDATA1|VARCHAR2||是|連番                            CSV出力時に計算、実データとしては登録しない                                     |
|TUK_CDATA2|VARCHAR2||是|年月                            YYYYMM形式                                                    |
|TUK_CDATA3|VARCHAR2||是|（将来拡張用）                                                                                   |
|TUK_CDATA4|VARCHAR2||是|個人番号                                                                                      |
|TUK_CDATA5|VARCHAR2||是|超勤１ 予算項                                                                                   |
|TUK_CDATA6|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA7|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA8|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA9|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA10|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA11|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA12|VARCHAR2||是|超勤２ 予算項                                                                                   |
|TUK_CDATA13|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA14|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA15|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA16|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA17|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA18|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA19|VARCHAR2||是|超勤３ 予算項                                                                                   |
|TUK_CDATA20|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA21|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA22|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA23|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA24|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA25|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA26|VARCHAR2||是|宿直１ 予算項                                                                                   |
|TUK_CDATA27|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA28|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA29|VARCHAR2||是|宿直２ 予算項                                                                                   |
|TUK_CDATA30|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA31|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA32|VARCHAR2||是|宿直３ 予算項                                                                                   |
|TUK_CDATA33|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA34|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA35|VARCHAR2||是|宿直４ 予算項                                                                                   |
|TUK_CDATA36|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA37|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA38|VARCHAR2||是|特勤１ 予算項                                                                                   |
|TUK_CDATA39|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA40|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA41|VARCHAR2||是|特勤２ 予算項                                                                                   |
|TUK_CDATA42|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA43|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA44|VARCHAR2||是|特勤３ 予算項                                                                                   |
|TUK_CDATA45|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA46|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA47|VARCHAR2||是|特勤４ 予算項                                                                                   |
|TUK_CDATA48|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA49|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA50|VARCHAR2||是|特勤５ 予算項                                                                                   |
|TUK_CDATA51|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA52|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA53|VARCHAR2||是|特勤６ 予算項                                                                                   |
|TUK_CDATA54|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA55|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA56|VARCHAR2||是|特勤７ 予算項                                                                                   |
|TUK_CDATA57|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA58|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA59|VARCHAR2||是|特勤８ 予算項                                                                                   |
|TUK_CDATA60|VARCHAR2||是|区分                                                                                        |
|TUK_CDATA61|VARCHAR2||是|回数                                                                                        |
|TUK_CDATA62|VARCHAR2||是|勤務 日数                                                                                     |
|TUK_CDATA63|VARCHAR2||是|勤務 時数                                                                                     |
|TUK_CDATA64|VARCHAR2||是|超100 時間                                                                                   |
|TUK_CDATA65|VARCHAR2||是|時給者 勤務日数                                                                                  |
|TUK_CDATA66|VARCHAR2||是|欠勤日数                                                                                      |
|TUK_CDATA67|VARCHAR2||是|減額時間                                                                                      |
|TUK_CDATA68|VARCHAR2||是|超勤１ 予算項                                                                                   |
|TUK_CDATA69|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA70|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA71|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA72|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA73|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA74|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA75|VARCHAR2||是|超勤２ 予算項                                                                                   |
|TUK_CDATA76|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA77|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA78|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA79|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA80|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA81|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA82|VARCHAR2||是|超勤３ 予算項                                                                                   |
|TUK_CDATA83|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA84|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA85|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA86|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA87|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA88|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA89|VARCHAR2||是|勤務 日数                                                                                     |
|TUK_CDATA90|VARCHAR2||是|勤務 時数                                                                                     |
|TUK_CDATA91|VARCHAR2||是|超100 時間                                                                                   |
|TUK_CDATA92|VARCHAR2||是|欠勤日数                                                                                      |
|TUK_CDATA93|VARCHAR2||是|減額時間                                                                                      |
|TUK_CDATA94|VARCHAR2||是|拡張 超勤2 100                                                                                |
|TUK_CDATA95|VARCHAR2||是|超勤3 100                                                                                   |
|TUK_CDATA96|VARCHAR2||是|日割前超勤1 予算項                                                                                |
|TUK_CDATA97|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA98|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA99|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA100|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA101|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA102|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA103|VARCHAR2||是|日割前超勤2 予算項                                                                                |
|TUK_CDATA104|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA105|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA106|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA107|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA108|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA109|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA110|VARCHAR2||是|日割前超勤3 予算項                                                                                |
|TUK_CDATA111|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA112|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA113|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA114|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA115|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA116|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA117|VARCHAR2||是|その他 勤務日数                                                                                  |
|TUK_CDATA118|VARCHAR2||是|勤務 時数                                                                                     |
|TUK_CDATA119|VARCHAR2||是|超100 時間                                                                                   |
|TUK_CDATA120|VARCHAR2||是|欠勤日数                                                                                      |
|TUK_CDATA121|VARCHAR2||是|減額時間                                                                                      |
|TUK_CDATA122|VARCHAR2||是|日割前（単2）超勤1 予算項                                                                            |
|TUK_CDATA123|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA124|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA125|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA126|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA127|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA128|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA129|VARCHAR2||是|日割前（単2）超勤2 予算項                                                                            |
|TUK_CDATA130|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA131|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA132|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA133|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA134|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA135|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA136|VARCHAR2||是|日割前（単2）超勤3 予算項                                                                            |
|TUK_CDATA137|VARCHAR2||是|超125                                                                                      |
|TUK_CDATA138|VARCHAR2||是|超135                                                                                      |
|TUK_CDATA139|VARCHAR2||是|超150                                                                                      |
|TUK_CDATA140|VARCHAR2||是|超160                                                                                      |
|TUK_CDATA141|VARCHAR2||是|超夜                                                                                        |
|TUK_CDATA142|VARCHAR2||是|超休                                                                                        |
|TUK_CDATA143|VARCHAR2||是|その他 勤務 日数                                                                                 |
|TUK_CDATA144|VARCHAR2||是|勤務 時数                                                                                     |
|TUK_CDATA145|VARCHAR2||是|超100 時間                                                                                   |
|TUK_CDATA146|VARCHAR2||是|欠勤日数                                                                                      |
|TUK_CDATA147|VARCHAR2||是|減額時間                                                                                      |
|TUK_CDATA148|VARCHAR2||是|日割前拡張 超勤2 100                                                                             |
|TUK_CDATA149|VARCHAR2||是|超勤3 100                                                                                   |
|TUK_CDATA150|VARCHAR2||是|超勤175(単価1/予算項1)                                                                                 |
|TUK_CDATA151|VARCHAR2||是|超勤175(単価1/予算項2)                                                                                 |
|TUK_CDATA152|VARCHAR2||是|超勤175(単価1/予算項3)                                                                                 |
|TUK_CDATA153|VARCHAR2||是|超勤175(単価2/予算項1)                                                                                 |
|TUK_CDATA154|VARCHAR2||是|超勤175(単価2/予算項2)                                                                                 |
|TUK_CDATA155|VARCHAR2||是|超勤175(単価2/予算項3)                                                                                 |
|TUK_CDATA156|VARCHAR2||是|日割前・超勤175(単価1/予算項1)                                                                             |
|TUK_CDATA157|VARCHAR2||是|日割前・超勤175(単価1/予算項2)                                                                             |
|TUK_CDATA158|VARCHAR2||是|日割前・超勤175(単価1/予算項3)                                                                             |
|TUK_CDATA159|VARCHAR2||是|日割前・超勤175(単価2/予算項1)                                                                             |
|TUK_CDATA160|VARCHAR2||是|日割前・超勤175(単価2/予算項2)                                                                             |
|TUK_CDATA161|VARCHAR2||是|日割前・超勤175(単価2/予算項3)                                                                             |
|TUK_CDATA162|VARCHAR2||是|null|
|TUK_CDATA163|VARCHAR2||是|null|
|TUK_CDATA164|VARCHAR2||是|null|
|TUK_CDATA165|VARCHAR2||是|null|
|TUK_CDATA166|VARCHAR2||是|null|
|TUK_CDATA167|VARCHAR2||是|null|
|TUK_CDATA168|VARCHAR2||是|null|
|TUK_CDATA169|VARCHAR2||是|null|
|TUK_CDATA170|VARCHAR2||是|null|
|TUK_CDATA171|VARCHAR2||是|null|
|TUK_CDATA172|VARCHAR2||是|null|
|TUK_CDATA173|VARCHAR2||是|null|
|TUK_CDATA174|VARCHAR2||是|null|
|TUK_CDATA175|VARCHAR2||是|null|
|TUK_CDATA176|VARCHAR2||是|null|
|TUK_CDATA177|VARCHAR2||是|null|
|TUK_CDATA178|VARCHAR2||是|null|
|TUK_CDATA179|VARCHAR2||是|null|
|TUK_CDATA180|VARCHAR2||是|null|
|TUK_CDATA181|VARCHAR2||是|null|
|TUK_CDATA182|VARCHAR2||是|null|
|TUK_CDATA183|VARCHAR2||是|null|
|TUK_CDATA184|VARCHAR2||是|null|
|TUK_CDATA185|VARCHAR2||是|null|
|TUK_CDATA186|VARCHAR2||是|null|
|TUK_CDATA187|VARCHAR2||是|null|
|TUK_CDATA188|VARCHAR2||是|null|
|TUK_CDATA189|VARCHAR2||是|null|
|TUK_CDATA190|VARCHAR2||是|null|
|TUK_CDATA191|VARCHAR2||是|null|
|TUK_CDATA192|VARCHAR2||是|null|
|TUK_CDATA193|VARCHAR2||是|null|
|TUK_CDATA194|VARCHAR2||是|null|
|TUK_CDATA195|VARCHAR2||是|null|
|TUK_CDATA196|VARCHAR2||是|null|
|TUK_CDATA197|VARCHAR2||是|null|
|TUK_CDATA198|VARCHAR2||是|null|
|TUK_CDATA199|VARCHAR2||是|null|
|TUK_CDATA200|VARCHAR2||是|null|
