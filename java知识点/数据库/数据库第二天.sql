增insert into student values(1,'尚朋',20);
删delete from student ;
改update student set sname = '尚朋' where sid = 2;
查 select *from table
-- 查询每个部门的工资的总和  （分组查询）
select deptno,sum(sal) from t_employee group by deptno;

-- sum、count 、max、min、avg ： 组函数 和分组group by一起使用
-- group by语句中，select后面跟组函数或者group by后面跟的字段才有意义。

-- 查询每个部门的人数、工资总和、平均工资、最高工资和最低工资
select deptno,count(*),sum(sal),avg(sal),max(sal),min(sal) from t_employee group by deptno;

-- 按照部门编号和入职日期分组，统计每组的员工人数、工资总和和平均工资
select deptno,hiredate,count(*),sum(sal),avg(sal) from t_employee group by deptno,hiredate;

-- 按照部门编号分组，查询每组平均工资大于2000的部门的人数、工资总和、平均工资。
select deptno,count(*),sum(sal),avg(sal) from t_employee group by deptno having avg(sal) > 2000;

-- where 和 having的区别
-- 1、having可以用在group by后面，where不可以
-- 2、在where和having同时可以使用的地方，使用where

-- 子查询
-- 查询工资比SMITH工资高的所有的员工的全部信息
select * from t_employee where sal > (select sal from t_employee where ename = 'SMITH');

-- 查询工资和WARD一样的其他员工的全部信息(查询结果不包括本人)
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

-- 查询工资和职位都和WARD一样的其他员工的全部信息(查询结果不包括本人)
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

-- 查询员工信息，要求员工的部门，都在部门表中有记录
SELECT
	*
FROM
	t_employee
WHERE
	deptno IN (SELECT deptno FROM t_dept);

-- 查询员工信息，要求员工的部门，在部门表中没有记录
SELECT
	*
FROM
	t_employee
WHERE
	deptno NOT IN (SELECT deptno FROM t_dept);

-- 笛卡尔积
-- 查询员工信息和员工所在的部门的信息
SELECT
	*
FROM
	t_employee emp,
	t_dept dept
WHERE
	emp.deptno = dept.deptno;
	
-- 内连接：针对笛卡尔积中的结果筛选出来的我们需要的结果，会查询到两边都匹配的记录
SELECT
	*
FROM
	t_employee emp INNER JOIN
	t_dept dept
ON
	emp.deptno = dept.deptno;
	
-- 查询每个员工的编号、姓名、职位、部门名称和部门所在位置
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

-- 内连接写法：
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

-- 自连接
-- 查询员工的姓名、职位和他的领导的姓名
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

-- 查询每个员工的编号、姓名、基本工资、职位、领导姓名、部门名称、部门所在位置
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

-- 外连接 ： 左外连接、右外连接
-- 查询员工的姓名、职位和他的领导的姓名
-- LEFT JOIN ：= 内连接的结果+左边的表中在内连接中不匹配的结果，右边补null
SELECT
	t1.ename,
	t1.job,
	t2.ename
FROM
	t_employee t1 LEFT JOIN
	t_employee t2
ON
	t1.mgr = t2.empno;

-- 查询每个员工的编号、姓名、职位、部门名称和部门所在位置
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
	
-- t_dept d RIGHT JOIN	t_employee t  相当于t_employee t LEFT JOIN t_dept d
-- 左还是右，都会把对应方向上的表中的记录全部给查询出来，另外一个方向补null

-- 合并查询结果集
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
		
-- union 去重，而union all不去重。

-- 数据的插入、数据的删除、数据的更新
-- 创建数据表（数据类型、主外键等约束）
-- 数据库设计中的关系：一对一、一对多、多对多
-- JDBC - 项目 


-- 查询每个部门的工资的总和  （分组查询）
select deptno,sum(sal) from t_employee group by deptno;

-- sum、count 、max、min、avg ： 组函数 和分组group by一起使用
-- group by语句中，select后面跟组函数或者group by后面跟的字段才有意义。

-- 查询每个部门的人数、工资总和、平均工资、最高工资和最低工资
select deptno,count(*),sum(sal),avg(sal),max(sal),min(sal) from t_employee group by deptno;

-- 按照部门编号和入职日期分组，统计每组的员工人数、工资总和和平均工资
select deptno,hiredate,count(*),sum(sal),avg(sal) from t_employee group by deptno,hiredate;

-- 按照部门编号分组，查询每组平均工资大于2000的部门的人数、工资总和、平均工资。
select deptno,count(*),sum(sal),avg(sal) from t_employee group by deptno having avg(sal) > 2000;

-- where 和 having的区别
-- 1、having可以用在group by后面，where不可以
-- 2、在where和having同时可以使用的地方，使用where

-- 子查询
-- 查询工资比SMITH工资高的所有的员工的全部信息
select * from t_employee where sal > (select sal from t_employee where ename = 'SMITH');

-- 查询工资和WARD一样的其他员工的全部信息(查询结果不包括本人)
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

-- 查询工资和职位都和WARD一样的其他员工的全部信息(查询结果不包括本人)
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

-- 查询员工信息，要求员工的部门，都在部门表中有记录
SELECT
	*
