# 基本情報(DT_MAST_EMPLOYEES)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|ME_ID|numeric||是|IDカラム|
|ME_CCUSTOMERID_CK|varchar||否|顧客コード|
|ME_CCOMPANYID|varchar||否|法人コード|
|ME_CEMPLOYEEID_CK|varchar||否|職員番号|
|ME_CUSERID|varchar||否|ユーザID|
|ME_DSTARTDATE|date||否|データ開始日|
|ME_DENDDATE|date||否|データ終了日|
|ME_CMODIFIERUSERID|varchar||是|最終更新者|
|ME_DMODIFIEDDATE|date||是|最終更新日|
|ME_CKANANAME|varchar||否|カナ氏名|
|ME_CKANJINAME|varchar||否|漢字氏名|
|ME_CENGLISHNAME|varchar||是|英語氏名|
|ME_CEMPLOYEENAMECH|varchar||是|中国語氏名|
|ME_CEMPLOYEENAME01|varchar||是|予備1氏名|
|ME_CEMPLOYEENAME02|varchar||是|予備2氏名|
|ME_COLDSURNAMEINKANJI|varchar||是|旧姓（通称）|
|ME_COLDSURNAMEKANA|varchar||是|旧姓(カナ)|
|ME_COLDSURNAMEINENGLISH|varchar||是|旧姓（英語）|
|ME_DCHANGENAME|date||是|改姓年月日|
|ME_CMAIL|varchar||是|ﾒｰﾙｱﾄﾞﾚｽ|
|ME_DDATEOFBIRTH|date||是|生年月日|
|ME_NAGE|numeric||是|年齢|
|ME_NSTANDARDAGE|numeric||是|標準年齢|
|ME_CGENDER|varchar||是|性別|
|ME_CGENDERNM|varchar||是|性別コード|
|ME_CBLOODGROUP|varchar||是|血液型|
|ME_CBLOODGROUPNM|varchar||是|血液型コード|
|ME_CIFSTILLEMPLOYEDID|varchar||否|在職区分|
|ME_CIFSTILLEMPLOYEDNM|varchar||是|在職区分コード|
|ME_DDATEOFEMPLOYEMENT|date||是|入社年月日|
|ME_NAGEATENTRANCE|numeric||是|入社時年齢|
|ME_NYEAROFSERVICE|numeric||是|勤続年数|
|ME_CTRIALEMPLOYEMENT|varchar||是|試用区分|
|ME_CTRIALEMPLOYEMENTNM|varchar||是|試用区分コード|
|ME_DENDOFTRIALEMPLOYMENT|date||是|試用満了日|
|ME_DDATEOFRETIREMENT|date||是|退職年月日|
|ME_NAGEATRESIGNATION|numeric||是|退職時年齢|
|ME_CREASONOFRESIGNATION|varchar||是|退職理由|
|ME_CREASONOFRESIGNATIONNM|varchar||是|退職理由コード|
|ME_CREMARKSOFRESIGNATION|varchar||是|退職理由備考|
|ME_DDATEOFAGELIMIT|date||是|定年到達日|
|ME_DDATEFORMANAGERIALPOSITION|date||是|役職定年日|
|ME_CTYPEOFEMPLOYMENT|varchar||是|職員区分|
|ME_CTYPEOFEMPLOYMENTNM|varchar||是|職員区分コード|
|ME_CIFFRESHCANDIDATID|varchar||是|採用形態区分|
|ME_CIFFRESHCANDIDATNM|varchar||是|採用形態区分コード|
|ME_CMODEOFAPPOINTMENT|varchar||是|入社形態区分|
|ME_CMODEOFAPPOINTMENTNM|varchar||是|入社形態区分コード|
|ME_CTYPEOFSERVICE|varchar||是|勤務形態区分|
|ME_CTYPEOFSERVICENM|varchar||是|勤務形態区分コード|
|ME_CTYPEOFHABITATION|varchar||是|居住区分|
|ME_CTYPEOFHABITATIONNM|varchar||是|居住区分コード|
|ME_CMARITALSTATUS|varchar||是|既婚区分|
|ME_CMARITALSTATUSNM|varchar||是|既婚区分コード|
|ME_CIFCOSTCALCID|varchar||是|直間区分|
|ME_CIFCOSTCALCNM|varchar||是|直間区分コード|
|ME_CTYPEOFPAYMENT|varchar||是|給与区分|
|ME_CTYPEOFPAYMENTNM|varchar||是|給与区分コード|
|ME_CNATIONALITY|varchar||是|国籍|
|ME_CNATIONALITYNM|varchar||是|国籍コード|
|ME_CDOMICILEOFORIGIN|varchar||是|本籍地|
|ME_CDOMICILEOFORIGINNM|varchar||是|本籍地コード|
|ME_CSPAREDESC1|varchar||是|予備内容1（別名用ダミー）|
|ME_CSPAREDESC1JA|varchar||是|予備内容1（日本語）|
|ME_CSPAREDESC1EN|varchar||是|予備内容1（英語）|
|ME_CSPAREDESC1CH|varchar||是|予備内容1（中国語）|
|ME_CSPAREDESC101|varchar||是|予備内容1（予備１）|
|ME_CSPAREDESC102|varchar||是|予備内容1（予備2）|
|ME_CSPAREDESC2|varchar||是|予備内容2（別名用ダミー）|
|ME_CSPAREDESC2JA|varchar||是|予備内容2（日本語）|
|ME_CSPAREDESC2EN|varchar||是|予備内容2（英語）|
|ME_CSPAREDESC2CH|varchar||是|予備内容2（中国語）|
|ME_CSPAREDESC201|varchar||是|予備内容2（予備１）|
|ME_CSPAREDESC202|varchar||是|予備内容2（予備2）|
|ME_CSPAREDESC3|varchar||是|予備内容3（別名用ダミー）|
|ME_CSPAREDESC3JA|varchar||是|予備内容3（日本語）|
|ME_CSPAREDESC3EN|varchar||是|予備内容3（英語）|
|ME_CSPAREDESC3CH|varchar||是|予備内容3（中国語）|
|ME_CSPAREDESC301|varchar||是|予備内容3（予備１）|
|ME_CSPAREDESC302|varchar||是|予備内容3（予備2）|
|ME_CSPAREDESC4|varchar||是|予備内容4（別名用ダミー）|
|ME_CSPAREDESC4JA|varchar||是|予備内容4（日本語）|
|ME_CSPAREDESC4EN|varchar||是|予備内容4（英語）|
|ME_CSPAREDESC4CH|varchar||是|予備内容4（中国語）|
|ME_CSPAREDESC401|varchar||是|予備内容4（予備１）|
|ME_CSPAREDESC402|varchar||是|予備内容4（予備2）|
|ME_CSPAREDESC5|varchar||是|予備内容5（別名用ダミー）|
|ME_CSPAREDESC5JA|varchar||是|予備内容5（日本語）|
|ME_CSPAREDESC5EN|varchar||是|予備内容5（英語）|
|ME_CSPAREDESC5CH|varchar||是|予備内容5（中国語）|
|ME_CSPAREDESC501|varchar||是|予備内容5（予備１）|
|ME_CSPAREDESC502|varchar||是|予備内容5（予備2）|
|ME_NNUMBER01|numeric||是|予備数値1|
|ME_NNUMBER02|numeric||是|予備数値2|
|ME_NNUMBER03|numeric||是|予備数値3|
|ME_NNUMBER04|numeric||是|予備数値4|
|ME_NNUMBER05|numeric||是|予備数値5|
|ME_CCHAR01|varchar||是|予備文字列1|
|ME_CCHAR02|varchar||是|予備文字列2|
|ME_CCHAR03|varchar||是|予備文字列3|
|ME_CCHAR04|varchar||是|予備文字列4|
|ME_CCHAR05|varchar||是|予備文字列5|
|ME_DDATE01|date||是|予備日付1|
|ME_DDATE02|date||是|予備日付2|
|ME_DDATE03|date||是|予備日付3|
|ME_DDATE04|date||是|予備日付4|
|ME_DDATE05|date||是|予備日付5|
|ME_CCODE01|varchar||是|予備コード1|
|ME_CCODENM01|varchar||是|予備コード1コード|
|ME_CCODE02|varchar||是|予備コード2|
|ME_CCODENM02|varchar||是|予備コード2コード|
|ME_CCODE03|varchar||是|予備コード3|
|ME_CCODENM03|varchar||是|予備コード3コード|
|ME_CCODE04|varchar||是|予備コード4|
|ME_CCODENM04|varchar||是|予備コード4コード|
|ME_CCODE05|varchar||是|予備コード5|
|ME_CCODENM05|varchar||是|予備コード5コード|
|VERSIONNO|numeric||是|バージョンNo|
