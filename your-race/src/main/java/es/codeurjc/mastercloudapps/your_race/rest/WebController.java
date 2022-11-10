package es.codeurjc.mastercloudapps.your_race.rest;

import es.codeurjc.mastercloudapps.your_race.Features;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.togglz.core.manager.FeatureManager;

@RestController
public class WebController {

    private final FeatureManager featureManager;

    public WebController(FeatureManager featureManager) {
        this.featureManager = featureManager;
    }

    @GetMapping
    public String getUseMongo() {
        if(featureManager.isActive(Features.USEMONGO)) {
           return  "Using Mongo DB.";
        } else {
            return "Using Postgres.";
        }
    }
}