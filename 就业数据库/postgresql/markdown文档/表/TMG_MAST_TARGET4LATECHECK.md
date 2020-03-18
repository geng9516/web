# 遅刻早退判定対象マスタ                   勤怠種別、出張区分から遅刻・早退を判定する対象を設定するマス(TMG_MAST_TARGET4LATECHECK)
| 列名   | 类型   | KEY  | 可否为空 | 注释   |
| ---- | ---- | ---- | ---- | ---- |
|TMTL_CCUSTOMERID|varchar||否|顧客ｺｰﾄﾞ                        固定：01                                                       |
|TMTL_CCOMPANYID|varchar||否|法人ｺｰﾄﾞ                                                                                    |
|TMTL_DSTARTDATE|date||否|ﾃﾞｰﾀ開始日                                                                                   |
|TMTL_DENDDATE|date||否|ﾃﾞｰﾀ終了日                                                                                   |
|TMTL_CMODIFIERUSERID|varchar||是|更新者                                                                                       |
|TMTL_DMODIFIEDDATE|date||是|更新日                                                                                       |
|TMTL_CMODIFIERPROGRAMID|varchar||是|更新プログラムID                                                                                 |
|TMTL_CBUSINESSTRIPID_R|varchar||否|出張区分                                                                                      |
|TMTL_CWORKTYPEID|varchar||否|勤怠種別                                                                                      |
