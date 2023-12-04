package Musala.Soft.Assessment.Controller;

import Musala.Soft.Assessment.Dto.*;
import Musala.Soft.Assessment.Pojo.AvailableDronesResponse;
import Musala.Soft.Assessment.Pojo.BatteryLevelResponse;
import Musala.Soft.Assessment.Pojo.DroneResponse;
import Musala.Soft.Assessment.Pojo.MedicationItemResponse;
import Musala.Soft.Assessment.Service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping("/register")
    public DroneResponse registerDrone(@Valid @RequestBody DroneRegistrationDTO droneRegistrationDTO) {
        DroneResponse response = new DroneResponse();
         response = droneService.registerDrone(droneRegistrationDTO);
         return response;
    }

    @PostMapping("/{droneId}/load")
    public DroneResponse loadDrone(@PathVariable Long droneId, @Valid @RequestBody LoadDroneDTO loadDroneDTO) {
        DroneResponse response = new DroneResponse();
        response = droneService.loadDroneWithMedication(droneId, loadDroneDTO);
        return response;
    }


    @GetMapping("/{droneId}/medications")
    public MedicationItemResponse getLoadedMedications(@PathVariable Long droneId) {
        MedicationItemResponse response = new MedicationItemResponse();
        response = droneService.checkLoadedMedications(droneId);
        return response;
    }

    @GetMapping("/available")
    public AvailableDronesResponse getAvailableDrones() {

        AvailableDronesResponse availableDronesResponse = new AvailableDronesResponse();
        availableDronesResponse = droneService.getAvailableDrones();
        return availableDronesResponse;
    }

    @GetMapping("/{droneId}/battery")
    public BatteryLevelResponse getBatteryLevel(@PathVariable Long droneId) {
        BatteryLevelResponse response = new BatteryLevelResponse();
        response = droneService.checkDroneBatteryLevel(droneId);
        return response;
    }
}
