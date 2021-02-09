-- https://www.cnblogs.com/donleo123/p/11640982.html
表架构
学生表 student(id, name, age, sex)
课程表 course(id, name, teacher_id)
成绩表 score(id, course_id, score)
教师表 teacher(id, name)

-- 建表语句
CREATE TABLE student(id int, name varchar(20), age int, sex varchar(10));
CREATE TABLE course(id int, name varchar(20), teacher_id int);
CREATE TABLE score(student_id int, course_id int, score int);
CREATE TABLE teacher(id int, name varchar(20));

-- 插入测试数据语句
insert into student
select 1,'刘一',18,'男' union all
select 2,'钱二',19,'女' union all
select 3,'张三',17,'男' union all
select 4,'李四',18,'女' union all
select 5,'王五',17,'男' union all
select 6,'赵六',19,'女'

insert into teacher
select 1,'叶平' union all
select 2,'贺高' union all
select 3,'杨艳' union all
select 4,'周磊'

insert into course
select 1,'语文',1 union all
select 2,'数学',2 union all
select 3,'英语',3 union all
select 4,'物理',4

insert into score
select 1,1,56 union all
select 1,2,78 union all
select 1,3,67 union all
select 1,4,58 union all
select 2,1,79 union all
select 2,2,81 union all
select 2,3,92 union all
select 2,4,68 union all
select 3,1,91 union all
select 3,2,47 union all
select 3,3,88 union all
select 3,4,56 union all
select 4,2,88 union all
select 4,3,90 union all
select 4,4,93 union all
select 5,1,46 union all
select 5,3,78 union all
select 5,4,53 union all
select 6,1,35 union all
select 6,2,68 union all
select 6,4,71

-- 问题：
-- 1、查询“001”课程比“002”课程成绩高的所有学生的学号
select distinct a.student_id from
(select student_id, score from score where course_id = 1) a,
(select student_id, score from score where course_id = 2) b
where a.student_id = b.student_id
and a.score > b.score;

-- 2、查询平均成绩大于60分的同学的学号和平均成绩；
select student_id, avg(score) from score
group by student_id
having avg(score) > 60;

-- 3、查询所有同学的学号、姓名、选课数、总成绩；
select
student_id,
name,
coalesce(count(course_id), 0),
coalesce(sum(score), 0)
from student a left join score b
on a.id = b.student_id
group by a.id, name

-- 4、查询姓“李”的老师的个数；
select count(distinct(name)) from teacher where name like '李%';

-- 5、查询没学过“叶平”老师课的同学的学号、姓名；
select id, name from student where id not in(
select
distinct sc.student_id
from course c, teacher t, score sc
where  c.id = sc.course_id
and c.teacher_id = t.id
and t.name = '叶平');

-- 6、查询学过“001”并且也学过编号“002”课程的同学的学号、姓名；
select id, name from student where id in (
select
distinct s1.student_id
from (select student_id from score where course_id = 1) s1, (select student_id from score where course_id = 2) s2
where s1.student_id = s2.student_id);

-- 7、查询学过“叶平”老师所教的所有课的同学的学号、姓名；
select id, name from student where id  in(
select
sc.student_id
from course c, teacher t, score sc
where  c.id = sc.course_id
and c.teacher_id = t.id
and t.name = '叶平'
group by sc.student_id
having count(sc.course_id) = (select count(c.id) from course c, teacher t where c.teacher_id = t.id and t.name = '叶平')
);

-- 8、查询课程编号“002”的成绩比课程编号“001”课程低的所有同学的学号、姓名；

-- 9、查询所有课程成绩小于60分的同学的学号、姓名； // 看join的key是否是查询出来的column
select id, name from student where id not in
(select distinct student_id from score where score >= 60);

-- 10、查询没有学全所有课的同学的学号、姓名；
select id, name from student where id in (
select student_id from score group by student_id
having count(course_id) < (select count(1) from course)
);

-- 11、查询至少有一门课与学号为“1001”的同学所学相同的同学的学号和姓名；


















