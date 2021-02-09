-- SQL Problem 1
-- Assume we have loaded a flat file with patient diagnosis data into a table called “Data”. The table structure is:
--
-- Create table Data (
-- Firstname varchar(50),
-- Lastname varchar(50),
-- Date_of_birth datetime,
-- Medical_record_number varchar(20),
-- Diagnosis_date datetime,
-- Diagnosis_code varchar(20))
--
-- The data in the flat file looks like this:
--
-- 'jane','jones','2/2/2001','MRN-11111','3/3/2009','diabetes'
-- 'jane','jones','2/2/2001','MRN-11111','1/3/2009','asthma'
-- 'jane','jones','5/5/1975','MRN-88888','2/17/2009','flu'
-- 'tom','smith','4/12/2002','MRN-22222','3/3/2009','diabetes'
-- 'tom','smith','4/12/2002','MRN-33333','1/3/2009','asthma'
-- 'tom','smith','4/12/2002','MRN-33333','2/7/2009','asthma'
-- 'jack','thomas','8/10/1991','MRN-44444','3/7/2009','asthma'
--
-- You can assume that no two patients have the same firstname, lastname, and date of birth combination. However one patient might have several visits on different days.  These should all have the same medical record number.
-- The problem is this: Tom Smith has 2 different medical record numbers. Write a query that would always show all the patients
-- who are like Tom Smith – patients with more than one medical record number.
-- This problem has many solutions, but if you know SQL, you should be able to find one that uses a single query with nosubqueries.

with tmp as (
select 'jane' as Firstname ,'jones' as Lastname ,date'2/2/2001' as Date_of_birth,'MRN-11111' as Medical_record_number ,date'3/3/2009' as Diagnosis_date  ,'diabetes' as Diagnosis_code union all
select 'jane','jones',date'2/2/2001','MRN-11111',date'1/3/2009','asthma'
union all
select 'jane','jones',date'5/5/1975','MRN-88888',date'2/17/2009','flu'
union all
select 'tom','smith',date'4/12/2002','MRN-22222',date'3/3/2009','diabetes'
union all
select 'tom','smith',date'4/12/2002','MRN-33333',date'1/3/2009','asthma'
union all
select 'tom','smith',date'4/12/2002','MRN-33333',date'2/7/2009','asthma'
union all
select 'jack','thomas',date'8/10/1991','MRN-44444',date'3/7/2009','asthma'
)
select Firstname, Lastname, count(distinct Medical_record_number)
from tmp
group by Firstname,Lastname
having(count(distinct Medical_record_number)) > 1




-- SQL Problem 2
-- Given the following tables and data:
--
-- create table months (monthIndex int);
-- create table invoice(invoiceId int, month int)
-- create table lineitems(lid int, invoiceID int, charged_amount decimal(6,2), contracted_rate decimal(6,2))
--
-- Insert into months values (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12);
--
-- Insert into invoice values
--                 (1,1),
--                 (2,1),
--                 (3,2),
--                 (4,2),
--                 (5,4),
--                 (6,5),
--                 (7,5),
--                 (8,8),
--                 (9,8),
--                 (10,3),
--                 (11,3),
--                 (12,6),
--                 (13,7),
--                 (14,10),
--                 (15,11),
--                 (16,11);
--
--
-- Insert into lineitems values
--                 (1,1,1000.10,1000.10),
--                 (2,1,1500.20,1200.00),
--                 (3,1,1300.10,1300.10),
--                 (4,2,2100.30,2100.30),
--                 (5,2,1100.10,1000.10),
--                 (6,2,1100.40,1100.40),
--                 (7,3,6000.10,6000.40),
--                 (8,3,1400.10,1400.40),
--                 (9,4,4500.10,4500.10),
--                 (10,5,3300.10,3000.18),
--                 (11,5,2900.10,2900.10),
--                 (12,6,8900.10,8900.10),
--                 (13,6,2200.10,2200.10),
--                 (14,8,3700.10,3700.50),
--                 (15,9,7000.10,7000.60),
--                 (16,10,2200.10,2200.10),
--                 (17,10,2200.10,2200.10),
--                 (18,11,2200.10,2200.10),
--                 (19,12,2200.10,2200.10),
--                 (20,13,2200.10,2200.10),
--                 (21,13,2200.10,2200.10),
--                 (22,14,1100.10,1000.10),
--                 (23,14,1100.40,1100.40),
--                 (24,14,6000.10,6000.40),
--                 (25,15,1400.10,1400.40),
--                 (26,16,4500.10,4500.10),
--                 (27,16,3300.10,3000.18);
--
-- Develop a single SQL  Query that computes the total invoices amount for each month , and the percentage of change from the previous month.
-- If there are no invoices for the month , the computed value should be null.
--
-- Here is the result that the query should generate:

