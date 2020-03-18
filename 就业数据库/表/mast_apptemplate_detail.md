## mast_apptemplate_detail

#### 字段类型

列名|类型|长度|注释|主键|是否为null|默认值
:---:|:---:|:---:|:---:|:---:|:---:|:---:
MATD_ID|int8|64|IDカラム|Y|N|-
MATD_CTEMPLATEID|varchar|30|ブロックID|N|N|-
MATD_CBLOCKID|varchar|30|ブロック名|N|N|-
MATD_NSEQ|int2|16|並び順|N|N|-
MATD_CCOMPONENTID|varchar|30|コンポーネントID|N|N|-
MATD_CMODIFIERUSERID|varchar|30|最終更新者|N|Y|NULL
MATD_DMODIFIEDDATE|date|0|最終更新日|N|Y|NULL
VERSIONNO|int8|64|バージョンNo|N|N|1

#### 建表语句
###### postgresql
```postgresql

    drop table if exists mast_apptemplate_detail;
    create table mast_apptemplate_detail (
       matd_id int8,
       matd_ctemplateid varchar(30) not null,
       matd_cblockid varchar(30) not null,
       matd_nseq int2 not null,
       matd_ccomponentid varchar(30) not null,
       matd_cmodifieruserid varchar(30) default(null),
       matd_dmodifieddate date default(null),
       versionno int8 default(1) not null,
       primary key(matd_id)
    );
    
```

##### 示例数据
```postgresql
  INSERT INTO mast_apptemplate_detail VALUES ('1000000007', 'PersonalManagement', 'header', '1', 'Header', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000008', 'PersonalManagement', 'header', '2', 'BreadCrumbsList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000009', 'ContentSearch', 'footer', '1', 'Footer', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000010', 'ContentSearch', 'header', '1', 'Header', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000011', 'ContentSearch', 'header', '2', 'BreadCrumbsList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000012', 'ContentSearchBK', 'detail', '1', 'AppList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000013', 'ContentSearchResultEmp', 'detail', '1', 'EmpInfoForSearch', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000014', 'ContentSearchResultEmp', 'detail', '2', 'AppList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000015', 'ContentSearchResultEmp', 'footer', '1', 'Footer', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000016', 'ContentSearchResultEmp', 'header', '1', 'Header', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000017', 'ContentSearchResultEmp', 'header', '2', 'BreadCrumbsList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000018', 'ContentSearchResultOrg', 'detail', '1', 'AppList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000019', 'ContentSearchResultOrg', 'footer', '1', 'Footer', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000020', 'ContentSearchResultOrg', 'header', '1', 'Header', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000021', 'ContentSearchResultOrg', 'header', '2', 'BreadCrumbsList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000022', 'TalentManagement', 'header', '1', 'Header', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000023', 'TalentManagement', 'header', '2', 'BreadCrumbsList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000024', 'TalentManagement', 'left', '1', 'DescriptionArea', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000025', 'TalentManagement', 'left', '2', 'EvalEmpCooperation', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000026', 'TalentManagement', 'left', '3', 'OrgEmpCooperation', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000027', 'TalentManagement', 'left', '4', 'EmpAppCooperation', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000028', 'TalentManagement', 'left', '5', 'SecurityDate', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000029', 'TalentManagement', 'left', '6', 'SiteLayoutChange', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000030', 'TalentManagement', 'left', '7', 'EvalLevelTree', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000031', 'TalentManagement', 'left', '8', 'TalentOrgTree', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000032', 'TalentManagement', 'left', '9', 'EvaluateeList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000033', 'TalentManagement', 'detail', '1', 'EmployeeInfo', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000034', 'TalentManagement', 'detail', '2', 'AppList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000035', 'TalentManagement', 'footer', '1', 'Footer', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000036', 'OrganizationalManagement', 'header', '1', 'Header', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000037', 'OrganizationalManagement', 'header', '2', 'BreadCrumbsList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000038', 'OrganizationalManagement', 'left', '1', 'DescriptionArea', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000039', 'OrganizationalManagement', 'left', '2', 'CompOrgCooperation', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000040', 'OrganizationalManagement', 'left', '3', 'SecurityDate', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000041', 'OrganizationalManagement', 'left', '4', 'CompanyTree', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000042', 'OrganizationalManagement', 'left', '5', 'OrgTree', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000043', 'OrganizationalManagement', 'left', '6', 'OrgAppCooperation', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000044', 'OrganizationalManagement', 'detail', '1', 'AppList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000045', 'OrganizationalManagement', 'footer', '1', 'Footer', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000046', 'PersonalInformation', 'detail', '1', 'PersonalInformationCompnent', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000047', 'PersonalInformation', 'detail', '2', 'AppList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000048', 'PersonalInformation', 'footer', '1', 'Footer', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000049', 'PersonalInformation', 'header', '1', 'Header', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000050', 'PersonalInformation', 'header', '2', 'BreadCrumbsList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000051', 'ManagementSite', 'detail', '1', 'AppList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000052', 'ManagementSite', 'footer', '1', 'Footer', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000053', 'ManagementSite', 'header', '1', 'Header', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');
  INSERT INTO mast_apptemplate_detail VALUES ('1000000054', 'ManagementSite', 'header', '2', 'BreadCrumbsList', NULL, TO_DATE('2012-09-13 20:24:42', 'SYYYY-MM-DD HH24:MI:SS'), '1');

```