FROM
	t_employee
WHERE
	deptno IN (SELECT deptno FROM t_dept);

-- 查询员工信息，要求员工的部门，在部门表中没有记录
SELECT
	*
FROM
	t_employee
WHERE
	deptno NOT IN (SELECT deptno FROM t_dept);

-- 笛卡尔积
-- 查询员工信息和员工所在的部门的信息
SELECT
	*
FROM
	t_employee emp,
	t_dept dept
WHERE
	emp.deptno = dept.deptno;
	
-- 内连接：针对笛卡尔积中的结果筛选出来的我们需要的结果，会查询到两边都匹配的记录
SELECT
	*
FROM
	t_employee emp INNER JOIN
	t_dept dept
ON
	emp.deptno = dept.deptno;
	
-- 查询每个员工的编号、姓名、职位、部门名称和部门所在位置
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

-- 内连接写法：
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

-- 自连接
-- 查询员工的姓名、职位和他的领导的姓名
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

-- 查询每个员工的编号、姓名、基本工资、职位、领导姓名、部门名称、部门所在位置
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

-- 外连接 ： 左外连接、右外连接
-- 查询员工的姓名、职位和他的领导的姓名
-- LEFT JOIN ：= 内连接的结果+左边的表中在内连接中不匹配的结果，右边补null
SELECT
	t1.ename,
	t1.job,
	t2.ename
FROM
	t_employee t1 LEFT JOIN
	t_employee t2
ON
	t1.mgr = t2.empno;

-- 查询每个员工的编号、姓名、职位、部门名称和部门所在位置
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
	
-- t_dept d RIGHT JOIN	t_employee t  相当于t_employee t LEFT JOIN t_dept d
-- 左还是右，都会把对应方向上的表中的记录全部给查询出来，另外一个方向补null

-- 合并查询结果集
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
		
-- union 去重，而union all不去重。

-- 数据的插入、数据的删除、数据的更新
-- 创建数据表（数据类型、主外键等约束）
-- 数据库设计中的关系：一对一、一对多、多对多
-- JDBC - 项目 


--  创建表
-- 创建一个学生表student，包含学号、姓名和年龄
create table student(
	sno int,
	sname varchar(10),
	age int
);

-- 查看表的结构
desc student;

-- 删除指定表
drop table student;

-- 创建一个学生表student，包含学号、身份证号码、学生姓名、身高（保留2位小数），生日
-- Java中的整数数据类型：byte short int long
-- Mysql中的整数数据类型：tinyint smallint mediumint int/integer bigint 
-- Mysql中的字符串类型：char varchar
-- Mysql中的浮点类型：float、double、decimal（定点类型）
-- Mysql中的日期：date datetime timestamp year
create table student(
	sno int,
	pid char(18),
	sname varchar(20),
	height double(3,2), 
	birthday date
);

-- 约束：为了保证数据的完整性、避免脏数据
-- 创建学生表，要求学生编号唯一，且不能为null，身份证号码不能为null，身高默认值是0.00，生日默认值是1990-1-1
-- public static final int AGE = 
create table student(
	sno int unique not null,
	pid char(18) not null,
	sname varchar(20),
	height double(3,2) default 0.00, 
	birthday date default '1990-01-01'
);

-- 主键约束
-- 1、业务上指的是某一列或多列，能够唯一的确定一条记录
-- 2、约束上，等同于 unique not null
create table student(
	sno int unsigned primary key auto_increment,
	pid char(18) not null,
	sname varchar(20),
	height double(3,2) default 0.00, 
	birthday date default '1990-01-01'
);

-- 先有数据表如下：
create table student(
	sno int primary key auto_increment,
	sname varchar(20),
	age int
);
-- 插入数据
insert into student values(1,'尚朋',20);
insert into student(sname,age) values('张锋',21);
insert into student values(default,'张锋',20);
-- 插入多条记录
insert into student(sname,age) values('张锋1',20),('张锋2',20),('张锋3',20);

-- 删除全部数据
delete from student ;

-- 按条件删除数据
delete from student where sid = 1;

-- 修改数据 先去查询一下，再去更新或删除
update student set sname = '尚朋' where sid = 2;
update student set sname='张锋',age=21 where sid = 2;

-- 清空表（效率很高）
truncate student;-- 能够将自动递增的值回归为1


-- 数据库设计中的关系：一对一、一对多、多对多
-- 一对一 一对多
-- 查询尚朋同学的所有的文章标题和内容
select atitle,acontent from aritcle where sid = (select sid from student where sname = '尚朋');

-- 查询学生姓名和年龄，以及该学生对应的文章标题和内容等文章信息
select sname,age,atitle,acontent from student stu ,aritcle ar where stu.sid = ar.sid;


-- 多对多，需要建立中间表
-- 一个学生可以选择多门课程 一个课程可以被多个学生选择
-- 查询尚朋同学选择了哪些课程
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
					sname = '尚朋'
			)
	);
																																							多表联查
-- 使用多表联查=================================================================================================================================
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
and stu.sname = '尚朋';



-- 查询有哪些同学选择了Java课程
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

-- 查询全部学生信息和他所选择的课程信息
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


























































