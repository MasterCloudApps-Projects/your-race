package es.codeurjc.mastercloudapps.your_race.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import es.codeurjc.mastercloudapps.your_race.repos.OrganizerRepository;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;

import es.codeurjc.mastercloudapps.your_race.repos.RaceRepository;
import es.codeurjc.mastercloudapps.your_race.domain.Race;

import es.codeurjc.mastercloudapps.your_race.repos.ApplicationPeriodRepository;
import es.codeurjc.mastercloudapps.your_race.domain.ApplicationPeriod;

import java.time.LocalDateTime;
import java.time.Month;

import javax.annotation.PostConstruct;

@Service
public class InitializerDataService {

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private ApplicationPeriodRepository applicationPeriodRepository;

    @PostConstruct
    public  void init(){

        this.raceRepository.deleteAll();
        this.organizerRepository.deleteAll();

        Organizer organizer1 = new Organizer("New York Road Runners", "Race organization","New");
        Organizer organizer2 = new Organizer("La Legión", "Race organization","New");


        Race race1 = new Race ("New York City Marathon",
                "The New York City Marathon is an annual marathon that courses through the five boroughs of New York City. " +
                        "It is the largest marathon in the world, with 53,627 finishers in 2019 and 98,247 applicants for the 2017 race",
                LocalDateTime.of(2022, Month.NOVEMBER,6, 9,0),
                "New York, NY, USA",
                42.195,
                "Running",
                new ApplicationPeriod(LocalDateTime.of(2022, Month.JANUARY,1,0,0), LocalDateTime.of(2022, Month.OCTOBER,31,23,59)),
                organizer1
                );

        Race race2 = new Race ("101 kilómetros de Ronda",
                "Marcha trail de 101 kilómetros en 24 horas por la serranía de Ronda y alrededores, organizados por el Club Deportivo La Legión 101 Km.",
                LocalDateTime.of(2023, Month.MAY,13, 10,0),
                "Ronda, Málaga, Spain",
                101,
                "Marcha individual, marcha por equipos y MTB",
                new ApplicationPeriod(LocalDateTime.of(2022, Month.NOVEMBER,1,0,0), LocalDateTime.of(2022, Month.DECEMBER,31,23,59)),
                organizer2
        );



                this.organizerRepository.save(organizer1);
                this.raceRepository.save(race1);

                this.organizerRepository.save(organizer2);
                this.raceRepository.save(race2);

    }

}
