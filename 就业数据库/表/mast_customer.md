## mast_customer

#### 字段类型

列名|类型|长度|注释|主键|是否为null|默认值
:---:|:---:|:---:|:---:|:---:|:---:|:---:
MC_ID|int8|64|IDカラム|Y|N|AUTO_INCREMENT
MC_CCUSTOMERID_PK|varchar|10|顧客コード|N|Y|NULL
MC_CCUSTOMERNAME|varchar|100|顧客名称|N|Y|NULL
MC_CDELFLAG|varchar|10|削除フラグ|N|N|'0'
MC_DDELDATE|date|0|削除日付|N|Y|NULL
MC_DCREATEDDATE|date|0|作成日付|N|N|now()
MC_CLANGUAGE|varchar|10|言語区分|N|Y|NULL
MC_CMODIFIERUSERID|varchar|30|最終更新者|N|Y|NULL
MC_DMODIFIEDDATE|date|0|最終更新日|N|Y|NULL
VERSIONNO|int8|64|バージョンNo|N|N|1

#### 建表语句
###### postgresql
```postgresql

 drop table if exists mast_customer;
 create table mast_customer (
     mc_id bigserial,
     mc_ccustomerid_pk varchar(10) default(null),
     mc_ccustomername varchar(100) default(null),
     mc_cdelflag varchar(10) default('0'),
     mc_ddeldate date default null,
     mc_dcreatedate date default(now()),
     mc_clanguage varchar(10) default(null),
     mc_cmodifieruserid varchar(30) default(null),
     mc_dmodifieddate date default(null),
     versionno int8 default(1) not null,
     primary key(mc_id)
  );

```
