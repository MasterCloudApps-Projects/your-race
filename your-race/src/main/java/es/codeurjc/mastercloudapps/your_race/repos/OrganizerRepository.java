package es.codeurjc.mastercloudapps.your_race.repos;

import es.codeurjc.mastercloudapps.your_race.domain.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
}
