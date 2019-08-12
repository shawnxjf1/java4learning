-- 状态：在mysql客户端执行通过
-- sql 语句执行：source dssp.sql
-- 注意  1. mysql: 表名 列名 PRIMARYKEY(ID) 都不需要加'  2.attention:-- 与解释的语句必须包含空格，否则在securecrt里执行报错。
-- Create the database named 'mybatis'.  
-- It's OK to use ', not OK to use ' or " surrounding the database name to prevent it from being interpreted as a keyword if possible.  
CREATE DATABASE IF NOT EXISTS dssp  -- IF NOT  EXISTS 'dssp' dssp 不需要打''不然会报错
DEFAULT CHARACTER SET = 'UTF8';  

-- use dssp database
use dssp;

-- Drop the table if exists
DROP TABLE IF EXISTS indexMeta;

-- Create a table named 'indexMeta'    attention:-- 与解释的语句必须包含空格，否则在securecrt里执行报错。

CREATE TABLE indexMeta (
    indexId int(20) NOT NULL AUTO_INCREMENT,    -- 开始写了long 出错，mysql没有long  int范围（-2147483648 to 2147483647），还有其他数据类型bigint，double ....
    indexName varchar(200) NOT NULL  COMMENT '索引名称',  
	typeName varchar(200) NOT NULL COMMENT 'type名称',
    indexAlias varchar(200) DEFAULT NULL COMMENT '索引别名',
    validBegin 	date DEFAULT NULL COMMENT '有效开始时间',
	validEnd  date DEFAULT NULL COMMENT '有效结束时间',
	bizType  varchar(20) COMMENT '业务名称',
	sysName  varchar(20) COMMENT '系统名称',
	createTime datetime  COMMENT '创建时间',
	updatetime datetime COMMENT '更新时间',
	status  int(1) COMMENT '1表示有效 0表示无效',
    description varchar(200) DEFAULT NULL COMMENT '描述',  
    PRIMARY KEY (indexId)  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;  
 
-- Insert a test record  
Insert INTO indexMeta
(indexName,typeName,indexAlias,validBegin,validEnd,bizType,sysName,createTime,updateTime,status,description) 
VALUES ('pospIndex1', 'pospType1', 'pospAlias', str_to_date('2000-1-1','%Y-%m-%d'),str_to_date('3069-1-1','%Y-%m-%d'),'posp','dssp',now(),now(),'1','dssp 检索posp数据，获取相应的index');

-- Drop the table if exists  
DROP TABLE IF EXISTS searchRule;  
      
-- Create a table named 'Article'  
CREATE TABLE searchRule (  
    ruleId int NOT NULL AUTO_INCREMENT,  
    ruleName varchar(30) NOT NULL  COMMENT '规则名称',  
    searchField varchar(30) NOT NULL COMMENT '基于哪个字段查询',  
    parseType varchar(25) NOT NULL COMMENT '查询字段解析类型 1.date 2.number 3.string 4.collection' ,  
    matchBegin varchar(30) DEFAULT NULL COMMENT '查询起始值',
	matchEnd  varchar(30) DEFAULT NULL COMMENT '查询结束值',
	matchCollection varchar(50) DEFAULT NULL COMMENT '查询集合',
	indexId  long NOT NULL COMMENT '外键->指向indexMeta.indexId',
	bizType  varchar(25) NOT NULL COMMENT '业务类型 posp tv，要求前端提供,对于遗留系统则采用特征映射匹配分解出 bizType',
	sysName varchar(20) NOT NULL COMMENT '应用名称,比如dssp  日志收集',
	createTime  datetime NOT NULL COMMENT '创建时间',
	updateTime  datetime NOT NULL COMMENT '更新时间',
	status  int(2) NOT NULL COMMENT '-1：删除 0：无效  1：有效',
	description varchar(200) COMMENT '描述',
	PRIMARY KEY (ruleId)  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;  
      
-- Add several test records  
INSERT INTO searchRule 
(ruleName, searchField, parseType, matchBegin, matchEnd, matchCollection, indexId, bizType, sysName, createTime, updateTime, status, description)
VALUES  
('pospRule1', 'TXNTIM', 'date','20140527000000','20140527235959','','1','posp','dssp',now(),now(),'1','posp 规则1插入'),
('pospRule2', 'TXNTIM', 'date','20140528000000','20140528235959','','1','posp','dssp',now(),now(),'1','posp 规则2插入');

-- ruleId=3 rule 3之间不能有空格
 delete  from searchRule where ruleId=3;