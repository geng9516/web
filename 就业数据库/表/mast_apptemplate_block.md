## mast_apptemplate_block

#### 字段类型

列名|类型|长度|注释|主键|是否为null|默认值
:---:|:---:|:---:|:---:|:---:|:---:|:---:
MAB_ID|int8|64|IDカラム|Y|N|-
MAB_CBLOCKID|varchar|30|ブロックID|N|N|-
MAB_CNAME|varchar|30|ブロック名|N|N|-
MAB_NSEQ|int2|16|並び順|N|N|-
MAB_CMODIFIERUSERID|varchar|30|最終更新者|N|Y|NULL
MAB_DMODIFIEDDATE|date|0|最終更新日|N|Y|NULL
VERSIONNO|int8|64|バージョンNo|N|N|1

#### 建表语句
###### postgresql
```postgresql

    drop table if exists mast_apptemplate_block;
    create table mast_apptemplate_block (
       mab_id int8,
       mab_cblockid varchar(30) not null,
       mab_cname varchar(30) not null,
       mab_nseq int2 not null,
       mab_cmodifieruserid varchar(30) default(null),
       mab_dmodifieddate date default(null),
       versionno int8 default(1) not null,
       primary key(mab_id)
    );
  
```

##### 示例数据
```postgresql

    INSERT INTO mast_apptemplate_block VALUES ('1000000001', 'header', 'ヘッダブロック', '10', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO mast_apptemplate_block VALUES ('1000000002', 'left', '左サイドブロック', '20', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO mast_apptemplate_block VALUES ('1000000003', 'detail', 'ボディーブロック', '30', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO mast_apptemplate_block VALUES ('1000000004', 'footer', 'フッタブロック', '40', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');

```