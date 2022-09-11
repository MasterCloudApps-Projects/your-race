package es.codeurjc.mastercloudapps.your_race.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import es.codeurjc.mastercloudapps.your_race.repos.OrganizerRepository;
import es.codeurjc.mastercloudapps.your_race.domain.Organizer;

import javax.annotation.PostConstruct;

@Service
public class InitializerDataService {

    @Autowired
    private OrganizerRepository organizerRepository;

    @PostConstruct
    public  void init(){

        initializeOrganizers();
        initializeRaces();

    }

    private void initializeOrganizers(){

        this.organizerRepository.deleteAll();

        Organizer organizer1 = new Organizer("New York Road Runners", "Running race organization","New");
        Organizer organizer2 = new Organizer("La Legion", "Running race organization","New");

        this.organizerRepository.save(organizer1);
        this.organizerRepository.save(organizer2);

    }

    privat void initializeRaces(){


    }


}
