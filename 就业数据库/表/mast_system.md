## mast_system

#### 字段类型

列名|类型|长度|注释|主键|是否为null|默认值
:---:|:---:|:---:|:---:|:---:|:---:|:---:
MAT_ID|int8|64|IDカラム|Y|N|NULL
MS_CSYSTEMID_PK|varchar|10|システムコード|N|Y|NULL
MS_CSYSTEMNAME|varchar|100|システム名称|N|Y|NULL
MS_CSYSTEMNAMEJA|varchar|100|システム名称（日本語）|N|Y|NULL
MS_CSYSTEMNAMEEN|varchar|100|システム名称（英語）|N|Y|NULL
MS_CSYSTEMNAMECH|varchar|100|システム名称（中国語）|N|Y|NULL
MS_CSYSTEMNAME01|varchar|100|システム名称（予備１）|N|Y|NULL
MS_CSYSTEMNAME02|varchar|100|システム名称（予備２）|N|Y|NULL
MS_CLANGUAGE|varchar|10|言語区分|N|Y|'0'
MS_DCREATEDDATE|date|0|作成日付|N|N|default(now())
MS_CDELFAG|varchar|10|削除フラグ|N|N|'0'
MS_DDELDATE|date|0|削除日付|N|Y|NULL
MS_NTYPE|int2|1|システム種別|N|N|-
MS_CMODIFIERUSERID|varchar|30|最終更新者|N|Y|NULL
MS_DMODIFIEDDATE|date|0|最終更新日|N|Y|NULL
VERSIONNO|int8|64|バージョンNo|N|N|1

#### 建表语句
###### postgresql
```postgresql

 drop table if exists mast_system;
  create table mast_system (
     ms_id int8,
     ms_csystemid_pk varchar(10) default(null),
     ms_csystemname varchar(100) default(null),
     ms_csystemnameja varchar(100) default(null),
     ms_csystemnameen varchar(100) default(null),
     ms_csystemnamech varchar(100) default(null),
     ms_ssystemname01 varchar(100) default(null),
     ms_ssystemname02 varchar(100) default(null),
     ms_clanguage varchar(10) default('0'),
     ms_dcreatedate date default(now()),
     ms_cdelflag varchar(10) not null,
     ms_ddeldate date default(null),
     ms_ntype int2 not null,
     ms_cmodifieruserid varchar(30) default(null),
     ms_dmodifieddate date default(null),
     versionno int8 default(1) not null,
     primary key(ms_id)
  );

INSERT INTO MAST_SYSTEM VALUES ('1000000000', '01', 'SmartCompany', 'SmartCompany', 'SmartCompany', 'SmartCompany', 'SmartCompany', 'SmartCompany', 'ja', TO_DATE('2007-04-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'), '0', NULL, '1', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');

```
