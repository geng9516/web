# プラガブル種別マスタ(TMG_PLUGGABLE_ROOT)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TPLR_CCUSTOMERID|VARCHAR2||否|顧客コード|
|TPLR_CCOMPANYID|VARCHAR2||否|法人コード|
|TPLR_CPHASE|VARCHAR2||否|処理フェーズ|
|TPLR_DSTARTDATE|DATE||否|データ開始日|
|TPLR_DENDDATE|DATE||否|データ終了日|
|TPLR_CMODIFIERUSERID|VARCHAR2||是|更新者|
|TPLR_DMODIFIEDDATE|DATE||是|更新日|
|TPLR_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID|
|TPLR_CPLUGGABLETYPE|VARCHAR2||是|カラム「TPL_CWORKTYPEID」の扱い 「*(一律)」「対象者の身分」「コード直接指定」|
