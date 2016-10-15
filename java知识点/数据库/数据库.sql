-- 连接数据库
mysql -hlocalhost -P3306 -uroot -proot

-- 退出数据库 
exit 
quit
\q

-- 查看全部等数据库
show databases;

-- 使用具体某个数据库 
use mysql;

-- 查看数据库中的所有表
show tables;

-- 查看某表中的全部数据 
select * from user;

-- SQL：结构化查询语言 （指令的合集）  DDL DML DCL 
-- 操作数据库 数据表 数据表的数据

-- 创建数据库
create database ucai_db;

-- 删除数据库
drop database ucai_db;

-- 操作表中的数据：增删改查

-- 查询全部员工信息
select * from t_employee;
-- 或者
select empno,ename,job,mgr,hiredate,sal,comm,deptno from t_employee;

-- 查询所有员工的姓名、工号和岗位
select ename,empno,job from t_employee;

-- 查询该公司所有的岗位
select distinct job from t_employee;

-- 查询该公司所有员工的姓名、月薪，年薪（sql语句支持+-*/%这些数学操作）
select ename,sal,sal*12 from t_employee;
select ename,sal,sal*12 as yearSal from t_employee;
select ename,sal,sal*12 yearSal from t_employee;

-- 完成查询结构的拼接
select concat(ename,"的年薪为",sal*12,"美元") from t_employee;
-- 也可以配合别名一起使用
select concat(ename,"的年薪为",sal*12,"美元") as yearSal from t_employee;

-- 查询岗位是CLERK的所有的员工信息
select * from t_employee where job = 'CLERK';

-- 查询岗位为CLERK，并且工资大于1000的员工信息
select * from t_employee where job = 'CLERK' and sal > 1000;
select * from t_employee where job = 'CLERK' && sal > 1000;

-- 关系运算符：> < = != <> >= <=
-- 逻辑运算符：and && or || not

-- 查询工资在800--1500之间的员工信息
select * from t_employee where sal >= 800 and sal <= 1500;
select * from t_employee where sal >= 800 && sal <= 1500;
select * from t_employee where sal between 800 and 1500;   -- 前闭后闭

-- 查询工资不在800--1500之间的员工信息
select * from t_employee where sal not between 800 and 1500;
select * from t_employee where sal > 1500 or sal< 800;

-- 查询mgr为null的员工信息
select * from t_employee where mgr is null;

-- 查询comm奖金不为null的员工信息
select * from t_employee where comm is not null;

-- 查询工号是7521、7566、7654的员工信息
select * from t_employee where empno = 7521 or empno = 7566 || empno = 7654;
select * from t_employee where empno in (7521,7566,7654);

-- 查询工号不是7521、7566、7654的员工信息
select * from t_employee where empno not in (7521,7566,7654);

-- 查询员工的姓名以A开头的员工信息
select * from t_employee where ename like 'A%';

-- 查询员工的姓名第二个字母是A的员工信息
select * from t_employee where ename like '_A%';

-- 手机智能机Android手机大屏手机千元机  模糊查询

-- 查询所有员工，按照员工的工资升序排序
select * from t_employee order by sal asc; -- asc:升序，默认不写也是升序，降序的话使用desc

-- 多字段排序：查询所有员工，按照员工工资升序排序，如果工资相同，按照员工的入职日期降序排序
select * from  t_employee order by sal asc,hiredate desc;

-- 分页查询
select * from t_employee limit 0,5;  -- 第一页
select * from t_employee limit 5,5;  -- 第二页
select * from t_employee limit 10,5;  -- 第三页

-- 通用公式 pageId页码,pageSize每页条数
select * from t_employee limit (pageId-1)*pageSize,pageSize;
select * from t_employee limit (页码-1)*每页条数,每页条数;

-- 组函数 
-- 查询公司的全部人数
select count(*) from t_employee;

-- 查询公司领取奖金的人数
select count(comm) from t_employee where comm != 0;

-- 查询员工领取奖金的平均数
select avg(comm) from t_employee where comm != 0;

-- 查询所有员工的工资的总和
select sum(sal) from t_employee;

-- 查询员工领取的全部奖金
select sum(comm) from t_employee where comm != 0;

-- 查询员工中最高工资和最低工资
select max(sal),min(sal) from t_employee;
























