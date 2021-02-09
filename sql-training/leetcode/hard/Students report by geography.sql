-- Question 105
-- A U.S graduate school has students from Asia, Europe and America. The students' location information are stored in table student as below.
 

-- | name   | continent |
-- |--------|-----------|
-- | Jack   | America   |
-- | Pascal | Europe    |
-- | Xi     | Asia      |
-- | Jane   | America   |
 

-- Pivot the continent column in this table so that each name is sorted alphabetically and displayed underneath its corresponding continent. The output headers should be America, Asia and Europe respectively. It is guaranteed that the student number from America is no less than either Asia or Europe.
 

-- For the sample input, the output is:
 

-- | America | Asia | Europe |
-- |---------|------|--------|
-- | Jack    | Xi   | Pascal |
-- | Jane    |      |        |

-- Solution
select min(case when continent = 'America' then name end) as America,
min(case when continent = 'Asia' then name end) as Asia,
min(case when continent = 'Europe' then name end) as Europe
from 
(select *, row_number() over(partition by continent order by name) as rn
from student) a
group by rn


-- solution
WITH continent_pivot AS
(
SELECT
CASE WHEN continent = 'America' THEN name END AS 'America',
CASE WHEN continent = 'Asia' THEN name END AS 'Asia',
CASE WHEN continent = 'Europe' THEN name END AS 'Europe',
ROW_NUMBER() OVER(PARTITION BY continent ORDER BY name) AS rk
FROM student
)
SELECT
MAX(America) AS America,
MAX(Asia) AS Asia,
MAX(Europe) AS Europe
FROM continent_pivot
GROUP BY rk;