package es.codeurjc.mastercloudapps.your_race.service;

import org.springframework.stereotype.Service;


@Service
public class RegistrationService {

    private final TrackService trackService;

    public RegistrationService(TrackService trackService){
        this.trackService = trackService;
    }


}
