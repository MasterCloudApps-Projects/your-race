package es.codeurjc.mastercloudapps.your_race.service;

import es.codeurjc.mastercloudapps.your_race.model.RegistrationByOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Slf4j
@Service
public class NotificationService {

	EmitterProcessor<RegistrationByOrderDTO> processor = EmitterProcessor.create();

	public void notify(RegistrationByOrderDTO registrationByOrderDTO) {
		log.info("New raceByOrderCreationProgressNotifications: "+registrationByOrderDTO.getApplicationCode());
		processor.onNext(registrationByOrderDTO);
	}

	@Bean
	public Supplier<Flux<RegistrationByOrderDTO>> producer() {
		return () -> this.processor;
	}
}
