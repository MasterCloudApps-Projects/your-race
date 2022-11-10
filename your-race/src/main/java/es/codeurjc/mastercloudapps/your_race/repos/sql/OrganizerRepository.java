package es.codeurjc.mastercloudapps.your_race.repos.sql;

import es.codeurjc.mastercloudapps.your_race.domain.sql.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
}
