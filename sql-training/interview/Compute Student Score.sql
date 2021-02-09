-- 成绩表
-- student_id course score
-- 求每个同学的总分，名次，他和下面一名同学的分数差
-- 求每门课的中位数

create table student(id int, name varchar(20), course varchar(20), score int);
insert into student values(1, '周润发','语文',89);
insert into student values(1, '周润发','数学',99);
insert into student values(1, '周润发','外语',67);
insert into student values(1, '周润发','物理',77);
insert into student values(1, '周润发','化学',87);
insert into student values(2, '周星驰','语文',91);
insert into student values(2, '周星驰','数学',81);
insert into student values(2, '周星驰','外语',88);
insert into student values(2, '周星驰','物理',68);
insert into student values(2, '周星驰','化学',83);
insert into student values(3, '黎明','语文',85);
insert into student values(3, '黎明','数学',65);
insert into student values(3, '黎明','外语',95);
insert into student values(3, '黎明','物理',90);
insert into student values(3, '黎明','化学',78);

select
id,
sum(score),
rank() over(order by sum(score) desc) as rk,
COALESCE(sum(score) - lead(sum(score), 1) over(), 0) as diff
from
student
group by id;

with tmp as
(select *,
row_number() over(partition by course order by score) as rk,
count(*) over(partition by course) as cnt
from student)
select course, score from tmp where rk between cnt/2 and cnt/2+1;

