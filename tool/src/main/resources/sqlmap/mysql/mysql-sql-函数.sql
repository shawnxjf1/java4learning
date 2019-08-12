-- ifnull 如何为null 返回''

```sql
 SELECT
        ifnull(t1.provider_name,'') providerName,
        ifnull(t2.category_name,'') categoryName
        FROM provider_obj t1
```

-- values() 表示即将插入或者更新的数据<br>

```sql

```


--- 插入可能出现的情况有：
插入 的数据不存在： 直接插入
插入的数据存在（通过主键和唯一索引确定是否存在）： a).忽略 ignore  b).更新

存在时忽略：
```sql
   <insert id="batchIgnoreInsert">
        insert ignore into provider_customer (
        customer_id, provider_id, customer_name, customer_type, contact_person,
        contact_phone, mi_chat, province, city, district,
        street,bill_company,taxpayer_register_no,account_bank,bank_account_no,remark,create_user_name,create_user,update_user,create_time,update_time)
        values
        <foreach collection="providerCustomerList" item="providerCustomer" separator=",">
            (#{providerCustomer.customerId}, #{providerCustomer.providerId}, #{providerCustomer.customerName},
            #{providerCustomer.customerType}, #{providerCustomer.contactPerson}, #{providerCustomer.contactPhone},
            ifnull(#{providerCustomer.miChat},''),ifnull(#{providerCustomer.province},''),
            ifnull(#{providerCustomer.city},''),ifnull(#{providerCustomer.district},''),ifnull(#{providerCustomer.street},''),ifnull(#{providerCustomer.billCompany},''),
            ifnull(#{providerCustomer.taxpayerRegisterNo},''),ifnull(#{providerCustomer.accountBank},''),ifnull(#{providerCustomer.bankAccountNo},''),ifnull(#{providerCustomer.remark},''),
            ifnull(#{providerCustomer.createUserName},''),ifnull(#{providerCustomer.createUser},''),ifnull(#{providerCustomer.updateUser},''),
            ifnull(#{providerCustomer.createTime},''),ifnull(#{providerCustomer.updateTime},''))
        </foreach>
    </insert>
```

更新：
```sql  
//批量插入，如果已经存在的情况就更新某些字段（on duplicate key update），values()表示每条带更新数据的字段值。
<update id="batchInsertOrUpdate">
        insert into provider_product (
        product_id,image_url,product_name,retail_price,discount_price,
        product_type,brand,barcode,description,unit,
        create_time,update_time
        ) values
        <foreach collection="list" item="product" separator=",">
            (#{product.productId},#{product.imageUrl},#{product.productName},#{product.retailPrice},#{product.discountPrice},
            #{product.productType},#{product.brand},#{product.barcode},#{product.description},#{product.unit},
            #{product.createTime},#{product.updateTime})
        </foreach>
        on duplicate key update
        image_url= values (image_url),
        product_name= values (product_name),
        retail_price= values (retail_price),
        discount_price= values (discount_price),
        product_type= values (product_type),
        brand= values (brand),
        barcode= values (barcode),
        description= values (description),
        unit= values (unit),
        create_time= values (create_time),
        update_time= values (update_time)
    </update>
```

更新：
-- 在执行REPLACE后，系统返回了所影响的行数，如果返回1，说明在表中并没有重复的记录，如果返回2，说明有一条重复记录，系统自动先调用了 DELETE删除这条记录，然后再记录用INSERT来insert这条记录。如果返回的值大于2，那说明有多个唯一索引，有多条记录被删除和insert。

```sql 
REPLACE INTO users(id, name, age)

VALUES(123, '赵本山', 50), (134,'Mary',15);

REPLACE也可以使用SET语句

REPLACE INTO users SET id = 123, name = '赵本山', age = 50;
```

--  更新可能出现的情况有：
更新的数据存在：直接更新
更新的数据不存在： 执行插入操作

