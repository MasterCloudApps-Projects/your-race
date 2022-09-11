// DML Statements
select r.*, ap.*, o.* 
from organizer o,
race r 
FULL outer join application_period ap
ON r.application_period_id  = ap.id 
where r.organizer_id = o.id 



// DDL Statements

drop table race cascade;
drop table application_period;
drop table organizer;
drop table athlete cascade;
drop table registration;
drop table application;


delete from organizer
delete from race
delete from application_period 


select * from organizer 
select * from race
select * from application_period ap 
