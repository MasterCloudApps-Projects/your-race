package es.codeurjc.mastercloudapps.your_race.component;

import es.codeurjc.mastercloudapps.your_race.model.RegistrationByOrderDTO;
import es.codeurjc.mastercloudapps.your_race.service.ApplicationService;
import es.codeurjc.mastercloudapps.your_race.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RaceByOrderAmqp {

	@Autowired
	private TrackService trackService;
	@Autowired
	private ApplicationService applicationService;

	@RabbitListener(id = "consumer", queues = "raceByOrderCreationProgressNotifications", ackMode = "AUTO")
	public void consumer(RegistrationByOrderDTO registrationByOrderDTO) {
		try {
			log.info("Consumer: "+registrationByOrderDTO.getApplicationCode().toString());
			trackService.createByOrder(registrationByOrderDTO);
		} catch (Exception e) {
			log.error("error detectado",e);
		}
	}
	
}
