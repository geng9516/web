# 遅刻早退判定対象マスタ                   勤怠種別、出張区分から遅刻・早退を判定する対象を設定するマス(TMG_MAST_TARGET4LATECHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMTL_DSTARTDATE|DATE||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMTL_DENDDATE|DATE||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMTL_CMODIFIERUSERID|VARCHAR2||是|更新者                                                                                       |
|TMTL_DMODIFIEDDATE|DATE||是|更新日                                                                                       |
|TMTL_CMODIFIERPROGRAMID|VARCHAR2||是|更新プログラムID                                                                                 |
|TMTL_CBUSINESSTRIPID_R|VARCHAR2||否|出張区分                                                                                      |
|TMTL_CWORKTYPEID|VARCHAR2||否|勤怠種別                                                                                      |
|TMTL_CCUSTOMERID|VARCHAR2||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMTL_CCOMPANYID|VARCHAR2||否|法人ｺｰﾄﾞ                                                                                    |
