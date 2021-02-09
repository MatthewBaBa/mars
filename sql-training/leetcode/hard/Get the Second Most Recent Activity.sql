-- /*
-- Table: UserActivity
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | username      | varchar |
-- | activity      | varchar |
-- | startDate     | Date    |
-- | endDate       | Date    |
-- +---------------+---------+
-- This table does not contain primary key.
-- This table contain information about the activity performed of each user in a period of time.
-- A person with username performed a activity from startDate to endDate.
-- Write an SQL query to show the second most recent activity of each user.
-- If the user only has one activity, return that one.
-- A user can't perform more than one activity at the same time. Return the result table in any order.
-- The query result format is in the following example:
-- UserActivity table:
-- +------------+--------------+-------------+-------------+
-- | username   | activity     | startDate   | endDate     |
-- +------------+--------------+-------------+-------------+
-- | Alice      | Travel       | 2020-02-12  | 2020-02-20  |
-- | Alice      | Dancing      | 2020-02-21  | 2020-02-23  |
-- | Alice      | Travel       | 2020-02-24  | 2020-02-28  |
-- | Bob        | Travel       | 2020-02-11  | 2020-02-18  |
-- +------------+--------------+-------------+-------------+
-- Result table:
-- +------------+--------------+-------------+-------------+
-- | username   | activity     | startDate   | endDate     |
-- +------------+--------------+-------------+-------------+
-- | Alice      | Dancing      | 2020-02-21  | 2020-02-23  |
-- | Bob        | Travel       | 2020-02-11  | 2020-02-18  |
-- +------------+--------------+-------------+-------------+
-- The most recent activity of Alice is Travel from 2020-02-24 to 2020-02-28, before that she was dancing from 2020-02-21 to 2020-02-23.
-- Bob only has one record, we just take that one.
-- */

Create table  UserActivity (username varchar(30), activity varchar(30), startDate date, endDate date);
Truncate table UserActivity
insert into UserActivity (username, activity, startDate, endDate) values ('Alice', 'Travel', '2020-02-12', '2020-02-20');
insert into UserActivity (username, activity, startDate, endDate) values ('Alice', 'Dancing', '2020-02-21', '2020-02-23');
insert into UserActivity (username, activity, startDate, endDate) values ('Alice', 'Travel', '2020-02-24', '2020-02-28');
insert into UserActivity (username, activity, startDate, endDate) values ('Bob', 'Travel', '2020-02-11', '2020-02-18');


WITH tmp AS(
SELECT username, activity, startDate, endDate,
ROW_NUMBER() OVER(PARTITION BY username ORDER BY startDate DESC) AS rnk,
COUNT(activity) OVER(PARTITION BY username) AS num
FROM UserActivity
)
SELECT DISTINCT username, activity, startDate, endDate FROM tmp
WHERE
CASE WHEN num = 1 THEN rnk = 1
ELSE rnk = 2
END;



WITH tmp AS(
SELECT username, activity, startDate, endDate,
ROW_NUMBER() OVER(PARTITION BY username ORDER BY startDate DESC) AS rnk,
COUNT(activity) OVER(PARTITION BY username) AS num
FROM UserActivity
)
SELECT DISTINCT username, activity, startDate, endDate FROM tmp
WHERE rnk = IF(num = 1, 1, 2);