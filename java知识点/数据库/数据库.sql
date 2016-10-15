-- �������ݿ�
mysql -hlocalhost -P3306 -uroot -proot

-- �˳����ݿ� 
exit 
quit
\q

-- �鿴ȫ�������ݿ�
show databases;

-- ʹ�þ���ĳ�����ݿ� 
use mysql;

-- �鿴���ݿ��е����б�
show tables;

-- �鿴ĳ���е�ȫ������ 
select * from user;

-- SQL���ṹ����ѯ���� ��ָ��ĺϼ���  DDL DML DCL 
-- �������ݿ� ���ݱ� ���ݱ������

-- �������ݿ�
create database ucai_db;

-- ɾ�����ݿ�
drop database ucai_db;

-- �������е����ݣ���ɾ�Ĳ�

-- ��ѯȫ��Ա����Ϣ
select * from t_employee;
-- ����
select empno,ename,job,mgr,hiredate,sal,comm,deptno from t_employee;

-- ��ѯ����Ա�������������ź͸�λ
select ename,empno,job from t_employee;

-- ��ѯ�ù�˾���еĸ�λ
select distinct job from t_employee;

-- ��ѯ�ù�˾����Ա������������н����н��sql���֧��+-*/%��Щ��ѧ������
select ename,sal,sal*12 from t_employee;
select ename,sal,sal*12 as yearSal from t_employee;
select ename,sal,sal*12 yearSal from t_employee;

-- ��ɲ�ѯ�ṹ��ƴ��
select concat(ename,"����нΪ",sal*12,"��Ԫ") from t_employee;
-- Ҳ������ϱ���һ��ʹ��
select concat(ename,"����нΪ",sal*12,"��Ԫ") as yearSal from t_employee;

-- ��ѯ��λ��CLERK�����е�Ա����Ϣ
select * from t_employee where job = 'CLERK';

-- ��ѯ��λΪCLERK�����ҹ��ʴ���1000��Ա����Ϣ
select * from t_employee where job = 'CLERK' and sal > 1000;
select * from t_employee where job = 'CLERK' && sal > 1000;

-- ��ϵ�������> < = != <> >= <=
-- �߼��������and && or || not

-- ��ѯ������800--1500֮���Ա����Ϣ
select * from t_employee where sal >= 800 and sal <= 1500;
select * from t_employee where sal >= 800 && sal <= 1500;
select * from t_employee where sal between 800 and 1500;   -- ǰ�պ��

-- ��ѯ���ʲ���800--1500֮���Ա����Ϣ
select * from t_employee where sal not between 800 and 1500;
select * from t_employee where sal > 1500 or sal< 800;

-- ��ѯmgrΪnull��Ա����Ϣ
select * from t_employee where mgr is null;

-- ��ѯcomm����Ϊnull��Ա����Ϣ
select * from t_employee where comm is not null;

-- ��ѯ������7521��7566��7654��Ա����Ϣ
select * from t_employee where empno = 7521 or empno = 7566 || empno = 7654;
select * from t_employee where empno in (7521,7566,7654);

-- ��ѯ���Ų���7521��7566��7654��Ա����Ϣ
select * from t_employee where empno not in (7521,7566,7654);

-- ��ѯԱ����������A��ͷ��Ա����Ϣ
select * from t_employee where ename like 'A%';

-- ��ѯԱ���������ڶ�����ĸ��A��Ա����Ϣ
select * from t_employee where ename like '_A%';

-- �ֻ����ܻ�Android�ֻ������ֻ�ǧԪ��  ģ����ѯ

-- ��ѯ����Ա��������Ա���Ĺ�����������
select * from t_employee order by sal asc; -- asc:����Ĭ�ϲ�дҲ�����򣬽���Ļ�ʹ��desc

-- ���ֶ����򣺲�ѯ����Ա��������Ա�����������������������ͬ������Ա������ְ���ڽ�������
select * from  t_employee order by sal asc,hiredate desc;

-- ��ҳ��ѯ
select * from t_employee limit 0,5;  -- ��һҳ
select * from t_employee limit 5,5;  -- �ڶ�ҳ
select * from t_employee limit 10,5;  -- ����ҳ

-- ͨ�ù�ʽ pageIdҳ��,pageSizeÿҳ����
select * from t_employee limit (pageId-1)*pageSize,pageSize;
select * from t_employee limit (ҳ��-1)*ÿҳ����,ÿҳ����;

-- �麯�� 
-- ��ѯ��˾��ȫ������
select count(*) from t_employee;

-- ��ѯ��˾��ȡ���������
select count(comm) from t_employee where comm != 0;

-- ��ѯԱ����ȡ�����ƽ����
select avg(comm) from t_employee where comm != 0;

-- ��ѯ����Ա���Ĺ��ʵ��ܺ�
select sum(sal) from t_employee;

-- ��ѯԱ����ȡ��ȫ������
select sum(comm) from t_employee where comm != 0;

-- ��ѯԱ������߹��ʺ���͹���
select max(sal),min(sal) from t_employee;
























