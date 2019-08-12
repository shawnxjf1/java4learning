--  datetime类型转string类型
```sql
        date_format(provider_obj.audit_time,'%Y-%m-%d %H:%i') audit_time,
```

-- 日期的比较，sql语句中可以直接通过字符串比较日期。
select t.audit_time,t.* from provider_obj t where t.audit_time > '2018-05-24 11:24';
select t.audit_time,t.* from provider_obj t where t.audit_time > '2018-05-24 11';
``` 工程中的代码如下：
 <if test="auditBeginTime != null and auditBeginTime != '' ">
            and audit_time  <![CDATA[ >= ]]> #{auditBeginTime}
</if>
```