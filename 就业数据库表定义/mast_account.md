## mast_account

#### 字段类型

列名|类型|长度|注释|主键|是否为null|默认值
:---:|:---:|:---:|:---:|:---:|:---:|:---:
ma_id|int8|64|IDカラム|Y|N|AUTO_INCREMENT
MA_CCUSTOMERID|varchar|10|顧客コード|N|Y|NULL
MA_CUSERID|varchar|30|ユーザID|N|Y|NULL
MA_CACCOUNT|varchar|30|アカウント|N|Y|NULL
MA_DSTART|date|0|有効期間開始日|N|Y|now()
MA_DEND|date|0|有効期間終了日|N|Y|to_date('2222-12-31'::text, 'YYYY/MM/DD'::text)
MA_NRETRYCOUNTER|int2|0|パスワード間違い回数|N|Y|0
MA_NPASSWORDLOCK|bool|1|ロックアウトフラグ|N|Y|false
MA_CADMINUSER|varchar|1|管理ツールユーザフラグ|N|Y|0
MA_DCREATE|date|0|アカウント作成日|N|Y|NULL
MA_CMODIFIERUSERID|varchar|30|最終更新者|N|Y|NULL
MA_DMODIFIEDDATE|date|0|最終更新日|N|Y|NULL
VERSIONNO|int8|64|バージョンNo|N|N|1

#### 建表语句
###### postgresql
```postgresql

  drop table if exists mast_account;
  create table mast_account (
     ma_id bigserial,
     ma_ccustomerid varchar(10) default(null),
     ma_cuserid varchar(30) default(null),
     ma_caccount varchar(30) default(null),
     ma_dstart date default(now()),
     ma_dend date default(to_date('2222-12-31', 'YYYY/MM/DD')),
     ma_nretrycounter int2 default(0),
     ma_npasswordlock bool default(false),
     ma_cadminuser varchar(1) default('0'),
     ma_dcreate date default(null),
     ma_cmodifieruserid varchar(30) default(null),
     ma_dmodifieddate date default(null),
     versionno int8 default(1) not null,
     primary key(ma_id)
  );

```

