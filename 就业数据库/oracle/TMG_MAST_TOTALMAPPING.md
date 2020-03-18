# 集計項目マッピングマスタ                  日次集計処理において、TMG_DAILYおよびTMG_DAI(TMG_MAST_TOTALMAPPING)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMTM_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMTM_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
|TMTM_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMTM_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMTM_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMTM_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMTM_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMTM_CWORKTYPEID|VARCHAR2||否|勤怠種別     MGD:TMG_WORKTYPE|
|TMTM_CMASTER4SOURCE|VARCHAR2||否|変換元マスタコード                                                                                 |
|TMTM_CUNITID|VARCHAR2||否|集計単位ｺｰﾄﾞ                                                                                  |
|TMTM_CMASTER4DEST|VARCHAR2||否|変換先マスタコード                                                                                 |
|TMTM_CPHASE|VARCHAR2||否|null|
|TMTM_CMANAGERID|VARCHAR2||否|管理職コード     TMG_MAST_MANAGER4SALARY(TMG_HIST_MANAGER)|
