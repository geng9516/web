# TMG_GROUP_POST(TMG_GROUP_POST)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGRP_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TGRP_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TGRP_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TGRP_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TGRP_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TGRP_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TGRP_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TGRP_CSECTIONID|VARCHAR2||否|部署コード                                                       MO:MO_CSECTIONID_CK           |
|TGRP_CGROUPID|VARCHAR2||否|グループコード                       グループ作成時にIDを付番                                               |
|TGRP_CPOSTID|VARCHAR2||否|役職コード|
|TGRP_NDAYDUTY_WEEKLY|NUMBER||是|日直回数上限値(週次)|
|TGRP_NNIGHTDUTY_WEEKLY|NUMBER||是|宿直回数上限値(週次)|
|TGRP_NDAYDUTY_MONTHLY|NUMBER||是|日直回数上限値(月次)|
|TGRP_NNIGHTDUTY_MONTHLY|NUMBER||是|宿直回数上限値(月次)|
