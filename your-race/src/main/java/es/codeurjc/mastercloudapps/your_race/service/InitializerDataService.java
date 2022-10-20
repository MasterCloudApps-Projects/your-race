package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.domain.RegistrationInfo;
import es.codeurjc.mastercloudapps.your_race.model.RegistrationType;

import es.codeurjc.mastercloudapps.your_race.repos.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;

import es.codeurjc.mastercloudapps.your_race.domain.Race;

import es.codeurjc.mastercloudapps.your_race.domain.ApplicationPeriod;

import es.codeurjc.mastercloudapps.your_race.domain.Athlete;

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

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private ApplicationRepository applicationRepository;


    @Autowired
    private TrackRepository trackRepository;



    @PostConstruct
    public  void init(){

        this.trackRepository.deleteAll();
        this.applicationRepository.deleteAll();
        this.raceRepository.deleteAll();
        this.organizerRepository.deleteAll();
        this.athleteRepository.deleteAll();


        Organizer organizer1 = Organizer.builder().name("New York Road Runners").build();
        Organizer organizer2 = Organizer.builder().name("La Legión").build();

        Race race1 = getRace1(organizer1);
        Race race2 = getRace2(organizer2);
        Race race3 = getRace3(organizer2);
        Race race4 = getRace4(organizer2);

        Race raceClosed1 = getRaceClosed1(organizer1);
        Race raceClosed2 = getRaceClosed2(organizer2);

        race1.setRaceRegistrationInfo(RegistrationInfo.builder().registrationType(RegistrationType.BYDRAWING)
                .registrationDate(LocalDateTime.of(2022, Month.OCTOBER,31, 9,0))
                .registrationCost(500.00).build());

        race2.setRaceRegistrationInfo(RegistrationInfo.builder().registrationType(RegistrationType.BYORDER)
                .registrationDate(LocalDateTime.of(2023, Month.JANUARY,15, 9,0))
                .registrationCost(150.00).build());

        race3.setRaceRegistrationInfo(RegistrationInfo.builder().registrationType(RegistrationType.BYORDER)
                .registrationDate(LocalDateTime.of(2023, Month.JANUARY,16, 9,0))
                .registrationCost(150.00).build());

        race4.setRaceRegistrationInfo(RegistrationInfo.builder().registrationType(RegistrationType.BYORDER)
                .registrationDate(LocalDateTime.of(2023, Month.JANUARY,17, 9,0))
                .registrationCost(150.00).build());


        raceClosed1.setRaceRegistrationInfo(RegistrationInfo.builder().registrationType(RegistrationType.BYDRAWING)
                .registrationDate(LocalDateTime.of(2021, Month.OCTOBER,31, 9,0))
                .registrationCost(500.00).build());

        raceClosed2.setRaceRegistrationInfo(RegistrationInfo.builder().registrationType(RegistrationType.BYORDER)
                .registrationDate(LocalDateTime.of(2022, Month.JANUARY,15, 9,0))
                .registrationCost(150.00).build());


        Athlete athlete1 = Athlete.builder().name("Antonio").surname("Delgado").build();
        Athlete athlete2 = Athlete.builder().name("María").surname("Rodríguez").build();
        Athlete athlete3 = Athlete.builder().name("Clara").surname("Smith").build();

        this.organizerRepository.save(organizer1);
        this.raceRepository.save(race1);

        this.organizerRepository.save(organizer2);
        this.raceRepository.save(race2);
        this.raceRepository.save(race3);
        this.raceRepository.save(race4);

        this.raceRepository.save(raceClosed1);
        this.raceRepository.save(raceClosed2);

        this.athleteRepository.save(athlete1);
        this.athleteRepository.save(athlete2);
        this.athleteRepository.save(athlete3);

    }



    private Race getRace1(Organizer organizer1) {
        Race race1 = Race.builder()
                .name("New York City Marathon")
                .description("""
                        The New York City Marathon is an annual marathon that courses through the five boroughs of New York City.
                        It is the largest marathon in the world, with 53,627 finishers in 2019 and 98,247 applicants for the 2017 race
                        """)
                .date(LocalDateTime.of(2022, Month.NOVEMBER,6, 9,0))
                .location("New York, NY, USA")
                .distance(42.195)
                .type("Running")
                .applicationPeriod(ApplicationPeriod.builder()
                        .initialDate(LocalDateTime.of(2022, Month.JANUARY,1,0,0))
                        .lastDate(LocalDateTime.of(2022, Month.OCTOBER,31,23,59))
                        .build())
                .organizer(organizer1)
                .build();
        return race1;
    }

    private Race getRace2(Organizer organizer2) {
        Race race2 = Race.builder()
                .name("101 kilómetros de Ronda - Marcha Individual")
                .description("""
                        Marcha trail de 101 kilómetros en 24 horas por la serranía de Ronda y alrededores, organizados 
                        por el Club Deportivo La Legión 101 Km.
                        """)
                .date(LocalDateTime.of(2023, Month.MAY,13, 10,0))
                .location("Ronda, Málaga, Spain")
                .distance(101.0)
                .type("Running")
                .applicationPeriod(ApplicationPeriod.builder()
                        .initialDate(LocalDateTime.of(2022, Month.NOVEMBER,1,0,0))
                        .lastDate(LocalDateTime.of(2022, Month.DECEMBER,31,23,59))
                        .build())
                .organizer(organizer2)
                .build();
        return race2;
    }
    private Race getRace3(Organizer organizer2) {
        Race race3 = Race.builder()
                .name("101 kilómetros de Ronda - MTB")
                .description("""
                        Marcha trail de 101 kilómetros en 24 horas por la serranía de Ronda y alrededores, organizados 
                        por el Club Deportivo La Legión 101 Km.
                        """)
                .date(LocalDateTime.of(2023, Month.MAY,13, 10,0))
                .location("Ronda, Málaga, Spain")
                .distance(101.0)
                .type("MTB")
                .applicationPeriod(ApplicationPeriod.builder()
                        .initialDate(LocalDateTime.of(2022, Month.NOVEMBER,1,0,0))
                        .lastDate(LocalDateTime.of(2022, Month.DECEMBER,31,23,59))
                        .build())
                .organizer(organizer2)
                .build();
        return race3;
    }

    private Race getRace4(Organizer organizer2) {
        Race race4 = Race.builder()
                .name("101 kilómetros de Ronda - Por Equipos")
                .description("""
                        Marcha trail de 101 kilómetros en 24 horas por la serranía de Ronda y alrededores, organizados 
                        por el Club Deportivo La Legión 101 Km.
                        """)
                .date(LocalDateTime.of(2023, Month.MAY,13, 10,0))
                .location("Ronda, Málaga, Spain")
                .distance(101.0)
                .type("Team")
                .applicationPeriod(ApplicationPeriod.builder()
                        .initialDate(LocalDateTime.of(2022, Month.NOVEMBER,1,0,0))
                        .lastDate(LocalDateTime.of(2022, Month.DECEMBER,31,23,59))
                        .build())
                .organizer(organizer2)
                .build();

        return race4;
    }

    private Race getRaceClosed1(Organizer organizer1) {
        Race race = Race.builder()
                .name("New York City Marathon - Edition of 2021")
                .description("""
                        The New York City Marathon is an annual marathon that courses through the five boroughs of New York City.
                        It is the largest marathon in the world, with 53,627 finishers in 2019 and 98,247 applicants for the 2017 race
                        """)
                .date(LocalDateTime.of(2021, Month.NOVEMBER,6, 9,0))
                .location("New York, NY, USA")
                .distance(42.195)
                .type("Running")
                .applicationPeriod(ApplicationPeriod.builder()
                        .initialDate(LocalDateTime.of(2021, Month.JANUARY,1,0,0))
                        .lastDate(LocalDateTime.of(2021, Month.OCTOBER,31,23,59))
                        .build())
                .organizer(organizer1)
                .build();
        return race;
    }

    private Race getRaceClosed2(Organizer organizer2) {
        Race race = Race.builder()
                .name("101 kilómetros de Ronda - Marcha Individual - Edition of 2022")
                .description("""
                        Marcha trail de 101 kilómetros en 24 horas por la serranía de Ronda y alrededores, organizados 
                        por el Club Deportivo La Legión 101 Km.
                        """)
                .date(LocalDateTime.of(2022, Month.MAY,13, 10,0))
                .location("Ronda, Málaga, Spain")
                .distance(101.0)
                .type("Running")
                .applicationPeriod(ApplicationPeriod.builder()
                        .initialDate(LocalDateTime.of(2021, Month.NOVEMBER,1,0,0))
                        .lastDate(LocalDateTime.of(2021, Month.DECEMBER,31,23,59))
                        .build())
                .organizer(organizer2)
                .build();
        return race;
    }

}
