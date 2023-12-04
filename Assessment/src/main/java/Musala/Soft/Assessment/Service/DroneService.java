package Musala.Soft.Assessment.Service;

import Musala.Soft.Assessment.Dto.*;

import Musala.Soft.Assessment.Pojo.AvailableDronesResponse;
import Musala.Soft.Assessment.Pojo.BatteryLevelResponse;
import Musala.Soft.Assessment.Pojo.DroneResponse;
import Musala.Soft.Assessment.Pojo.MedicationItemResponse;


public interface DroneService {

    DroneResponse registerDrone(DroneRegistrationDTO droneRegistrationDTO);

    DroneResponse loadDroneWithMedication(Long droneId, LoadDroneDTO loadDroneDTO);

    MedicationItemResponse checkLoadedMedications(Long droneId);

    AvailableDronesResponse getAvailableDrones();

    BatteryLevelResponse checkDroneBatteryLevel(Long droneId);
}

