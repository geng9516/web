## mast_apptemplate

#### 字段类型

列名|类型|长度|注释|主键|是否为null|默认值
:---:|:---:|:---:|:---:|:---:|:---:|:---:
MAT_ID|int8|64|IDカラム|Y|N|NULL
MAT_CTEMPLATEID|varchar|30|レイアウトID|N|N|-
MAT_CNAME|varchar|100|レイアウト名|N|N|-
MAT_CMODIFIERUSERID|varchar|30|最終更新者|N|Y|NULL
MAT_DMODIFIEDDATE|date|0|最終更新日|N|Y|NULL
VERSIONNO|int8|64|バージョンNo|N|N|1

#### 建表语句
###### postgresql
```postgresql

    drop table if exists mast_apptemplate;
    create table mast_apptemplate (
       mat_id int8,
       mat_ctemplateid varchar(30) not null,
       mat_cname varchar(100) not null,
       mat_cmodifieruserid varchar(30) default(null),
       mat_dmodifieddate date default(null),
       versionno int8 default(1) not null,
       primary key(mat_id)
    );
  
    INSERT INTO MAST_APPTEMPLATE VALUES ('1000000001', 'ContentSearchResultEmp', 'コンテンツ検索結果（従業員）', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO MAST_APPTEMPLATE VALUES ('1000000002', 'ContentSearchResultOrg', 'コンテンツ検索結果（組織）', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO MAST_APPTEMPLATE VALUES ('1000000003', 'ManagementSite', 'システム管理メニュー', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO MAST_APPTEMPLATE VALUES ('1000000004', 'PersonalManagement', 'パーソナルサイト', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO MAST_APPTEMPLATE VALUES ('1000000005', 'TopPage', 'トップページ', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO MAST_APPTEMPLATE VALUES ('1000000006', 'OrganizationalManagement', '組織情報サイト', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO MAST_APPTEMPLATE VALUES ('1000000007', 'PersonalInformation', '人事情報サイト', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO MAST_APPTEMPLATE VALUES ('1000000008', 'TalentManagement', 'マネージャサイト', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO MAST_APPTEMPLATE VALUES ('1000000009', 'ContentSearch', 'コンテンツ検索', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');
    INSERT INTO MAST_APPTEMPLATE VALUES ('1000000010', 'Dialog', 'ダイアログ', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');

```

