# 組織ツリーマスタ(MAST_ORGANISATION)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|MO_ID|numeric||否|ＩＤカラム|
|MO_CCUSTOMERID_CK_FK|varchar||是|顧客コード|
|MO_CCOMPANYID_CK_FK|varchar||是|法人コード|
|MO_CSECTIONID_CK|varchar||是|組織コード|
|MO_CLAYEREDSECTIONID|varchar||是|組織階層コード|
|MO_CSECTIONNAME|varchar||是|組織名称|
|MO_CSECTIONNAMEJA|varchar||是|組織名称（日本語）|
|MO_CSECTIONNAMEEN|varchar||是|組織名称（英語）|
|MO_CSECTIONNAMECH|varchar||是|組織名称（中国語）|
|MO_CSECTIONNAME01|varchar||是|組織名称（予備１）|
|MO_CSECTIONNAME02|varchar||是|組織名称（予備２）|
|MO_CSECTIONNICK|varchar||是|組織略称（通称）|
|MO_CSECTIONNICKJA|varchar||是|組織略称（通称_日本語）|
|MO_CSECTIONNICKEN|varchar||是|組織略称（通称_英語）|
|MO_CSECTIONNICKCH|varchar||是|組織略称（通称_中国語）|
|MO_CSECTIONNICK01|varchar||是|組織略称（通称_予備１）|
|MO_CSECTIONNICK02|varchar||是|組織略称（通称_予備２）|
|MO_CLANGUAGE|varchar||是|言語区分|
|MO_DSTART|date||否|データ開始日|
|MO_DEND|date||是|データ終了日|
|MO_CPARENTID|varchar||是|上位組織コード|
|MO_NLEVEL|numeric||是|階層レベル|
|MO_NSEQ|numeric||是|行|
|MO_NHR|numeric||是|仮想組織フラグ|
|MO_CMODIFIERUSERID|varchar||是|最終更新者|
|MO_DMODIFIEDDATE|date||是|最終更新日|
|MO_CSPAREDESC1|varchar||是|予備内容1（別名用ダミー）|
|MO_CSPAREDESC1JA|varchar||是|予備内容1（日本語）|
|MO_CSPAREDESC1EN|varchar||是|予備内容1（英語）|
|MO_CSPAREDESC1CH|varchar||是|予備内容1（中国語）|
|MO_CSPAREDESC101|varchar||是|予備内容1（予備１）|
|MO_CSPAREDESC102|varchar||是|予備内容1（予備2）|
|MO_CSPAREDESC2|varchar||是|予備内容2（別名用ダミー）|
|MO_CSPAREDESC2JA|varchar||是|予備内容2（日本語）|
|MO_CSPAREDESC2EN|varchar||是|予備内容2（英語）|
|MO_CSPAREDESC2CH|varchar||是|予備内容2（中国語）|
|MO_CSPAREDESC201|varchar||是|予備内容2（予備１）|
|MO_CSPAREDESC202|varchar||是|予備内容2（予備2）|
|MO_CSPAREDESC3|varchar||是|予備内容3（別名用ダミー）|
|MO_CSPAREDESC3JA|varchar||是|予備内容3（日本語）|
|MO_CSPAREDESC3EN|varchar||是|予備内容3（英語）|
|MO_CSPAREDESC3CH|varchar||是|予備内容3（中国語）|
|MO_CSPAREDESC301|varchar||是|予備内容3（予備１）|
|MO_CSPAREDESC302|varchar||是|予備内容3（予備2）|
|MO_CSPAREDESC4|varchar||是|予備内容4（別名用ダミー）|
|MO_CSPAREDESC4JA|varchar||是|予備内容4（日本語）|
|MO_CSPAREDESC4EN|varchar||是|予備内容4（英語）|
|MO_CSPAREDESC4CH|varchar||是|予備内容4（中国語）|
|MO_CSPAREDESC401|varchar||是|予備内容4（予備１）|
|MO_CSPAREDESC402|varchar||是|予備内容4（予備2）|
|MO_CSPAREDESC5|varchar||是|予備内容5（別名用ダミー）|
|MO_CSPAREDESC5JA|varchar||是|予備内容5（日本語）|
|MO_CSPAREDESC5EN|varchar||是|予備内容5（英語）|
|MO_CSPAREDESC5CH|varchar||是|予備内容5（中国語）|
|MO_CSPAREDESC501|varchar||是|予備内容5（予備１）|
|MO_CSPAREDESC502|varchar||是|予備内容5（予備2）|
|MO_CSPARECHAR1|varchar||是|予備文字1|
|MO_CSPARECHAR2|varchar||是|予備文字2|
|MO_CSPARECHAR3|varchar||是|予備文字3|
|MO_CSPARECHAR4|varchar||是|予備文字4|
|MO_CSPARECHAR5|varchar||是|予備文字5|
|MO_NSPARENUM1|numeric||是|予備数値1|
|MO_NSPARENUM2|numeric||是|予備数値2|
|MO_NSPARENUM3|numeric||是|予備数値3|
|MO_NSPARENUM4|numeric||是|予備数値4|
|MO_NSPARENUM5|numeric||是|予備数値5|
|MO_DSPAREDATE1|date||是|予備日付1|
|MO_DSPAREDATE2|date||是|予備日付2|
|MO_DSPAREDATE3|date||是|予備日付3|
|MO_DSPAREDATE4|date||是|予備日付4|
|MO_DSPAREDATE5|date||是|予備日付5|
|VERSIONNO|numeric||否|バージョンNo|
