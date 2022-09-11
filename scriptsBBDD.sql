delete from organizer
delete from race
delete from application_period 


select * from organizer 
select * from race
select * from application_period ap 


select r.*, ap.*, o.* 
from organizer o,
race r 
FULL outer join application_period ap
ON r.application_period_id  = ap.id 
where r.organizer_id = o.id 