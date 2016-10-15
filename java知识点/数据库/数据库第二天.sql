��insert into student values(1,'����',20);
ɾdelete from student ;
��update student set sname = '����' where sid = 2;
�� select *from table
-- ��ѯÿ�����ŵĹ��ʵ��ܺ�  �������ѯ��
select deptno,sum(sal) from t_employee group by deptno;

-- sum��count ��max��min��avg �� �麯�� �ͷ���group byһ��ʹ��
-- group by����У�select������麯������group by��������ֶβ������塣

-- ��ѯÿ�����ŵ������������ܺ͡�ƽ�����ʡ���߹��ʺ���͹���
select deptno,count(*),sum(sal),avg(sal),max(sal),min(sal) from t_employee group by deptno;

-- ���ղ��ű�ź���ְ���ڷ��飬ͳ��ÿ���Ա�������������ܺͺ�ƽ������
select deptno,hiredate,count(*),sum(sal),avg(sal) from t_employee group by deptno,hiredate;

-- ���ղ��ű�ŷ��飬��ѯÿ��ƽ�����ʴ���2000�Ĳ��ŵ������������ܺ͡�ƽ�����ʡ�
select deptno,count(*),sum(sal),avg(sal) from t_employee group by deptno having avg(sal) > 2000;

-- where �� having������
-- 1��having��������group by���棬where������
-- 2����where��havingͬʱ����ʹ�õĵط���ʹ��where

-- �Ӳ�ѯ
-- ��ѯ���ʱ�SMITH���ʸߵ����е�Ա����ȫ����Ϣ
select * from t_employee where sal > (select sal from t_employee where ename = 'SMITH');

-- ��ѯ���ʺ�WARDһ��������Ա����ȫ����Ϣ(��ѯ�������������)
SELECT
	*
FROM
	t_employee
WHERE
	sal = (
		SELECT
			sal
		FROM
			t_employee
		WHERE
			ename = 'WARD'
	)
AND ename != 'WARD';

-- ��ѯ���ʺ�ְλ����WARDһ��������Ա����ȫ����Ϣ(��ѯ�������������)
SELECT
	*
FROM
	t_employee
WHERE
	(sal,job) = (
		SELECT
			sal,job
		FROM
			t_employee
		WHERE
			ename = 'WARD'
	)
AND ename != 'WARD';

-- ��ѯԱ����Ϣ��Ҫ��Ա���Ĳ��ţ����ڲ��ű����м�¼
SELECT
	*
FROM
	t_employee
WHERE
	deptno IN (SELECT deptno FROM t_dept);

-- ��ѯԱ����Ϣ��Ҫ��Ա���Ĳ��ţ��ڲ��ű���û�м�¼
SELECT
	*
FROM
	t_employee
WHERE
	deptno NOT IN (SELECT deptno FROM t_dept);

-- �ѿ�����
-- ��ѯԱ����Ϣ��Ա�����ڵĲ��ŵ���Ϣ
SELECT
	*
FROM
	t_employee emp,
	t_dept dept
WHERE
	emp.deptno = dept.deptno;
	
-- �����ӣ���Եѿ������еĽ��ɸѡ������������Ҫ�Ľ�������ѯ�����߶�ƥ��ļ�¼
SELECT
	*
FROM
	t_employee emp INNER JOIN
	t_dept dept
ON
	emp.deptno = dept.deptno;
	
-- ��ѯÿ��Ա���ı�š�������ְλ���������ƺͲ�������λ��
SELECT
	emp.empno,
	emp.ename,
	emp.job,
	dept.dname,
	dept.loc
FROM
	t_employee emp,
	t_dept dept
WHERE
	emp.deptno = dept.deptno;

-- ������д����
SELECT
	emp.empno,
	emp.ename,
	emp.job,
	dept.dname,
	dept.loc
FROM
	t_employee emp INNER JOIN
	t_dept dept
ON
	emp.deptno = dept.deptno;

-- ������
-- ��ѯԱ����������ְλ�������쵼������
SELECT
	t1.ename,
	t1.job,
	t2.ename
FROM
	t_employee t1,
	t_employee t2
WHERE
	t1.mgr = t2.empno;

SELECT
	t1.ename,
	t1.job,
	t2.ename
FROM
	t_employee t1 INNER JOIN
	t_employee t2
ON
	t1.mgr = t2.empno;

