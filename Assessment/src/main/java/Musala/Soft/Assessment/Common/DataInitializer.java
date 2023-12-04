package Musala.Soft.Assessment.Common;

import Musala.Soft.Assessment.Entity.Drone;
import Musala.Soft.Assessment.Entity.Medication;
import Musala.Soft.Assessment.Pojo.ModelType;
import Musala.Soft.Assessment.Pojo.StateType;
import Musala.Soft.Assessment.Repository.DroneRepository;
import Musala.Soft.Assessment.Repository.MedicationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        return args -> {
            Random random = new Random();

            // Preloading drones with different attributes
            for (int i = 1; i <= 10; i++) {
                Drone drone = new Drone();
                drone.setSerialNumber("SN" + i);
                drone.setModel(getRandomModelType(random));
                drone.setWeightLimit(100 + random.nextInt(400));
                drone.setBatteryCapacity(25 + random.nextInt(75));
                drone.setState(StateType.IDLE);
                droneRepository.save(drone);
            }

            // Preloading medications with different attributes
            for (int i = 1; i <= 10; i++) {
                Medication medication = new Medication();
                medication.setName("Medication" + i);
                medication.setWeight(10 + random.nextInt(90));
                medication.setCode("MED" + i);
                medication.setImage("image_url_" + i);
                medicationRepository.save(medication);
            }
        };
    }

    private ModelType getRandomModelType(Random random) {
        ModelType[] models = ModelType.values();
        return models[random.nextInt(models.length)];
    }
}