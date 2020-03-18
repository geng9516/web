## mast_admin_func

#### 字段类型

列名|类型|长度|注释|主键|是否为null|默认值
:---:|:---:|:---:|:---:|:---:|:---:|:---:
MAF_ID|int8|64|IDカラム|Y|N|-
MAF_NFORMID_PK|int2|16|機能ID|N|Y|NULL
MAF_CFORM_DESCRIPTION|varchar|1000|機能名称|N|N|-
MAF_CFORM_NAME|varchar|100|フォーム名称|N|N|-
MAF_CLANGUAGE|varchar|10|言語区分|N|Y|NULL
MAF_CMODIFIERUSERID|varchar|30|最終更新者|N|Y|NULL
MAF_DMODIFIEDDATE|date|0|最終更新日|N|Y|NULL
VERSIONNO|int8|64|バージョンNo|N|N|1

#### 建表语句
###### postgresql
```postgresql

    drop table if exists mast_admin_func;
    create table mast_admin_func (
       maf_id int8,
       maf_nformid_pk int2,
       maf_cform_description varchar(1000) not null,
       maf_cform_name varchar(100) not null,
       maf_clanguage varchar(10) default(null),
       maf_cmodifieruserid varchar(30) default(null),
       maf_dmodifieddate date default(null),
       versionno int8 default(1) not null,
       primary key(maf_id)
    );
  
```

##### 示例数据
```postgresql

    insert into mast_admin_func values (1000000001,1,'データディクショナリ設定','psadmin.dataDictionaryBuilder.DataDictionaryBuilder','ja','01_000001','2012-09-13 20:24:50',1);
    insert into mast_admin_func values (1000000002,2,'組織マスタ設定','psadmin.orgTreeBuilder.OrgTreeBuilder','ja','01_000001','2012-09-13 20:24:50',1);
    insert into mast_admin_func values (1000000003,3,'ステータスコントロールマスタメンテ','psadmin.statusMastEditor.StatusMastEditor','ja',null,'2012-09-13 20:24:50',1);

```