# プラガブル種別マスタ(TMG_PLUGGABLE_ROOT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPLR_CCUSTOMERID|varchar||否|顧客コード|
|TPLR_CCOMPANYID|varchar||否|法人コード|
|TPLR_CPHASE|varchar||否|処理フェーズ|
|TPLR_DSTARTDATE|date||否|データ開始日|
|TPLR_DENDDATE|date||否|データ終了日|
|TPLR_CMODIFIERUSERID|varchar||是|更新者|
|TPLR_DMODIFIEDDATE|date||是|更新日|
|TPLR_CMODIFIERPROGRAMID|varchar||是|更新プログラムID|
|TPLR_CPLUGGABLETYPE|varchar||是|カラム「TPL_CWORKTYPEID」の扱い 「*(一律)」「対象者の身分」「コード直接指定」|
