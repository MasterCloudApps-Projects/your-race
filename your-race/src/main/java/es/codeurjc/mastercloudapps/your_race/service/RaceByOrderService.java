package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.model.RegistrationByOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class RaceByOrderService {

	@Autowired
	private NotificationService notificationService;
	@Autowired
	private TrackService trackService;

	@Bean
	public Consumer<RegistrationByOrderDTO> consumer() {
		log.info("Bean consummer!");
		return request -> {
			try {
				log.info("Consumer: "+request);
				trackService.createByOrder(request);
			} catch (Exception e) {
				log.error("error detectado",e);
			}
		};
	}

	public void createNewRaceByOrder(RegistrationByOrderDTO registrationByOrderDTO) {
		
		notificationService.notify(registrationByOrderDTO);
		
	}
	
}
