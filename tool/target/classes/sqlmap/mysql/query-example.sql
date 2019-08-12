-- 有如下table：
--ruleId, num, year, month
-- 23,     5,  2018,  1
-- 25,     3,  2018,  1
-- 23,     7,  2018,  2

-- 一个表中按月份统计，该月的销售总数。
select count(1),sum(num) from table1 GROUP BY ruleId.  -- 所有月份汇总的

select count(1),sum(num) from table1 GROUP BY ruleId.  -- 按照月份进行汇总，很容易想到if lese（这里是case when）返回每个月。<br>

```sql
 select alarm_rule_id alarmRuleId,
        <foreach collection="lastYearMonthList" item="lastMonth" separator=",">
            sum(case when year(create_time) = (${nowYear}-1) and month(create_time) = ${lastMonth}
            then 1
            else 0 end) ${(nowYear-1)+'年'+lastMonth+'月'}
        </foreach>
        ,
        <foreach collection="nowMonthList" item="nowMonth" separator=",">
            sum(case when year(create_time) = ${nowYear} and month(create_time) = ${nowMonth}
            then 1
            else 0 end) ${nowYear+'年'+nowMonth+'月'}
        </foreach>
        from alarm_record
```
