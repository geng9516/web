# [勤怠]入社取消用バックアップ・日別情報(TMG_BAK_DAILY)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TDA_DCREATE|date||是|作成日|
|TDA_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ固定：01 |
|TDA_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ|
|TDA_CEMPLOYEEID|varchar||否|社員番号|
|TDA_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日 固定：1900/01/01 |
|TDA_DENDDATE|date||否|ﾃﾞｰﾀ終了日 固定：2222/12/31 |
|TDA_CMODIFIERUSERID|varchar||是|更新者 |
|TDA_DMODIFIEDDATE|date||是|更新日 |
|TDA_CMODIFIERPROGRAMID|varchar||是|更新プログラムID |
|TDA_NYYYY|numeric||否|該当年 YYYY|
|TDA_DYYYYMM|date||否|該当年月YYYY/MM/01|
|TDA_DYYYYMMDD|date||否|勤務年月日 YYYY/MM/DD|
|TDA_CSTATUSFLG|varchar||是|ステータスフラグMGD:TMG_DATASTATUS|
|TDA_CNTFSTATUSFLG|varchar||是|申請ステータスフラグMGD:TMG_NTFSTATUS |
|TDA_CERRCODE|varchar||是|エラーコードMGD:TMG_ERRCODE |
|TDA_CERRMSG|varchar||是|エラーメッセージ|
|TDA_NOPEN_TP|numeric||是|[打刻]始業時刻300～1740(朝5:00～翌朝5:00)|
|TDA_NCLOSE_TP|numeric||是|[打刻]終業時刻|
|TDA_CHOLFLG|varchar||是|休日フラグ MGD:TMG_HOLFLG|
|TDA_CWORKINGID_P|varchar||是|[予定]就業区分MGD:TMG_WORK|
|TDA_CWORKINGNAME_P|varchar||是|[予定]就業名称|
|TDA_NOPEN_P|numeric||是|[予定]始業時刻|
|TDA_NCLOSE_P|numeric||是|[予定]終業時刻|
|TDA_NRESTOPEN_P|numeric||是|[予定]休憩開始時刻|
|TDA_NRESTCLOSE_P|numeric||是|[予定]休憩終了時刻|
|TDA_CMODIFIERUSERID_P|varchar||是|[予定]更新者 |
|TDA_DMODIFIEDDATE_P|date||是|[予定]更新日時|
|TDA_NLOCK_P|numeric||是|[予定]予定作成ロック 連続した終日休暇の間の休日は予定作成で編集不可にする|
|TDA_NREST45_P|numeric||是|[予定]休憩45分選択 休憩45分選択を反映するかどうかの判定に使用。 |
|TDA_NOPEN_N|numeric||是|[申請反映]始業時刻|
|TDA_NCLOSE_N|numeric||是|[申請反映]終業時刻|
|TDA_NRESTOPEN_N|numeric||是|[申請反映]休憩開始時刻|
|TDA_NRESTCLOSE_N|numeric||是|[申請反映]休憩終了時刻|
|TDA_CMODIFIERUSERID_N|varchar||是|[申請反映]更新者 |
|TDA_DMODIFIEDDATE_N|date||是|[申請反映]更新日時|
|TDA_NOPEN_O|numeric||是|[超過勤務]開始時刻|
|TDA_NCLOSE_O|numeric||是|[超過勤務]終了時刻|
|TDA_CCOMMENT_O|varchar||是|[超過勤務]指示コメント|
|TDA_CMODIFIERUSERID_O|varchar||是|[超過勤務]更新者 |
|TDA_DMODIFIEDDATE_O|date||是|[超過勤務]更新日時|
|TDA_CWORKINGID_R|varchar||是|[実績]就業区分MGD:TMG_WORK|
|TDA_CWORKINGNAME_R|varchar||是|[実績]就業名称|
|TDA_NOPEN_R|numeric||是|[実績]始業時刻|
|TDA_NCLOSE_R|numeric||是|[実績]終業時刻|
|TDA_NRESTOPEN_R|numeric||是|[実績]休憩開始時刻|
|TDA_NRESTCLOSE_R|numeric||是|[実績]休憩終了時刻|
|TDA_COWNCOMMENT_R|varchar||是|[実績]本人コメント|
|TDA_CBOSSCOMMENT_R|varchar||是|[実績]承認者コメント |
|TDA_CMODIFIERUSERID_R|varchar||是|[実績]更新者 |
|TDA_DMODIFIEDDATE_R|date||是|[実績]更新日時|
|TDA_CMESSAGE|varchar||是|[実績]部分休業メッセージ 勤怠登録画面の｢備考｣表示用。TMG_BAK_DAILY_DETAI|
|TDA_NCALC01|numeric||是|[計算]項目１超勤(125/100)|
|TDA_NCALC02|numeric||是|[計算]項目２休日(135/100)|
|TDA_NCALC03|numeric||是|[計算]項目３超勤(150/100)|
|TDA_NCALC04|numeric||是|[計算]項目４休日(160/100)|
|TDA_NCALC05|numeric||是|[計算]項目５夜勤(25/100)|
|TDA_NCALC06|numeric||是|[計算]項目６休日(135/100)|
|TDA_NCALC07|numeric||是|[計算]項目７勤務日数|
|TDA_NCALC08|numeric||是|[計算]項目８勤務時間数|
|TDA_NCALC09|numeric||是|[計算]項目９翌日：勤務時間数|
|TDA_NCALC10|numeric||是|[計算]項目１０翌日：欠勤：時間|
|TDA_NCALC11|numeric||是|[計算]項目１１欠勤：日|
|TDA_NCALC12|numeric||是|[計算]項目１２欠勤：時間|
|TDA_NCALC13|numeric||是|[計算]項目１３給与用：勤務日数|
|TDA_NCALC14|numeric||是|[計算]項目１４給与用：勤務時間数|
|TDA_NCALC15|numeric||是|[計算]項目１５遅刻回数|
|TDA_NCALC16|numeric||是|[計算]項目１６早退回数|
|TDA_NCALC17|numeric||是|[計算]項目１７休出日数|
|TDA_NCALC18|numeric||是|[計算]項目１８翌日：超勤(150/100)|
|TDA_NCALC19|numeric||是|[計算]項目１９翌日：休日(160/100)|
|TDA_NCALC20|numeric||是|[計算]項目２０翌日：夜勤(25/100)|
|TDA_NCALC21|numeric||是|[計算]項目２１翌日：給与用：勤務時間数|
|TDA_NCALC22|numeric||是|[計算]項目２２超勤(100/100)|
|TDA_NCALC23|numeric||是|[計算]項目２３翌日：超勤(100/100)|
|TDA_NCALC24|numeric||是|[計算]項目２４翌日：超勤(125/100)|
|TDA_NCALC25|numeric||是|[計算]項目２５翌日：休日(135/100)|
|TDA_NCALC26|numeric||是|[計算]項目２６給与用：減額対象の時間数|
|TDA_NCALC27|numeric||是|[計算]項目２７翌日：給与用：減額対象の時間数|
|TDA_NCALC28|numeric||是|[計算]項目２８所定労働時間数|
|TDA_NCALC29|numeric||是|[計算]項目２９超勤(175/100)|
|TDA_NCALC30|numeric||是|[計算]項目３０翌日：超勤(175/100)|
|TDA_CBUSINESSTRIPID_P|varchar||是|[予定]出張区分|
|TDA_CBUSINESSTRIPID_R|varchar||是|[実績]出張区分|
|TDA_CCOMMENT_P|varchar||是|[予定]コメント|
|TDA_CPATTERNID|varchar||是|勤務パターンID|
|TDA_NCALC31|numeric||是|[計算]項目３１休日(185/100)|
|TDA_NCALC32|numeric||是|[計算]項目３２翌日：休日(185/100)|
|TDA_NCALC33|numeric||是|[計算]項目３３超勤2(100/100)|
|TDA_NCALC34|numeric||是|[計算]項目３４翌日：超勤2(100/100)|
|TDA_NCALC35|numeric||是|[計算]項目３５超勤2(125/100)|
|TDA_NCALC36|numeric||是|[計算]項目３６翌日：超勤2(125/100)|
|TDA_NCALC37|numeric||是|[計算]項目３７超勤2(150/100)|
|TDA_NCALC38|numeric||是|[計算]項目３８翌日：超勤2(150/100)|
|TDA_NCALC39|numeric||是|[計算]項目３９超勤2(175/100)|
|TDA_NCALC40|numeric||是|[計算]項目４０翌日：超勤2(175/100)|
|TDA_NCALC41|numeric||是|[計算]項目４１休日2(135/100)|
|TDA_NCALC42|numeric||是|[計算]項目４２翌日：休日2(135/100)|
|TDA_NCALC43|numeric||是|[計算]項目４３休日2(160/100)|
|TDA_NCALC44|numeric||是|[計算]項目４４翌日：休日2(160/100)|
|TDA_NCALC45|numeric||是|[計算]項目４５休日2(185/100)|
|TDA_NCALC46|numeric||是|[計算]項目４６翌日：休日2(185/100)|
|TDA_NCALC47|numeric||是|[計算]項目４７超勤(125/100)：60hOver|
|TDA_NCALC48|numeric||是|[計算]項目４８翌日：超勤(125/100)：60hOver|
|TDA_NCALC49|numeric||是|[計算]項目４９超勤(150/100)：60hOver|
|TDA_NCALC50|numeric||是|[計算]項目５０翌日：超勤(150/100)：60hOver|
|TDA_NCALC51|numeric||是|[計算]項目５１休日(135/100)：60hOver|
|TDA_NCALC52|numeric||是|[計算]項目５２翌日：休日(135/100)：60hOver|
|TDA_NCALC53|numeric||是|[計算]項目５３休日(160/100)：60hOver|
|TDA_NCALC54|numeric||是|[計算]項目５４翌日：休日(160/100)：60hOver|
|TDA_NCALC55|numeric||是|[計算]項目５５超勤2(125/100)：60hOver|
|TDA_NCALC56|numeric||是|[計算]項目５６翌日：超勤2(125/100)：60hOver|
|TDA_NCALC57|numeric||是|[計算]項目５７超勤2(150/100)：60hOver|
|TDA_NCALC58|numeric||是|[計算]項目５８翌日：超勤2(150/100)：60hOver|
|TDA_NCALC59|numeric||是|[計算]項目５９休日2(135/100)：60hOver|
|TDA_NCALC60|numeric||是|[計算]項目６０翌日：休日2(135/100)：60hOver|
|TDA_NCALC61|numeric||是|[計算]項目６１休日2(160/100)：60hOver|
|TDA_NCALC62|numeric||是|[計算]項目６２翌日：休日2(160/100)：60hOver|
|TDA_NCALC63|numeric||是|[計算]項目６３給与用：超勤(100/100)|
|TDA_NCALC64|numeric||是|[計算]項目６４翌日：給与用：超勤(100/100)|
|TDA_NCALC65|numeric||是|[計算]項目６５給与用：超勤(125/100)|
|TDA_NCALC66|numeric||是|[計算]項目６６翌日：給与用：超勤(125/100)|
|TDA_NCALC67|numeric||是|[計算]項目６７給与用：超勤(150/100)|
|TDA_NCALC68|numeric||是|[計算]項目６８翌日：給与用：超勤(150/100)|
|TDA_NCALC69|numeric||是|[計算]項目６９給与用：超勤(175/100)|
|TDA_NCALC70|numeric||是|[計算]項目７０翌日：給与用：超勤(175/100)|
|TDA_NCALC71|numeric||是|[計算]項目７１給与用：休日(135/100)|
|TDA_NCALC72|numeric||是|[計算]項目７２翌日：給与用：休日(135/100)|
|TDA_NCALC73|numeric||是|[計算]項目７３給与用：休日(160/100)|
|TDA_NCALC74|numeric||是|[計算]項目７４翌日：給与用：休日(160/100)|
|TDA_NCALC75|numeric||是|[計算]項目７５給与用：休日(185/100)|
|TDA_NCALC76|numeric||是|[計算]項目７６翌日：給与用：休日(185/100)|
|TDA_NCALC77|numeric||是|[計算]項目７７給与用：超勤2(100/100)|
|TDA_NCALC78|numeric||是|[計算]項目７８翌日：給与用：超勤2(100/100)|
|TDA_NCALC79|numeric||是|[計算]項目７９給与用：超勤2(125/100)|
|TDA_NCALC80|numeric||是|[計算]項目８０翌日：給与用：超勤2(125/100)|
|TDA_NCALC81|numeric||是|[計算]項目８１給与用：超勤2(150/100)|
|TDA_NCALC82|numeric||是|[計算]項目８２翌日：給与用：超勤2(150/100)|
|TDA_NCALC83|numeric||是|[計算]項目８３給与用：超勤2(175/100)|
|TDA_NCALC84|numeric||是|[計算]項目８４翌日：給与用：超勤2(175/100)|
|TDA_NCALC85|numeric||是|[計算]項目８５給与用：休日2(135/100)|
|TDA_NCALC86|numeric||是|[計算]項目８６翌日：給与用：休日2(135/100)|
|TDA_NCALC87|numeric||是|[計算]項目８７給与用：休日2(160/100)|
|TDA_NCALC88|numeric||是|[計算]項目８８翌日：給与用：休日2(160/100)|
|TDA_NCALC89|numeric||是|[計算]項目８９給与用：休日2(185/100)|
|TDA_NCALC90|numeric||是|[計算]項目９０翌日：給与用：休日2(185/100)|
|TDA_NCALC91|numeric||是|[計算]項目９１年次有給休暇(半休)：回数|
|TDA_NCALC92|numeric||是|[計算]項目９２年次有給休暇：時間|
|TDA_NCALC93|numeric||是|[計算]項目９３特別休暇（有給）：時間|
|TDA_NCALC94|numeric||是|[計算]項目９４翌日：特別休暇（有給）：時間|
|TDA_NCALC95|numeric||是|[計算]項目９５特別休暇（無給）：時間|
|TDA_NCALC96|numeric||是|[計算]項目９６翌日：特別休暇（無給）：時間|
|TDA_NCALC97|numeric||是|[計算]項目９７就業禁止（有給）：時間|
|TDA_NCALC98|numeric||是|[計算]項目９８翌日：就業禁止（有給）：時間|
|TDA_NCALC99|numeric||是|[計算]項目９９就業禁止（無給）：時間|
|TDA_NCALC100|numeric||是|[計算]項目１００翌日：就業禁止（無給）：時間|
|TDA_NCALC101|numeric||是|[計算]項目１０１勤務しないことの承認：時間|
|TDA_NCALC102|numeric||是|[計算]項目１０２翌日：勤務しないことの承認：時間|
|TDA_NCALC103|numeric||是|[計算]項目１０３短時間勤務減額時間数（子の養育）：時間|
|TDA_NCALC104|numeric||是|[計算]項目１０４翌日：短時間勤務減額時間数（子の養育）：時間|
|TDA_NCALC105|numeric||是|[計算]項目１０５短時間勤務減額時間数（家族の介護）：時間|
|TDA_NCALC106|numeric||是|[計算]項目１０６翌日：短時間勤務減額時間数（家族の介護）：時間|
|TDA_NCALC107|numeric||是|[計算]項目１０７出張：時間|
|TDA_NCALC108|numeric||是|[計算]項目１０８翌日：出張：時間|
|TDA_NCALC109|numeric||是|[計算]項目１０９出張(移動日)：時間|
|TDA_NCALC110|numeric||是|[計算]項目１１０翌日：出張(移動日)：時間|
|TDA_NCALC111|numeric||是|[計算]項目１１１外出：時間|
|TDA_NCALC112|numeric||是|[計算]項目１１２翌日：外出：時間|
|TDA_NCALC113|numeric||是|[計算]項目１１３研修：時間|
|TDA_NCALC114|numeric||是|[計算]項目１１４翌日：研修：時間|
|TDA_NCALC115|numeric||是|[計算]項目１１５集計予備|
|TDA_NCALC116|numeric||是|[計算]項目１１６集計予備|
|TDA_NCALC117|numeric||是|[計算]項目１１７集計予備|
|TDA_NCALC118|numeric||是|[計算]項目１１８集計予備|
|TDA_NCALC119|numeric||是|[計算]項目１１９集計予備|
|TDA_NCALC120|numeric||是|[計算]項目１２０集計予備|
|TDA_NCALC121|numeric||是|[計算]項目１２１集計予備|
|TDA_NCALC122|numeric||是|[計算]項目１２２集計予備|
|TDA_NCALC123|numeric||是|[計算]項目１２３集計予備|
|TDA_NCALC124|numeric||是|[計算]項目１２４集計予備|
|TDA_NCALC125|numeric||是|[計算]項目１２５集計予備|
|TDA_NCALC126|numeric||是|[計算]項目１２６集計予備|
|TDA_NCALC127|numeric||是|[計算]項目１２７集計予備|
|TDA_NCALC128|numeric||是|[計算]項目１２８集計予備|
|TDA_NCALC129|numeric||是|[計算]項目１２９集計予備|
|TDA_NCALC130|numeric||是|[計算]項目１３０集計予備|
|TDA_NCALC131|numeric||是|[計算]項目１３１集計予備|
|TDA_NCALC132|numeric||是|[計算]項目１３２集計予備|
|TDA_NCALC133|numeric||是|[計算]項目１３３集計予備|
|TDA_NCALC134|numeric||是|[計算]項目１３４集計予備|
|TDA_NCALC135|numeric||是|[計算]項目１３５集計予備|
|TDA_NCALC136|numeric||是|[計算]項目１３６集計予備|
|TDA_NCALC137|numeric||是|[計算]項目１３７集計予備|
|TDA_NCALC138|numeric||是|[計算]項目１３８集計予備|
|TDA_NCALC139|numeric||是|[計算]項目１３９集計予備|
|TDA_NCALC140|numeric||是|[計算]項目１４０集計予備|
|TDA_NCALC141|numeric||是|[計算]項目１４１集計予備|
|TDA_NCALC142|numeric||是|[計算]項目１４２集計予備|
|TDA_NCALC143|numeric||是|[計算]項目１４３集計予備|
|TDA_NCALC144|numeric||是|[計算]項目１４４集計予備|
|TDA_NCALC145|numeric||是|[計算]項目１４５集計予備|
|TDA_NCALC146|numeric||是|[計算]項目１４６集計予備|
|TDA_NCALC147|numeric||是|[計算]項目１４７集計予備|
|TDA_NCALC148|numeric||是|[計算]項目１４８集計予備|
|TDA_NCALC149|numeric||是|[計算]項目１４９集計予備|
|TDA_NCALC150|numeric||是|[計算]項目１５０集計予備|
|TDA_CREFNTFNO|varchar||是|申請反映元申請番号|
|TDA_CWORKINGID_P_PRE|varchar||是|申請反映前の予定就業区分|
|TDA_DSUBSTITUTED|date||是|振替先（元）の日付|
|TDA_NOPEN_F|numeric||是|[修正実績]始業時刻|
|TDA_NCLOSE_F|numeric||是|[修正実績]終業時刻|
