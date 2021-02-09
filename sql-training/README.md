1. 求中位数
```
select id, company, salary
from
(select *, 
row_number() over(partition by company order by salary) as rn,
count(*) over(partition by company) as cnt
from employee) a
where rn between cnt/2 and cnt/2+1

#利用SQL表的主键id，在待求序列相等时，正向编号按id升序排序编号，反向编号按id降序排序编号，这样就保证了两列编号的走向处处相反，从而使得判断条件生效。
select
id,
company,
salary
from(select
id,
company,
salary,
cast(row_number() over(partition by company order by salary asc, id asc) as signed) as 'id1',
cast(row_number() over(partition by company order by salary desc, id desc) as signed) as 'id2'
from employee) as newtable
where abs(id1-id2)=1 or
id1=id2;
```

2.求某个字段的最小值, min(column)函数
根据某个字段的极值求另外一个活多个字段的值，开窗函数然后子查询rk=1
聚合值求范围(大于某个数) -> group by having count > 5
聚合值就极值(最大值，最小值) rank() over(order by count(1) desc) as rk, 然后自查询rk=1
求极值 limit 1

3.行转列 和 列转行
行转列：分组case when
```
#行专列: 如果是通过key groupby
CREATE  TABLE [StudentScores]
(
   [UserName]         NVARCHAR(20),        --学生姓名
    [Subject]          NVARCHAR(30),        --科目
    [Score]            FLOAT,               --成绩
)
SELECT 
      UserName, 
      MAX(CASE Subject WHEN '语文' THEN Score ELSE 0 END) AS '语文',
      MAX(CASE Subject WHEN '数学' THEN Score ELSE 0 END) AS '数学',
      MAX(CASE Subject WHEN '英语' THEN Score ELSE 0 END) AS '英语',
      MAX(CASE Subject WHEN '生物' THEN Score ELSE 0 END) AS '生物'
FROM dbo.[StudentScores]
GROUP BY UserName

#如果是非key groupby
CREATE TABLE [Inpours]
(
   [ID]                INT IDENTITY(1,1), 
   [UserName]          NVARCHAR(20),  --游戏玩家
    [CreateTime]        DATETIME,      --充值时间    
    [PayType]           NVARCHAR(20),  --充值类型    
    [Money]             DECIMAL,       --充值金额
    [IsSuccess]         BIT,           --是否成功 1表示成功， 0表示失败
    CONSTRAINT [PK_Inpours_ID] PRIMARY KEY(ID)
)
SELECT CONVERT(VARCHAR(10), CreateTime, 120) AS CreateTime,
       CASE PayType WHEN '支付宝'     THEN SUM(Money) ELSE 0 END AS '支付宝',
       CASE PayType WHEN '手机短信'    THEN SUM(Money) ELSE 0 END AS '手机短信',
       CASE PayType WHEN '工商银行卡'  THEN SUM(Money) ELSE 0 END AS '工商银行卡',
       CASE PayType WHEN '建设银行卡'  THEN SUM(Money) ELSE 0 END AS '建设银行卡'
FROM Inpours
GROUP BY CreateTime, PayType
```

列转行: 主要是通过UNION ALL ,MAX来实现
```
CREATE TABLE ProgrectDetail
(
    ProgrectName         NVARCHAR(20), --工程名称
    OverseaSupply        INT,          --海外供应商供给数量
    NativeSupply         INT,          --国内供应商供给数量
    SouthSupply          INT,          --南方供应商供给数量
    NorthSupply          INT           --北方供应商供给数量
)
SELECT ProgrectName, 'OverseaSupply' AS Supplier,
        MAX(OverseaSupply) AS 'SupplyNum'
FROM ProgrectDetail
GROUP BY ProgrectName
UNION ALL
SELECT ProgrectName, 'NativeSupply' AS Supplier,
        MAX(NativeSupply) AS 'SupplyNum'
FROM ProgrectDetail
GROUP BY ProgrectName
UNION ALL
SELECT ProgrectName, 'SouthSupply' AS Supplier,
        MAX(SouthSupply) AS 'SupplyNum'
FROM ProgrectDetail
GROUP BY ProgrectName
UNION ALL
SELECT ProgrectName, 'NorthSupply' AS Supplier,
        MAX(NorthSupply) AS 'SupplyNum'
FROM ProgrectDetail
GROUP BY ProgrectName
```

