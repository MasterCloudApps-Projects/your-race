delete from track;
delete from application;
delete from race cascade;
delete from application_period;
delete from registration_info;
delete from organizer;
delete from athlete cascade;



INSERT INTO public.organizer (id,"name") VALUES
	 (10000,'New York Road Runners'),
	 (10004,'La Legión');


INSERT INTO public.application_period (id,initial_date,last_date) VALUES
	 (10002,'2022-01-01 00:00:00','2022-10-31 23:59:00'),
	 (10006,'2022-11-01 00:00:00','2022-12-31 23:59:00'),
	 (10009,'2022-11-01 00:00:00','2022-12-31 23:59:00'),
	 (10012,'2022-11-01 00:00:00','2022-12-31 23:59:00'),
	 (10015,'2021-01-01 00:00:00','2021-10-31 23:59:00'),
	 (10018,'2021-11-01 00:00:00','2021-12-31 23:59:00');
	 

INSERT INTO public.registration_info (id,concurrent_request_threshold,registration_cost,registration_date,registration_type) VALUES
	 (10003,NULL,500.0,'2022-10-31 09:00:00','BYDRAW'),
	 (10007,NULL,150.0,'2023-01-15 09:00:00','BYORDER'),
	 (10010,NULL,150.0,'2023-01-16 09:00:00','BYORDER'),
	 (10013,NULL,150.0,'2023-01-17 09:00:00','BYORDER'),
	 (10016,NULL,500.0,'2021-10-31 09:00:00','BYDRAW'),
	 (10019,NULL,150.0,'2022-01-15 09:00:00','BYORDER');	 
	 
INSERT INTO public.race (id,athlete_capacity,"date",description,distance,"location","name","type",application_period_id,organizer_id,race_registration_info_id) VALUES
	 (10001,NULL,'2022-11-06 09:00:00','The New York City Marathon is an annual marathon that courses through the five boroughs of New York City.
It is the largest marathon in the world, with 53,627 finishers in 2019 and 98,247 applicants for the 2017 race
',42.195,'New York, NY, USA','New York City Marathon','Running',10002,10000,10003),
	 (10005,NULL,'2023-05-13 10:00:00','Marcha trail de 101 kilómetros en 24 horas por la serranía de Ronda y alrededores, organizados
por el Club Deportivo La Legión 101 Km.
',101.0,'Ronda, Málaga, Spain','101 kilómetros de Ronda - Marcha Individual','Running',10006,10004,10007),
	 (10008,NULL,'2023-05-13 10:00:00','Marcha trail de 101 kilómetros en 24 horas por la serranía de Ronda y alrededores, organizados
por el Club Deportivo La Legión 101 Km.
',101.0,'Ronda, Málaga, Spain','101 kilómetros de Ronda - MTB','MTB',10009,10004,10010),
	 (10011,NULL,'2023-05-13 10:00:00','Marcha trail de 101 kilómetros en 24 horas por la serranía de Ronda y alrededores, organizados
por el Club Deportivo La Legión 101 Km.
',101.0,'Ronda, Málaga, Spain','101 kilómetros de Ronda - Por Equipos','Team',10012,10004,10013),
	 (10014,NULL,'2021-11-06 09:00:00','The New York City Marathon is an annual marathon that courses through the five boroughs of New York City.
It is the largest marathon in the world, with 53,627 finishers in 2019 and 98,247 applicants for the 2017 race
',42.195,'New York, NY, USA','New York City Marathon - Edition of 2021','Running',10015,10000,10016),
	 (10017,NULL,'2022-05-13 10:00:00','Marcha trail de 101 kilómetros en 24 horas por la serranía de Ronda y alrededores, organizados
por el Club Deportivo La Legión 101 Km.
',101.0,'Ronda, Málaga, Spain','101 kilómetros de Ronda - Marcha Individual - Edition of 2022','Running',10018,10004,10019);


	 
INSERT INTO public.athlete (id,"name",surname,track_record) VALUES
	 (10020,'Antonio','Delgado',NULL),
	 (10021,'María','Rodríguez',NULL),
	 (10022,'Clara','Smith',NULL);
	 

INSERT INTO public.application (id,application_code,application_athlete_id,application_race_id) VALUES
	 (10023,'F4t17RqcDF',10020,10001),
	 (10024,'fvOZT6GTrj',10021,10001),
	 (10025,'ZzpTugK1DO',10022,10001);

	 
INSERT INTO public.track (id,dorsal,payment_info,registration_date,score,status,athlete_id,race_id) VALUES
	 (10026,NULL,NULL,NULL,NULL,NULL,10020,10001),
	 (10027,NULL,NULL,NULL,NULL,NULL,10021,10001),
	 (10028,NULL,NULL,NULL,NULL,NULL,10022,10001);
	 
	 
