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
values('F001','M001','C001','�˫n��','���H��20��',100,1,'���J');
insert into FACILITY
values('F002','M001','C002','�˫n��','�M����79��',3142,1,'�j��');
insert into FACILITY
values('F003','M001','C003','�˫n��','�s�s���T�q142��',1072,1,'�j��');
insert into FACILITY
values('F004','M001','C004','���s��','���ظ�1498��',32,1,'���@�]�I');
insert into FACILITY
values('F005','M002','C005','�]�ߥ�','�̥���80��',26,1,'���J');
insert into FACILITY
values('F005','M002','C005','�]�ߥ�','���_��117��',26,1,'�j��');
insert into FACILITY
values('F006','M002','C005','�]�ߥ�','�շR��109��',2038,2,'�j��');
insert into FACILITY
values('F006','M002','C005','�]�ߥ�','�j�P��53��',128,2,'�j��');
insert into FACILITY
values('F007','M003','C006','�Y����','���ڨ��M����102��',353,1,'���@�]�I');
insert into FACILITY
values('F008','M003','C007','�Y����','�Y�������������@��69��',501,1,'���J');
insert into FACILITY
values('F009','M003','C008','�Y����','�H�q��������65��',194,1,'���J');
insert into FACILITY
values('F010','M003','C008','�Y����','�H�q��������116��',78,1,'�p����');


insert into VILLIAGE
values('C001','�˫n��','�j�H��','���q��1035��','037-581072');
insert into VILLIAGE
values('C002','�˫n��','�˫n��','�˫n�����s�� 103 ��','037-472735');
insert into VILLIAGE
values('C003','�˫n��','�s�Ψ�','�s�Ψ������ 14 ��','037-614186');
insert into VILLIAGE
values('C004','���s��','�H����','�H����������136-1��','037-724839');
insert into VILLIAGE
values('C005','�]�ߥ�','��]��','��]�������� 766 ��','037-333240');
insert into VILLIAGE
values('C006','�Y����','���ڨ�','���ڨ����ڸ�96��','037-660001');
insert into VILLIAGE
values('C007','�Y����','������','���������j��82��','037-661145');
insert into VILLIAGE
values('C008','�Y����','�H�q��','�H�q���H�q��53��1��','037-616072');


insert into POLICE
values('M001','�˫n����','�˫n����ڵ�72��','037-474796');
insert into POLICE
values('M002','�]�ߤ���','�]�ߥ������109��','037-320059');
insert into POLICE
values('M003','�Y������','�Y����������503��','037-663004');

commit;
--Q4
---Q4-1
select 
  distinct P.STATION as �Һޤ���, 
  P.TEL as �����s���q�� 
 from 
  FACILITY F 
  left join POLICE P on F.POLICE_NO = P.POLICE_NO
where 
  F.POPULATION > 1000;

---Q4-2
select 
  P.STATION as �Һޤ���, 
  P.TEL as �����s���q��, 
  count(F.FACILITY_NO) over (partition by P.STATION) as �����]�I�ƶq 
 from 
  FACILITY F   
  left join POLICE P  on P.POLICE_NO = F.POLICE_NO 
where 
  F.POPULATION > 1000 


---Q4-3
select
  P.STATION as �Һޤ���, 
  P.TEL as �����s���q��, 
  count(F.FACILITY_NO) over (partition by P.STATION) as �����]�I�ƶq, 
  concat(F.TOWNSHIP, F.ADDRESS) as �����]�I�a�}, 
  F.FACILITY_TYPE as ���� 
 from
    FACILITY F
    left join POLICE P on P.POLICE_NO = F.POLICE_NO 
where 
  F.POPULATION > 1000

---Q4-4
select 
  V.VILLIAGE_NAME as �����O, 
  F.ADDRESS as �����]�I�a�}, 
  F.POPULATION as �e�H�ƶq, 
  P.STATION as �Һޤ���, 
  P.TEL as �����s���q�� 
 from 
  FACILITY F 
  left join VILLIAGE V on F.VILLIAGE_NO = V.VILLIAGE_NO 
  left join POLICE P on F.POLICE_NO = P.POLICE_NO 
where 
  F.ADDRESS like '%��%';

---Q4-5
select 
  V.VILLIAGE_NAME as �����O, 
  concat(V.TOWNSHIP, V.ADDRESS) as �����줽�Ǧ�m, 
  F.ADDRESS as �����]�I�a�}, 
  F.POPULATION as �e�H�ƶq 
 from 
  FACILITY F 
  left join VILLIAGE V on F.VILLIAGE_NO = V.VILLIAGE_NO 
where 
  F.FACILITY_TYPE in ('���J', '�j��');


--Q5
---Q5-1
update 
  FACILITY 
set 
  POPULATION = 5000 
where 
  '�]�߿�' || TOWNSHIP || ADDRESS = '�]�߿��˫n��M����79��';
commit;

---Q5-2
delete from 
  FACILITY 
where 
  POPULATION < 1000;
commit;