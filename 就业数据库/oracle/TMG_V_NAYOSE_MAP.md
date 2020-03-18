# [就業]名寄せ情報(TMG_V_NAYOSE_MAP)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TVNM_POST_NAME|VARCHAR2||是|役職名称|
|TVNM_WORKERTYPEID|VARCHAR2||是|勤怠種別|
|TVNM_WORKERTYPE_NAME|VARCHAR2||是|勤怠種別名称|
|TVNM_IS_MANAGED|VARCHAR2||是|勤怠管理対象フラグ（1:管理対象、0:管理対象外）|
|TVNM_CUSTOMERID|VARCHAR2||是|顧客コード|
|TVNM_COMPANYID|VARCHAR2||是|法人コード|
|TVNM_USERID|VARCHAR2||是|ユーザーID|
|TVNM_ACCOUNT|VARCHAR2||是|アカウント|
|TVNM_IS_DEFAULT|NUMBER||是|デフォルト選択フラグ（1:初回ログイン時デフォルトで選択）|
|TVNM_EMPLOYEEID|VARCHAR2||否|職員番号|
|TVNM_KANJI_NAME|NVARCHAR2||否|漢字氏名|
|TVNM_KANA_NAME|NVARCHAR2||否|カナ氏名|
|TVNM_IS_RETIRED|VARCHAR2||否|在退フラグ（1:退職済み、0:在籍中）|
|TVNM_SECTIONID|VARCHAR2||是|所属コード|
|TVNM_SECTION_NAME|VARCHAR2||是|所属名称|
|TVNM_POSTID|VARCHAR2||是|役職コード|