-- ��ѯÿ��Ա���ı�š��������������ʡ�ְλ���쵼�������������ơ���������λ��
SELECT
	t1.empno,
	t1.ename,
	t1.sal,
	t1.job,
	t2.ename,
	d.dname,
	d.loc
FROM
	t_dept d,
	t_employee t1,
	t_employee t2
WHERE
	t1.deptno = d.deptno
AND t1.mgr = t2.empno;

SELECT
	t1.empno,
	t1.ename,
	t1.sal,
	t1.job,
	t2.ename,
	d.dname,
	d.loc
FROM
	t_dept d
INNER JOIN t_employee t1 ON t1.deptno = d.deptno
INNER JOIN t_employee t2 ON t1.mgr = t2.empno;

-- ������ �� �������ӡ���������
-- ��ѯԱ����������ְλ�������쵼������
-- LEFT JOIN ��= �����ӵĽ��+��ߵı������������в�ƥ��Ľ�����ұ߲�null
SELECT
	t1.ename,
	t1.job,
	t2.ename
FROM
	t_employee t1 LEFT JOIN
	t_employee t2
ON
	t1.mgr = t2.empno;

-- ��ѯÿ��Ա���ı�š�������ְλ���������ƺͲ�������λ��
SELECT
	t.empno,
	t.ename,
	t.job,
	d.dname,
	d.loc
FROM
	t_employee t LEFT JOIN
	t_dept d
ON
	t.deptno = d.deptno;
	
-- t_dept d RIGHT JOIN	t_employee t  �൱��t_employee t LEFT JOIN t_dept d
-- �����ң�����Ѷ�Ӧ�����ϵı��еļ�¼ȫ������ѯ����������һ������null

-- �ϲ���ѯ�����
-- union/union all
SELECT
	*
FROM
	t_employee
WHERE
	empno IN (7369, 7499)
UNION ALL
	SELECT
		*
	FROM
		t_employee
	WHERE
		empno IN (7499, 7369);
		
-- union ȥ�أ���union all��ȥ�ء�

-- ���ݵĲ��롢���ݵ�ɾ�������ݵĸ���
-- �������ݱ��������͡��������Լ����
-- ���ݿ�����еĹ�ϵ��һ��һ��һ�Զࡢ��Զ�
-- JDBC - ��Ŀ 


-- ��ѯÿ�����ŵĹ��ʵ��ܺ�  �������ѯ��
select deptno,sum(sal) from t_employee group by deptno;

-- sum��count ��max��min��avg �� �麯�� �ͷ���group byһ��ʹ��
-- group by����У�select������麯������group by��������ֶβ������塣

-- ��ѯÿ�����ŵ������������ܺ͡�ƽ�����ʡ���߹��ʺ���͹���
select deptno,count(*),sum(sal),avg(sal),max(sal),min(sal) from t_employee group by deptno;

-- ���ղ��ű�ź���ְ���ڷ��飬ͳ��ÿ���Ա�������������ܺͺ�ƽ������
select deptno,hiredate,count(*),sum(sal),avg(sal) from t_employee group by deptno,hiredate;

-- ���ղ��ű�ŷ��飬��ѯÿ��ƽ�����ʴ���2000�Ĳ��ŵ������������ܺ͡�ƽ�����ʡ�
select deptno,count(*),sum(sal),avg(sal) from t_employee group by deptno having avg(sal) > 2000;

-- where �� having������
-- 1��having��������group by���棬where������
-- 2����where��havingͬʱ����ʹ�õĵط���ʹ��where

-- �Ӳ�ѯ
-- ��ѯ���ʱ�SMITH���ʸߵ����е�Ա����ȫ����Ϣ
select * from t_employee where sal > (select sal from t_employee where ename = 'SMITH');

-- ��ѯ���ʺ�WARDһ��������Ա����ȫ����Ϣ(��ѯ�������������)
SELECT
	*
FROM
	t_employee
WHERE
	sal = (
		SELECT
			sal
		FROM
			t_employee
		WHERE
			ename = 'WARD'
	)
AND ename != 'WARD';

-- ��ѯ���ʺ�ְλ����WARDһ��������Ա����ȫ����Ϣ(��ѯ�������������)
SELECT
	*
FROM
	t_employee
WHERE
	(sal,job) = (
		SELECT
			sal,job
		FROM
			t_employee
		WHERE
			ename = 'WARD'
	)
AND ename != 'WARD';

-- ��ѯԱ����Ϣ��Ҫ��Ա���Ĳ��ţ����ڲ��ű����м�¼
SELECT
	*
