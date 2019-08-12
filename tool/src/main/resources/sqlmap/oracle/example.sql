-- 1. 以下代码为recon.queryGatewayCharge :主要演示每次只取3000条怎么取
  --<select id="queryGatewayCharge" parameterType="java.sql.Timestamp" resultMap="gatewayChargeMap">	
  --</select>
select *
		from (select row_number() over(order by update_time asc) row_number,
	               charge_id,site_charge_sn, pay_charge_sn,
	               charge_amount, charge_status,
	               create_time,update_time,
	               main_gate,sub_gate,curr_code
	          from t_charge
	         where update_time &gt;= #{begin_time} and charge_status in ('S', 'R')) t
		where t.row_number &lt;= 3000 order by t.row_number

--- 2.以下演示 sum() decode()函数怎么写
  --<select id="queryReconResult" parameterType="com.youku.paycenter.recon.biz.dto.ReconParam" resultMap="reconResultMap">
  --</select>
SELECT COUNT(*) AS TOTAL_COUNT,
		       SUM(AMOUNT) AS TOTAL_AMOUNT,
		       SUM(DECODE(RECON_STATUS, 'I', 1, 0)) AS INITIAL_COUNT,
		       SUM(DECODE(RECON_STATUS, 'I', AMOUNT, 0)) AS INITIAL_AMOUNT,
		       SUM(DECODE(RECON_STATUS, 'S', 1, 0)) AS CONSIST_COUNT,
		       SUM(DECODE(RECON_STATUS, 'S', AMOUNT, 0)) AS CONSIST_AMOUNT,
		       SUM(DECODE(RECON_STATUS, 'A', 1, 0)) AS AMOUNT_INCONSIST_COUNT,
		       SUM(DECODE(RECON_STATUS, 'A', AMOUNT, 0)) AS AMOUNT_INCONSIST_AMOUNT,
		       SUM(DECODE(RECON_STATUS, 'N', 1, 0)) AS NOT_EXISTS_COUNT,
		       SUM(DECODE(RECON_STATUS, 'N', AMOUNT, 0)) AS NOT_EXIST_AMOUNT,
		       SUM(DECODE(RECON_STATUS, 'I', 0, 'S', 0, 'A', 0, 'N', 0, 1)) AS UNKNOW_COUNT,
		       SUM(DECODE(RECON_STATUS, 'I', 0, 'S', 0, 'A', 0, 'N', 0, 1, AMOUNT)) AS UNKNOW_AMOUNT
		  FROM T_EWALLET_CHARGE
		 WHERE MODIFY_TIME &gt;= #{beginTime, jdbcType=TIMESTAMP}
		   AND MODIFY_TIME &lt; #{endTime, jdbcType=TIMESTAMP}		
	