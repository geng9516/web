# TMG_GROUP_POST(TMG_GROUP_POST)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TGRP_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TGRP_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TGRP_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TGRP_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TGRP_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TGRP_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TGRP_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TGRP_CSECTIONID|varchar||否|部署コード                                                       MO:MO_CSECTIONID_CK           |
|TGRP_CGROUPID|varchar||否|グループコード                       グループ作成時にIDを付番                                               |
|TGRP_CPOSTID|varchar||否|役職コード|
|TGRP_NDAYDUTY_WEEKLY|numeric||是|日直回数上限値(週次)|
|TGRP_NNIGHTDUTY_WEEKLY|numeric||是|宿直回数上限値(週次)|
|TGRP_NDAYDUTY_MONTHLY|numeric||是|日直回数上限値(月次)|
|TGRP_NNIGHTDUTY_MONTHLY|numeric||是|宿直回数上限値(月次)|