FROM
	t_employee
WHERE
	deptno IN (SELECT deptno FROM t_dept);

-- ��ѯԱ����Ϣ��Ҫ��Ա���Ĳ��ţ��ڲ��ű���û�м�¼
SELECT
	*
FROM
	t_employee
WHERE
	deptno NOT IN (SELECT deptno FROM t_dept);

-- �ѿ�����
-- ��ѯԱ����Ϣ��Ա�����ڵĲ��ŵ���Ϣ
SELECT
	*
FROM
	t_employee emp,
	t_dept dept
WHERE
	emp.deptno = dept.deptno;
	
-- �����ӣ���Եѿ������еĽ��ɸѡ������������Ҫ�Ľ�������ѯ�����߶�ƥ��ļ�¼
SELECT
	*
FROM
	t_employee emp INNER JOIN
	t_dept dept
ON
	emp.deptno = dept.deptno;
	
-- ��ѯÿ��Ա���ı�š�������ְλ���������ƺͲ�������λ��
SELECT
	emp.empno,
	emp.ename,
	emp.job,
	dept.dname,
	dept.loc
FROM
	t_employee emp,
	t_dept dept
WHERE
	emp.deptno = dept.deptno;

-- ������д����
SELECT
	emp.empno,
	emp.ename,
	emp.job,
	dept.dname,
	dept.loc
FROM
	t_employee emp INNER JOIN
	t_dept dept
ON
	emp.deptno = dept.deptno;

-- ������
-- ��ѯԱ����������ְλ�������쵼������
SELECT
	t1.ename,
	t1.job,
	t2.ename
FROM
	t_employee t1,
	t_employee t2
WHERE
	t1.mgr = t2.empno;

SELECT
	t1.ename,
	t1.job,
	t2.ename
FROM
	t_employee t1 INNER JOIN
	t_employee t2
ON
	t1.mgr = t2.empno;

-- ��ѯÿ��Ա���ı�š��������������ʡ�ְλ���쵼�������������ơ���������λ��
SELECT
	t1.empno,
	t1.ename,
	t1.sal,
	t1.job,
	t2.ename,
	d.dname,
	d.loc
FROM
	t_dept d,
	t_employee t1,
	t_employee t2
WHERE
	t1.deptno = d.deptno
AND t1.mgr = t2.empno;

SELECT
	t1.empno,
	t1.ename,
	t1.sal,
	t1.job,
	t2.ename,
	d.dname,
	d.loc
FROM
	t_dept d
INNER JOIN t_employee t1 ON t1.deptno = d.deptno
INNER JOIN t_employee t2 ON t1.mgr = t2.empno;

-- ������ �� �������ӡ���������
-- ��ѯԱ����������ְλ�������쵼������
-- LEFT JOIN ��= �����ӵĽ��+��ߵı������������в�ƥ��Ľ�����ұ߲�null
SELECT
	t1.ename,
	t1.job,
	t2.ename
FROM
	t_employee t1 LEFT JOIN
	t_employee t2
ON
	t1.mgr = t2.empno;

-- ��ѯÿ��Ա���ı�š�������ְλ���������ƺͲ�������λ��
SELECT
	t.empno,
	t.ename,
	t.job,
	d.dname,
	d.loc
FROM
	t_employee t LEFT JOIN
	t_dept d
ON
	t.deptno = d.deptno;
	
-- t_dept d RIGHT JOIN	t_employee t  �൱��t_employee t LEFT JOIN t_dept d
-- �����ң�����Ѷ�Ӧ�����ϵı��еļ�¼ȫ������ѯ����������һ������null

-- �ϲ���ѯ�����
-- union/union all
SELECT
	*
FROM
	t_employee
WHERE
	empno IN (7369, 7499)
UNION ALL
	SELECT
		*
	FROM
		t_employee
	WHERE
		empno IN (7499, 7369);
		
-- union ȥ�أ���union all��ȥ�ء�

-- ���ݵĲ��롢���ݵ�ɾ�������ݵĸ���
-- �������ݱ��������͡��������Լ����
-- ���ݿ�����еĹ�ϵ��һ��һ��һ�Զࡢ��Զ�
-- JDBC - ��Ŀ 


--  ������
-- ����һ��ѧ����student������ѧ�š�����������
create table student(
	sno int,
	sname varchar(10),
	age int
);

-- �鿴��Ľṹ
desc student;

-- ɾ��ָ����
drop table student;