4.同比和环比 
**同比**一般情况下是今年第n月与去年第n月比。同比发展速度主要是为了消除季节变动的影响，用以说明本期发展水平与去年同期发展水平对比而达到的相对发展速度。如，本期2月比去年2月，本期6月比去年6月等。其计算公式为：同比发展速度的公式应该改成：同比发展速度=本期发展水平/去年同期水平×100%；同比增长速度=（本期发展水平-去年同期水平）/去年同期水平×100%。在实际工作中，经常使用这个指标，如某年、某季、某月与上年同期对比计算的发展速度，就是同比发展速度。   
`同比增长率=（本期数-同期数）/|同期数|×100%`
**环比**表示连续2个统计周期（比如连续两月）内的量的变化比。环比增长率=（本期数-上期数）/上期数×100%。 反映本期比上期增长了多少；环比发展速度，一般是指报告期水平与前一时期水平之比，表明现象逐期的发展速度。
`环比增长率=（本期数-上期数）/上期数×100%`
```
每个时间窗口的标示为当个时间窗口的第一天
# 开窗后相除
select distinct city,count(case when datetime='20190924' then orderId else null end) over (partition by city) / count(case when datetime=date_sub('20190924',interval 1 week) then orderId else null end) over (partition by city) as '比例' from job_interview;
# 相除后group by
select city,count(case when datetime='20190924' then orderId else null end) / count(case when datetime=date_sub('20190924',interval 1 week) then orderId else null end)  as '比例' from job_interview group by city;
```
```
#当前月份的销售额
select 
date_format(a.create_time,'%Y-%m') as now_time,
sum( a.out_stock_money) AS  a_out_money,
sum( a.freight_money) AS a_fre_money,
CONVERT (sum( a.out_stock_money)-sum( a.freight_money),DECIMAL ( 10,2 )) as a_sale_money
from food_orders_info a
where a.state=1
and a.order_state=60
GROUP BY date_format(a.create_time,'%Y-%m')

#上个月的销售额
select 
date_format(DATE_ADD(b.create_time,INTERVAL 1 MONTH ), '%Y-%m') 	as now_time,
sum( b.out_stock_money) AS bout_money,
sum( b.freight_money) AS bfe_money,
CONVERT (sum(b.out_stock_money)-sum( b.freight_money),DECIMAL ( 10,2 )) as bsale_money
from food_orders_info b
where b.state=1
and b.order_state=60
GROUP BY date_format(DATE_ADD(b.create_time,INTERVAL 1 MONTH ), '%Y-%m')

DATE_ADD(b.create_time,INTERVAL 1 MONTH )  #表示加一个月

#当前月和上月销售额进行关联
SELECT
	now_sale.now_time,
	now_sale.now_year,
CASE
		
		WHEN a_sale_money IS NULL 
		OR a_sale_money = 0 THEN
			0 ELSE a_sale_money 
		END this_sale_money,
CASE
		
		WHEN b_sale_money IS NULL 
		OR b_sale_money = 0 THEN
			0 ELSE b_sale_money 
		END last_month_money,

CASE
		
		WHEN b_sale_money IS NULL 
		OR b_sale_money = 0 THEN
			0 ELSE ( CONVERT ( ( ( a_sale_money - b_sale_money ) / b_sale_money ) * 100, DECIMAL ( 10, 2 ) ) ) 
		END month_ratio
FROM
	(
	SELECT
		date_format( a.create_time, '%Y-%m' ) AS now_time,
		date_format( a.create_time, '%Y' ) AS now_year,
		sum( a.out_stock_money ) AS a_out_money,
		sum( a.freight_money ) AS a_fre_money,
		CONVERT ( sum( a.out_stock_money ) - sum( a.freight_money ), DECIMAL ( 10, 2 ) ) AS a_sale_money 
	FROM
		food_orders_info a 
	WHERE
		a.state = 1 
		AND a.order_state = 60 
	
	GROUP BY
		date_format( a.create_time, '%Y-%m' ),
		date_format( a.create_time, '%Y' ) 
	ORDER BY
		date_format( a.create_time, '%Y-%m' ) ASC 
	) now_sale
	LEFT JOIN (
	SELECT
		date_format( DATE_ADD( b.create_time, INTERVAL 1 MONTH ), '%Y-%m' ) AS now_time,
		sum( b.out_stock_money ) AS bout_money,
		sum( b.freight_money ) AS bfe_money,
		CONVERT ( sum( b.out_stock_money ) - sum( b.freight_money ), DECIMAL ( 10, 2 ) ) AS b_sale_money 
	FROM
		food_orders_info b 
	WHERE
		b.state = 1 
		AND b.order_state = 60 
	GROUP BY
		date_format( DATE_ADD( b.create_time, INTERVAL 1 MONTH ), '%Y-%m' ) 
	ORDER BY
		date_format( DATE_ADD( b.create_time, INTERVAL 1 MONTH ), '%Y-%m' ) ASC 
	) old_sale ON now_sale.now_time = old_sale.now_time
ORDER BY
	now_sale.now_year DESC,
	now_sale.now_time ASC

由于数据库销售金额有为空的情况，所以的加上判断条件，让为空或被除数为空的值，返回0。
再查询去年同期的销售金额

SELECT
		date_format( DATE_ADD( c.create_time, INTERVAL 1 YEAR ), '%Y-%m' ) AS last_year_time,
		sum( c.out_stock_money ) AS bout_money,
		sum( c.freight_money ) AS bfe_money,
		CONVERT ( sum( c.out_stock_money ) - sum( c.freight_money ), DECIMAL ( 10, 2 ) ) AS last_year_money 
	FROM
		food_orders_info c 
	WHERE
		c.state = 1 
		AND c.order_state = 60 
	GROUP BY
	date_format( DATE_ADD( c.create_time, INTERVAL 1 YEAR ), '%Y-%m' ) 

DATE_ADD( c.create_time, INTERVAL 1 YEAR ) 表示加一年

查询季度主要使用 QUARTER ( 时间)函数
```

  
5.SQL with as语法
WITH AS短语，也叫做子查询部分（subquery factoring），可以定义一个SQL片断，该SQL片断会被整个SQL语句用到。可以使SQL语句的可读性更高，也可以在UNION ALL的不同部分，作为提供数据的部分。
```
with
cte1 as
(
    select * from table1 where name like 'abc%'
),
cte2 as
(
    select * from table2 where id > 20
),
cte3 as
(
    select * from table3 where price < 100
)
select a.* from cte1 a, cte2 b, cte3 c where a.id = b.id and a.id = c.i
```