-- +------------+--------------------+------------------+
-- | MONTH      | TOTOALCHARGEAMOUNT | CHANGEPERCENTAGE |
-- +------------+--------------------+------------------+
-- |     1      |        8101.20     |     (null)       |
-- |     2      |        11900.30    |      31.92       |
-- |     3      |        6600.30     |     -80.29       |
-- |     4      |         6200.20    |     -6.45        |
-- |     5      |       11100.20     |     44.14        |
-- |     6      |         2200.10    |     -404.53      |
-- |     7      |        4400.20     |      50.00       |
-- |     8      |        10700.20    |      58.87       |
-- |     9      |        (null)      |     (null)       |
-- |     10     |       8200.60      |     (null)       |
-- |     11     |       9200.30      |     10.86        |
-- |     12     |        (null)      |    (null)        |
-- +------------+--------------------+------------------+

with months as (
select 1 as monthIndex union all
select 2   union all
select 3   union all
select 4   union all
select 5   union all
select 6   union all
select 7   union all
select 8   union all
select 9   union all
select 10   union all
select 11   union all
select 12
),
invoice as (
select 1 invoiceId ,1  months union all
select 2,1 union all
select 3,2 union all
select 4,2 union all
select 5,4 union all
select 6,5 union all
select 7,5 union all
select 8,8 union all
select 9,8 union all
select 10,3 union all
select 11,3 union all
select 12,6 union all
select 13,7 union all
select 14,10 union all
select 15,11 union all
select 16,11
),
lineitems as (
select 1 as lid,1 as invoiceID,1000.10 as   charged_amount,1000.10  as  contracted_rate union all
select 2,1,1500.20,1200.00 union all
select 3,1,1300.10,1300.10 union all
select 4,2,2100.30,2100.30 union all
select 5,2,1100.10,1000.10 union all
select 6,2,1100.40,1100.40 union all
select 7,3,6000.10,6000.40 union all
select 8,3,1400.10,1400.40 union all
select 9,4,4500.10,4500.10 union all
select 10,5,3300.10,3000.18 union all
select 11,5,2900.10,2900.10 union all
select 12,6,8900.10,8900.10 union all
select 13,6,2200.10,2200.10 union all
select 14,8,3700.10,3700.50 union all
select 15,9,7000.10,7000.60 union all
select 16,10,2200.10,2200.10 union all
select 17,10,2200.10,2200.10 union all
select 18,11,2200.10,2200.10 union all
select 19,12,2200.10,2200.10 union all
select 20,13,2200.10,2200.10 union all
select 21,13,2200.10,2200.10 union all
select 22,14,1100.10,1000.10 union all
select 23,14,1100.40,1100.40 union all
select 24,14,6000.10,6000.40 union all
select 25,15,1400.10,1400.40 union all
select 26,16,4500.10,4500.10 union all
select 27,16,3300.10,3000.18
)
select monthIndex,sum(charged_amount) ,--lag(sum(charged_amount),1)over(order by monthIndex) pre,
case when lag(sum(charged_amount),1)over(order by monthIndex) is null then null else (sum(charged_amount)-lag(sum(charged_amount),1)over(order by monthIndex))/sum(charged_amount)*100 end
from months m
left join invoice i
on m.monthIndex=i.months
left join lineitems l
on i.invoiceID=l.invoiceId
group by monthIndex;



--SQL Problem 3
--A banking client provides a monthly snapshot of banking data (tens of millions of accounts)
--The data is sent with one row per account each month.  This is stored in table called “ACCOUNTS”
--Fields on the record include

