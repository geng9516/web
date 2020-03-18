## mast_css_category

#### 字段类型

列名|类型|长度|注释|主键|是否为null|默认值
:---:|:---:|:---:|:---:|:---:|:---:|:---:
MCC_ID|int8|64|IDカラム|Y|N|-
MCC_CCATEGORYID|varchar|30|カテゴリID|N|Y|NULL
MCC_CCATEGORYNAME|varchar|100|カテゴリ名称|N|Y|NULL
MCC_CCATEGORYNAMEJA|varchar|100|項目名（日本語）|N|Y|NULL
MCC_CCATEGORYNAMEEN|varchar|100|項目名（英語）|N|Y|NULL
MCC_CCATEGORYNAMECH|varchar|100|項目名（中国語）|N|Y|NULL
MCC_CCATEGORYNAME01|varchar|100|項目名（予備１）|N|Y|NULL
MCC_CCATEGORYNAME02|varchar|100|項目名（予備２）|N|Y|NULL
MCC_CCSSPREFIX|varchar|100|CSSファイル接頭辞|N|Y|NULL
MCC_CCSSDEFAULTFLG|varchar|1|デフォルトCSSフラグ|N|Y|NULL
MCC_CMODIFIERUSERID|varchar|30|最終更新者|N|Y|NULL
MCC_DMODIFIEDDATE|date|0|最終更新日|N|Y|NULL
VERSIONNO|int8|64|バージョンNo|N|N|1

#### 建表语句
###### postgresql
```postgresql

  drop table if exists mast_css_category;
  create table mast_css_category (
     mcc_id int8,
     mcc_ccategoryid varchar(30) default(null),
     mcc_ccategoryname varchar(100) default(null),
     mcc_ccategorynameja varchar(100) default(null),
     mcc_ccategorynameen varchar(100) default(null),
     mcc_ccategorynamech varchar(100) default(null),
     mcc_ccategoryname01 varchar(100) default(null),
     mcc_ccategoryname02 varchar(100) default(null),
     mcc_ccssprefix varchar(100) default(null),
     mcc_ccssdefaultflg varchar(1) default(null),
     mcc_cmodifieruserid varchar(30) default(null),
     mcc_dmodifieddate date default(null),
     versionno int8 default(1) not null,
     primary key(mcc_id)
  );

```

##### 示例数据
```postgresql

 INSERT INTO MAST_CSS_CATEGORY VALUES ('1000000001', 'default', 'デフォルトスタイル', 'デフォルトスタイル', 'Default Style', 'デフォルトスタイル', 'デフォルトスタイル', 'デフォルトスタイル', 'STD', '1', NULL, TO_DATE('2012-09-13 20:24:41', 'SYYYY-MM-DD HH24:MI:SS'), '1');

```