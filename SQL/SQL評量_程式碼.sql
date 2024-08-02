--Q2
create table FACILITY(
  FACILITY_NO varchar2(10) primary key, 
  POLICE_NO nvarchar2(10), 
  VILLIAGE_NO nvarchar2(10), 
  TOWNSHIP nvarchar2(10), 
  ADDRESS nvarchar2(50), 
  POPULATION number, 
  UNDER_FLOOR number, 
  FACILITY_TYPE nvarchar2(10)
);
create table VILLIAGE(
  VILLIAGE_NO varchar2(10) primary key, 
  TOWNSHIP nvarchar2(10), 
  VILLIAGE_NAME nvarchar2(10), 
  ADDRESS nvarchar2(50), 
  TEL varchar2(20)
);
create table POLICE(
  POLICE_NO varchar2(10) primary key, 
  STATION nvarchar2(10), 
  ADDRESS nvarchar2(50), 
  TEL varchar2(20)
);
commit;

--Q3
insert into FACILITY
values('F001','M001','C001','竹南鎮','中埔街20號',100,1,'公寓');
insert into FACILITY
values('F002','M001','C002','竹南鎮','和平街79號',3142,1,'大樓');
insert into FACILITY
values('F003','M001','C003','竹南鎮','龍山路三段142號',1072,1,'大樓');
insert into FACILITY
values('F004','M001','C004','後龍鎮','中華路1498號',32,1,'公共設施');
insert into FACILITY
values('F005','M002','C005','苗栗市','米市街80號',26,1,'公寓');
insert into FACILITY
values('F005','M002','C005','苗栗市','光復路117號',26,1,'大樓');
insert into FACILITY
values('F006','M002','C005','苗栗市','博愛街109號',2038,2,'大樓');
insert into FACILITY
values('F006','M002','C005','苗栗市','大同路53號',128,2,'大樓');
insert into FACILITY
values('F007','M003','C006','頭份市','民族里和平路102號',353,1,'公共設施');
insert into FACILITY
values('F008','M003','C007','頭份市','頭份市忠孝忠孝一路69號',501,1,'公寓');
insert into FACILITY
values('F009','M003','C008','頭份市','信義里中正路65號',194,1,'公寓');
insert into FACILITY
values('F010','M003','C008','頭份市','信義里中正路116號',78,1,'私營單位');


insert into VILLIAGE
values('C001','竹南鎮','大埔里','公義路1035號','037-581072');
insert into VILLIAGE
values('C002','竹南鎮','竹南里','竹南里中山路 103 號','037-472735');
insert into VILLIAGE
values('C003','竹南鎮','山佳里','山佳里國光街 14 號','037-614186');
insert into VILLIAGE
values('C004','後龍鎮','埔頂里','埔頂里中興路136-1號','037-724839');
insert into VILLIAGE
values('C005','苗栗市','綠苗里','綠苗里中正路 766 號','037-333240');
insert into VILLIAGE
values('C006','頭份市','民族里','民族里民族路96號','037-660001');
insert into VILLIAGE
values('C007','頭份市','忠孝里','忠孝里光大街82號','037-661145');
insert into VILLIAGE
values('C008','頭份市','信義里','信義里信義路53巷1號','037-616072');


insert into POLICE
values('M001','竹南分局','竹南鎮民族街72號','037-474796');
insert into POLICE
values('M002','苗栗分局','苗栗市金鳳街109號','037-320059');
insert into POLICE
values('M003','頭份分局','頭份市中興路503號','037-663004');

commit;
--Q4
---Q4-1
select 
  distinct P.STATION as 轄管分局, 
  P.TEL as 分局連絡電話 
 from 
  FACILITY F 
  left join POLICE P on F.POLICE_NO = P.POLICE_NO
where 
  F.POPULATION > 1000;

---Q4-2
select 
  P.STATION as 轄管分局, 
  P.TEL as 分局連絡電話, 
  count(F.FACILITY_NO) over (partition by P.STATION) as 避難設施數量 
 from 
  FACILITY F   
  left join POLICE P  on P.POLICE_NO = F.POLICE_NO 
where 
  F.POPULATION > 1000 


---Q4-3
select
  P.STATION as 轄管分局, 
  P.TEL as 分局連絡電話, 
  count(F.FACILITY_NO) over (partition by P.STATION) as 避難設施數量, 
  concat(F.TOWNSHIP, F.ADDRESS) as 避難設施地址, 
  F.FACILITY_TYPE as 類型 
 from
    FACILITY F
    left join POLICE P on P.POLICE_NO = F.POLICE_NO 
where 
  F.POPULATION > 1000

---Q4-4
select 
  V.VILLIAGE_NAME as 村里別, 
  F.ADDRESS as 避難設施地址, 
  F.POPULATION as 容人數量, 
  P.STATION as 轄管分局, 
  P.TEL as 分局連絡電話 
 from 
  FACILITY F 
  left join VILLIAGE V on F.VILLIAGE_NO = V.VILLIAGE_NO 
  left join POLICE P on F.POLICE_NO = P.POLICE_NO 
where 
  F.ADDRESS like '%中%';

---Q4-5
select 
  V.VILLIAGE_NAME as 村里別, 
  concat(V.TOWNSHIP, V.ADDRESS) as 村里辦公室位置, 
  F.ADDRESS as 避難設施地址, 
  F.POPULATION as 容人數量 
 from 
  FACILITY F 
  left join VILLIAGE V on F.VILLIAGE_NO = V.VILLIAGE_NO 
where 
  F.FACILITY_TYPE in ('公寓', '大樓');


--Q5
---Q5-1
update 
  FACILITY 
set 
  POPULATION = 5000 
where 
  '苗栗縣' || TOWNSHIP || ADDRESS = '苗栗縣竹南鎮和平街79號';
commit;

---Q5-2
delete from 
  FACILITY 
where 
  POPULATION < 1000;
commit;