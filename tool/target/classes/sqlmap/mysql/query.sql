
--查询语句使用between and 
select * from searchRule where  updateTime between str_to_date('2016-11-1','%Y-%m-%d') and now();
  --如下方式是错误的
select * from searchRule where  str_to_date('2016-11-1','%Y-%m-%d') < updateTime < now(); --没有结果出来的