package bbrz.at.weatherSpringBoot.repo;

import bbrz.at.weatherSpringBoot.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TemperatureRepo extends JpaRepository<Temperature, String> {

}
