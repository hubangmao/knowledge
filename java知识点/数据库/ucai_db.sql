/*
Navicat MySQL Data Transfer

Source Server         : Localhost
Source Server Version : 50548
Source Host           : localhost:3306
Source Database       : ucai_db

Target Server Type    : MYSQL
Target Server Version : 50548
File Encoding         : 65001

Date: 2016-06-15 16:53:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(20) DEFAULT NULL,
  `clen` int(11) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1001', 'Java', '30');
INSERT INTO `course` VALUES ('1002', 'Servlet', '10');
INSERT INTO `course` VALUES ('1003', 'JDBC', '5');
INSERT INTO `course` VALUES ('1004', '数据库', '5');
INSERT INTO `course` VALUES ('1005', 'Android', '50');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `sname` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '尚朋', '20');
INSERT INTO `student` VALUES ('2', '张锋', '21');

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_course
-- ----------------------------
INSERT INTO `student_course` VALUES ('1', '1', '1001');
INSERT INTO `student_course` VALUES ('2', '1', '1002');
INSERT INTO `student_course` VALUES ('3', '1', '1003');
INSERT INTO `student_course` VALUES ('4', '1', '1004');
INSERT INTO `student_course` VALUES ('5', '1', '1005');
INSERT INTO `student_course` VALUES ('6', '2', '1001');
INSERT INTO `student_course` VALUES ('7', '2', '1002');
INSERT INTO `student_course` VALUES ('8', '2', '1003');

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept` (
  `deptno` int(11) NOT NULL,
  `dname` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `loc` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept` VALUES ('10', 'ACCOUNTING', 'NEW YORK');
INSERT INTO `t_dept` VALUES ('20', 'RESEARCH', 'DALLAS');
INSERT INTO `t_dept` VALUES ('30', 'SALES', 'CHICAGO');
INSERT INTO `t_dept` VALUES ('40', 'OPERATIONS', 'BOSTON');

-- ----------------------------
-- Table structure for t_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `empno` int(11) DEFAULT NULL,
  `ename` varchar(20) DEFAULT NULL,
  `job` varchar(40) DEFAULT NULL,
  `mgr` int(11) DEFAULT NULL,
  `hiredate` date DEFAULT NULL,
  `sal` double(10,2) DEFAULT NULL,
  `comm` double(10,2) DEFAULT NULL,
  `deptno` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES ('7369', 'SMITH', 'CLERK', '7902', '2011-03-12', '800.00', null, '20');
INSERT INTO `t_employee` VALUES ('7499', 'ALLEN', 'SALESMAN', '7698', '2012-03-12', '1600.00', '300.00', '30');
INSERT INTO `t_employee` VALUES ('7521', 'WARD', 'SALESMAN', '7698', '2013-03-12', '1250.00', '500.00', '30');
INSERT INTO `t_employee` VALUES ('7566', 'JONES', 'MANAGER', '7839', '2011-03-12', '2975.00', null, '20');
INSERT INTO `t_employee` VALUES ('7654', 'MARTIN', 'SALESMAN', '7698', '2011-03-12', '1250.00', '1400.00', '30');
INSERT INTO `t_employee` VALUES ('7698', 'BLAKE', 'MANAGER', '7839', '2011-03-12', '2850.00', null, '30');
INSERT INTO `t_employee` VALUES ('7782', 'CLARK', 'MANAGER', '7839', '2015-03-12', '2450.00', null, '10');
INSERT INTO `t_employee` VALUES ('7788', 'SCOTT', 'ANALYST', '7566', '2011-03-12', '3000.00', null, '20');
INSERT INTO `t_employee` VALUES ('7839', 'KING', 'PRESIDENT', null, '2011-03-12', '5000.00', null, '10');
INSERT INTO `t_employee` VALUES ('7844', 'TURNER', 'SALESMAN', '7698', '2014-03-12', '1500.00', '0.00', '30');
INSERT INTO `t_employee` VALUES ('7876', 'ADAMS', 'CLERK', '7788', '2016-03-12', '1100.00', null, '20');
INSERT INTO `t_employee` VALUES ('7900', 'JAMES', 'CLERK', '7698', '2015-03-12', '950.00', null, '30');
INSERT INTO `t_employee` VALUES ('7902', 'FORD', 'ANALYST', '7566', '0000-00-00', '3000.00', null, '20');
INSERT INTO `t_employee` VALUES ('7934', 'MILLER', 'CLERK', '7782', '2011-03-12', '1300.00', null, '10');
INSERT INTO `t_employee` VALUES ('7999', 'SHANGPENG', 'CLERK', '7782', '2011-03-12', '1300.00', null, '50');
