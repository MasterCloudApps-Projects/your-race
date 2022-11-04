// DML Statements
select r.*, ap.*, o.* ,r2.*
from organizer o,
race r 
FULL outer join application_period ap
ON r.application_period_id  = ap.id 
full outer join registration r2 
on r2.id = r.race_registration_id 
where r.organizer_id = o.id 

select * from athlete a 
select * from registration_info r 
select * from organizer 
select * from race
select * from application_period ap 


delete from race cascade;
delete from application_period;
delete from organizer;
delete from athlete cascade;
delete from registration;
delete from application;
delete from track


// DDL Statements
drop table race cascade;
drop table application_period;
drop table organizer;
drop table athlete cascade;
drop table registration;
drop table application;
drop table track


select count(*) from track

select count(*) from application a 

select max(registration_date) , min (registration_date)from track 
where race_id >10000

select age (max(registration_date) , min (registration_date)) from track 
where race_id >10000


