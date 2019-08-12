eclipse outline可以显示.md的大纲
##修改默认字符集
修改myssql字符集为UTF-8使其支持中文
方法1  - 永久更改：
配置/etc/my.cnf文件：（linux 下local为UTF-8);
找到客户端配置[mysql] 在下面添加
default-character-set=utf8 默认字符集为utf8
在找到[mysqld] 添加
default-character-set=utf8 默认字符集为utf8
再查看编码：show variables like '%character%';

方法2(终极方法)  - 临时修改 重启之后又恢复默认值:
MySQL (mysql5)的字符集支持(Character Set Support)有两个方面：字符集(Character set)和排序方式(Collation)。对于字符集的支持细化到四个层次: 服务器(server)，数据库(database)，数据表(table)和连接(connection)。
a.修改服务器
mysql> set GLOBAL  character_set_server=utf8;
b.修改数据库
mysql> set   character_set_database=utf8;  --注意不是 set global character_set_database=utf8
c.修改连接
mysql> set NameS utf8;

注意:
执行SET NAMES utf8的效果等同于同时设定如下：
SET character_set_client='utf8';
SET character_set_connection='utf8';
SET character_set_results='utf8';

##mysql启动
一、启动方式 
1、使用 service 启动：service mysqld start
2、使用 mysqld 脚本启动：/etc/inint.d/mysqld start
3、使用 safe_mysqld 启动：safe_mysqld&

二、停止
1、使用 service 启动：service mysqld stop
2、使用 mysqld 脚本启动：/etc/inint.d/mysqld stop
3、mysqladmin shutdown

三、重启
1、使用 service 启动：service mysqld restart
2、使用 mysqld 脚本启动：/etc/inint.d/mysqld restart


##元数据查询
mysql>status
mysql> select version();