6.count+if函数结合使用
count函数
mysql中count函数用于统计数据表中的行的总数，或者根据查询结果统计某一列包含的行数，常见的用法如下 count(*) 计算表的总行数，包括空值 count(字段名) 计算指定列下的总行数，忽略空值（这点很重要，后面我们将利用这个特性）
if(expr, v1, v2)函数
if(expr, v1, v2) 函数的意思是，如果表达式expr为true（expr<>0 and expr <> NULL），则if()返回的是v1，否则返回v2
```
select count( if( bookTyoe = ‘文学类’, id, null ) ) from table group by press
当bookType=文学类时，会返回对应的id的值，否则放回null，对于指定列的count函数，null是被忽略的，这样就得到了我们想要的统计数量了。
想要去掉重复的id再统计，还可以在if前面加上DISTINCT，即 count( DISTINCT if( type in (2, 3), id, null ) )
```

7.SQL的select语句完整的执行顺序
SQL Select 语句完整的执行顺序：
```
1、from子句组装来自不同数据源的数据；
2、where子句基于指定的条件对记录行进行筛选；
3、group by子句将数据划分为多个分组；
4、使用聚集函数进行计算；
5、使用having子句筛选分组；
6、计算所有的表达式；
7、使用order by对结果集进行排序。
8、select 集合输出。
```
