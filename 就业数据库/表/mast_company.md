## mast_company

#### 字段类型

列名|类型|长度|注释|主键|是否为null|默认值
:---:|:---:|:---:|:---:|:---:|:---:|:---:
MAC_ID|int8|64|IDカラム|Y|N|-
MAC_CCUSTOMERID_CK_FK|varchar|10|顧客コード|N|Y|NULL
MAC_CCOMPANYID_CK|varchar|10|法人コード|N|Y|NULL
MAC_CLAYEREDCOMPANYID|varchar|4000|法人階層コード|N|N|-
MAC_CPARENTID|varchar|10|有上位法人コード|N|Y|NULL
MAC_NLEVEL|int4|32|階層レベル|N|N|-
MAC_NSEQ|int4|32|行|N|N|-
MAC_CCOMPANYNAME|varchar|100|法人名称|N|Y|NULL
MAC_CCOMPANYNAMEJA|varchar|100|法人名称（日本語）|N|Y|NULL
MAC_CCOMPANYNAMEEN|varchar|100|法人名称（英語）|N|Y|NULL
MAC_CCOMPANYNAMECH|varchar|100|法人名称（中国語）|N|Y|NULL
MAC_CCOMPANYNAME01|varchar|100|法人名称（予備１）|N|Y|NULL
MAC_CCOMPANYNAME02|varchar|100|法人名称（予備２）|N|Y|NULL
MAC_CCOMPANYNICK|varchar|100|法人略称（通称）|N|Y|NULL
MAC_CCOMPANYNICKJA|varchar|100|法人略称（通称_日本語）|N|Y|NULL
MAC_CCOMPANYNICKEN|varchar|100|法人略称（通称_英語）|N|Y|NULL
MAC_CCOMPANYNICKCH|varchar|100|法人略称（通称_中国語）|N|Y|NULL
MAC_CCOMPANYNICK01|varchar|100|法人略称（通称_予備１）|N|Y|NULL
MAC_CCOMPANYNICK02|varchar|100|法人略称（通称_予備２）|N|Y|NULL
MAC_CLANGUAGE|varchar|10|言語区分|N|Y|NULL
MAC_DSTART|date|0|データ開始日|N|N|-
MAC_DEND|date|0|データ終了日|N|Y|NULL
MAC_CDELFLAG|varchar|10|削除フラグ|N|Y|NULL
MAC_DDELDATE|date|0|削除日付|N|Y|NULL
MAC_CMODIFIERUSERID|varchar|30|最終更新者|N|Y|NULL
MAC_DMODIFIEDDATE|date|0|最終更新日|N|Y|NULL
VERSIONNO|int8|64|バージョンNo|N|N|1

#### 建表语句
###### postgresql
```postgresql

  drop table if exists mast_company;
  create table mast_company (
     mac_id int8,
     mac_ccustomerid_ck_fk varchar(19) default(null),
     mac_ccompanyid_ck varchar(10) default(null),
     mac_clayeredcompanyid varchar(4000) not null,
     mac_cparentid varchar(10) default(null),
     mac_nlevel int4 not null,
     mac_nseq int4 not null,
     mac_ccompanyname varchar(100) default(null),
     mac_ccompanynameja varchar(100) default(null),
     mac_ccompanynameen varchar(100) default(null),
     mac_ccompanynamech varchar(100) default(null),
     mac_ccompanyname01 varchar(100) default(null),
     mac_ccompanyname02 varchar(100) default(null),
     mac_ccompanynick varchar(100) default(null),
     mac_ccompanynickja varchar(100) default(null),
     mac_ccompanynicken varchar(100) default(null),
     mac_ccompanynickch varchar(100) default(null),
     mac_ccompanynick01 varchar(100) default(null),
     mac_ccompanynick02 varchar(100) default(null),
     mac_clanguage varchar(10) default(null),
     mac_dstart date not null,
     mac_dend date default(null),
     mac_cdelflag varchar(10) default(null),
     mac_ddeldate date default null,
     mac_cmodifieruserid varchar(30) default(null),
     mac_dmodifieddate date default(null),
     versionno int8 default(1) not null,
     primary key(mac_id)
  );

```

##### 示例数据
```postgresql

 INSERT INTO MAST_COMPANY VALUES ('41', '01', '01', ',01,', NULL, '1', '1', '国立研究開発法人　医薬基盤・健康・栄養研究所', '国立研究開発法人　医薬基盤・健康・栄養研究所', NULL, NULL, NULL, NULL, '医薬基盤', '医薬基盤', NULL, NULL, NULL, NULL, 'ja', TO_DATE('1900-01-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2222-12-31 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '0', NULL, 'INITDATA', TO_DATE('2018-04-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '631');

```