-- ����һ��ѧ����student������ѧ�š����֤���롢ѧ����������ߣ�����2λС����������
-- Java�е������������ͣ�byte short int long
-- Mysql�е������������ͣ�tinyint smallint mediumint int/integer bigint 
-- Mysql�е��ַ������ͣ�char varchar
-- Mysql�еĸ������ͣ�float��double��decimal���������ͣ�
-- Mysql�е����ڣ�date datetime timestamp year
create table student(
	sno int,
	pid char(18),
	sname varchar(20),
	height double(3,2), 
	birthday date
);

-- Լ����Ϊ�˱�֤���ݵ������ԡ�����������
-- ����ѧ����Ҫ��ѧ�����Ψһ���Ҳ���Ϊnull�����֤���벻��Ϊnull�����Ĭ��ֵ��0.00������Ĭ��ֵ��1990-1-1
-- public static final int AGE = 
create table student(
	sno int unique not null,
	pid char(18) not null,
	sname varchar(20),
	height double(3,2) default 0.00, 
	birthday date default '1990-01-01'
);

-- ����Լ��
-- 1��ҵ����ָ����ĳһ�л���У��ܹ�Ψһ��ȷ��һ����¼
-- 2��Լ���ϣ���ͬ�� unique not null
create table student(
	sno int unsigned primary key auto_increment,
	pid char(18) not null,
	sname varchar(20),
	height double(3,2) default 0.00, 
	birthday date default '1990-01-01'
);

-- �������ݱ����£�
create table student(
	sno int primary key auto_increment,
	sname varchar(20),
	age int
);
-- ��������
insert into student values(1,'����',20);
insert into student(sname,age) values('�ŷ�',21);
insert into student values(default,'�ŷ�',20);
-- ���������¼
insert into student(sname,age) values('�ŷ�1',20),('�ŷ�2',20),('�ŷ�3',20);

-- ɾ��ȫ������
delete from student ;

-- ������ɾ������
delete from student where sid = 1;

-- �޸����� ��ȥ��ѯһ�£���ȥ���»�ɾ��
update student set sname = '����' where sid = 2;
update student set sname='�ŷ�',age=21 where sid = 2;

-- ��ձ�Ч�ʺܸߣ�
truncate student;-- �ܹ����Զ�������ֵ�ع�Ϊ1


-- ���ݿ�����еĹ�ϵ��һ��һ��һ�Զࡢ��Զ�
-- һ��һ һ�Զ�
-- ��ѯ����ͬѧ�����е����±��������
select atitle,acontent from aritcle where sid = (select sid from student where sname = '����');

-- ��ѯѧ�����������䣬�Լ���ѧ����Ӧ�����±�������ݵ�������Ϣ
select sname,age,atitle,acontent from student stu ,aritcle ar where stu.sid = ar.sid;


-- ��Զ࣬��Ҫ�����м��
-- һ��ѧ������ѡ����ſγ� һ���γ̿��Ա����ѧ��ѡ��
-- ��ѯ����ͬѧѡ������Щ�γ�
SELECT
	*
FROM
	course
WHERE
	cid IN (
		SELECT
			cid
		FROM
			student_course
		WHERE
			sid = (
				SELECT
					sid
				FROM
					student
				WHERE
					sname = '����'
			)
	);
																																							�������
-- ʹ�ö������=================================================================================================================================
SELECT
	cou.cname,
	cou.clen
FROM
	student stu,
	course cou,
	student_course sc
WHERE
	stu.sid = sc.sid
AND cou.cid = sc.cid
and stu.sname = '����';



-- ��ѯ����Щͬѧѡ����Java�γ�
SELECT
	*
FROM
	student
WHERE
	sid IN (
		SELECT
			sid
		FROM
			student_course
		WHERE
			cid = (
				SELECT
					cid
				FROM
					course
				WHERE
					cname = 'Java'
			)
	)


SELECT
	stu.sname,
	stu.age
FROM
	student stu,
	course cou,
	student_course sc
WHERE
	stu.sid = sc.sid
AND cou.cid = sc.cid
AND cou.cname = 'Java';

-- ��ѯȫ��ѧ����Ϣ������ѡ��Ŀγ���Ϣ
SELECT
	stu.sname,
	stu.age,
	cou.cname,
	cou.clen
FROM
	student stu,
	course cou,
	student_course sc
WHERE
	stu.sid = sc.sid
AND cou.cid = sc.cid;


























