-- +-------------------+---------------------------------------------------------+------------------------------------------------------------------------------------+
-- |      Column       |                   Description                           |                                   Format/Example                                   |
-- +-------------------+---------------------------------------------------------+------------------------------------------------------------------------------------+
-- |       *YRMTH      |            Fiscal year and month of snapshot            |   6 digit numeric, 4 digit year appended to 2 digit month, example 201401, 201411  |
-- |    *AccountID     |            Unique identifier for an account.            |                            Integer, Example 10090207032                            |
-- |  Starting Balance |  Starting balance of account on close of previous month |                                      $25,790                                       |
-- |  Closing Balance  |      Closing balance of account on close of month       |                                      $29,320                                       |
-- |  Investment Class |                           ...                           |                                        ...                                         |
-- |       ...         |                           ...                           |                                        ...                                         |
-- +-------------------+---------------------------------------------------------+------------------------------------------------------------------------------------+

-- Many more columns, the two starred (*) columns are the only ones you will need for the query.  There are no columns to tell when an account is closed, or just opened, and no separate “Account  Master” – you must use this table alone for your resultant query.
-- The table has been in place starting 200301, so many accounts have dozens of rows, if they have been open for many years, and others just 1 or 2 rows, if new.
-- Write a query to give just a list of accountDs  that meet the following criteria.
-- 1)	Has a record for the specific month 201403
-- 2)	Also has a record for the specific month 201502
-- 3)	Is missing one or more month records between these two.
-- A good account that has been open from 201403 to 201502 inclusive would have of course rows for
-- 201403
-- 201404
-- 201405
-- 201406
-- 201407
-- 201408
-- 201409
-- 201410
-- 201411
-- 201412
-- 201501
-- 201502
-- It is fine to hardcode the start month (201403), end month (201502) and the actual count of the months between these (10 exclusive or 12 inclusive)  as part of your query.
-- Again the account must meet all 3 criteria to be a problem account.  If they only have a partial set of these records but don’t have the start month or don’t have the end month, it is not an issue, only when they have both the specified start and end and not a full set between.


select a.accountid,count(0)
from ACCOUNTS a
join ACCOUNTS b
on a.accountid = b.accountid
and b.yrmth = 201403
join  ACCOUNTS c
on a.accountid = c.accountid
and c.yrmth = 201502
where a.yrmth>=201403 and a.yrmth<=201502
group by  a.accountid
having(count(0))< months_between(date'20150201',date'20140301')+1


-- SQL Problem 4
-- An airline company has passengers with unique ID (PID) however sometimes passengers could have an old PID expiered and a new PID was assigned. The following mapping table captures this change.  The passengers booked fares at different time and different PID, some passengers never change the PID. Write a single query to find the total booking fare dollar amount for this passenger if the booking fact table is given below.
-- Passenger_map with sample data
-- odlPID		newPID		startDate		endDate
-- 1111		2222		01/01/2012		02/12/2014
-- 2222		3333		02/12/2014		01/01/2015
-- 3333		4444		01/01/2015		01/01/3000
-- ….
--
-- booking_fare with sample data
-- PID		bookingDate		bookingFare
-- 1111		1/23/2013		$540
-- 2222		2/25/2014`		$1200
-- 3333		3/23/2015		$340
-- 4444		4/25/2015`		$1100
-- 1234		1//23/2013		$540
-- 4567		1/25/2014`		$1200
-- …….


with tmp as (
select 1111 as oldid,2222 as newid from dual
union all
select 2222,3333 from dual
union all
select 3333,4444 from dual
)
,b as (
select level "层次",
sys_connect_by_path(oldid, '<-') "合并层次",
connect_by_root a.newid "根节点" ,
a.newid ,
a.oldid,
row_number()over(partition by oldid order by length(sys_connect_by_path(oldid, '<-')) desc) rn
from tmp a
connect by Prior oldid = newid
),Passenger_map as (
select 1111 as PID,540 as bookingFare from dual union all
select 2222,1200 from dual union all
select 3333,340 from dual union all
select 4444,1100 from dual union all
select 1234 ,540 from dual union all
select 4567 ,1200 from dual
)
select coalesce(b.根节点,m.pid),sum(bookingFare)
from Passenger_map m
left join b
on m.pid = b.oldid
and b.rn = 1
group by coalesce(b.根节点,m.pid)




