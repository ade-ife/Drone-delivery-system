package Musala.Soft.Assessment.Service.Impl;

import Musala.Soft.Assessment.Dto.*;
import Musala.Soft.Assessment.Entity.Drone;
import Musala.Soft.Assessment.Entity.Medication;
import Musala.Soft.Assessment.Pojo.*;
import Musala.Soft.Assessment.Repository.DroneRepository;
import Musala.Soft.Assessment.Repository.MedicationRepository;
import Musala.Soft.Assessment.Service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DroneServiceImpl implements DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;


    @Override
    public DroneResponse registerDrone(DroneRegistrationDTO droneRegistrationDTO) {
        DroneResponse response = new DroneResponse();

        try{
            Drone drone = convertToEntity(droneRegistrationDTO);
            drone = droneRepository.save(drone);
            response.setResponseCode(EnumResponseCodes.SUCCESS.toString());
            response.setResponseDescription("SUCCESSFUL");
            response.setDescription("DRONE REGISTERED SUCCESSFULLY");
            response.setDrone(convertToDroneInfoDTO(drone));
            return response;
        }catch (Exception exception){
                throw exception;
        }

    }

  @Transactional
    @Override
    public DroneResponse loadDroneWithMedication(Long droneId, LoadDroneDTO loadDroneDTO) {
      DroneResponse response = new DroneResponse();
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new RuntimeException("Drone not found"));


        if (drone.getBatteryCapacity() < 25 || !drone.getState().equals(StateType.IDLE)) {
            response.setResponseCode(EnumResponseCodes.FAILED.toString());
            response.setDescription("Drone cannot be loaded due to low battery or inappropriate state");
            response.setResponseDescription("UNSUCCESSFUL");
            return response;
        }


        double totalWeight = loadDroneDTO.getMedications().stream().mapToDouble(MedicationItemDTO::getWeight).sum();
        if (totalWeight + getCurrentLoadWeight(drone) > drone.getWeightLimit()) {
            response.setResponseCode(EnumResponseCodes.FAILED.toString());
            response.setDescription("Exceeds weight limit of the drone");
            response.setResponseDescription("UNSUCCESSFUL");
            return response;
        }

        // Load medications onto the drone
        Set<Medication> medications = convertToMedicationEntities(loadDroneDTO.getMedications(), drone);
        drone.getMedications().addAll(medications);
        drone.setState(StateType.LOADING);
        drone = droneRepository.save(drone);

      response.setResponseCode(EnumResponseCodes.SUCCESS.toString());
      response.setResponseDescription("SUCCESSFUL");
      response.setDescription("DRONE LOADED SUCCESSFULLY");
      response.setDrone(convertToDroneInfoDTO(drone));
      return response;

    }

    public MedicationItemResponse checkLoadedMedications(Long droneId) {
        MedicationItemResponse response = new MedicationItemResponse();
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new RuntimeException("Drone not found"));

        response.setResponseCode(EnumResponseCodes.SUCCESS.toString());
        response.setResponseDescription("SUCCESSFUL");
        response.setDescription("MEDICATION ITEMS FETCHED SUCCESSFULLY");
        response.setItemDTOS(drone.getMedications().stream()
                .map(this::convertToMedicationItemDTO)
                .collect(Collectors.toList()));
        return response;
    }


    @Override
    public AvailableDronesResponse getAvailableDrones() {
        AvailableDronesResponse response = new AvailableDronesResponse();
        List<DroneBasicInfoDTO> availableDrones = droneRepository.findAll().stream()
                .filter(drone -> drone.getState().equals(StateType.IDLE) && drone.getBatteryCapacity() >= 25)
                .map(this::convertToDroneBasicInfoDTO)
                .collect(Collectors.toList());

        AvailableDronesDTO dto = new AvailableDronesDTO();
        dto.setAvailableDrones(availableDrones);

        response.setResponseCode(EnumResponseCodes.SUCCESS.toString());
        response.setResponseDescription("SUCCESSFUL");
        response.setDescription("AVAILABLE DRONES FETCHED SUCCESSFULLY");
        response.setAvailableDrones(dto);
        return response;
    }

    @Override
    public BatteryLevelResponse checkDroneBatteryLevel(Long droneId) {
        BatteryLevelResponse response = new BatteryLevelResponse();
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new RuntimeException("Drone not found"));

        BatteryLevelDTO batteryLevelDTO = new BatteryLevelDTO();
        batteryLevelDTO.setDroneId(droneId);
        batteryLevelDTO.setBatteryLevel(drone.getBatteryCapacity());

        response.setResponseCode(EnumResponseCodes.SUCCESS.toString());
        response.setResponseDescription("SUCCESSFUL");
        response.setDescription("DRONE REGISTERED SUCCESSFULLY");
        response.setBatteryLevel(batteryLevelDTO);
        return response;
    }


    private Drone convertToEntity(DroneRegistrationDTO dto) {

        Drone drone = new Drone();
        drone.setSerialNumber(dto.getSerialNumber());
        drone.setModel(dto.getModel());
        drone.setWeightLimit(dto.getWeightLimit());
        drone.setBatteryCapacity(dto.getBatteryCapacity());
        drone.setState(StateType.IDLE);

        return drone;
    }

    private DroneInfoDTO convertToDroneInfoDTO(Drone drone) {

        DroneInfoDTO dto = new DroneInfoDTO();
        dto.setSerialNumber(drone.getSerialNumber());
        dto.setModel(drone.getModel());
        dto.setWeightLimit(drone.getWeightLimit());
        dto.setBatteryCapacity(drone.getBatteryCapacity());
        dto.setState(drone.getState());
        dto.setLoadedMedications(drone.getMedications().stream()
                .map(this::convertToMedicationItemDTO)
                .collect(Collectors.toSet()));

        return dto;
    }


    private Set<Medication> convertToMedicationEntities(Set<MedicationItemDTO> medicationItemDTOs, Drone drone) {
        return medicationItemDTOs.stream().map(dto -> {
            Medication medication = new Medication();
            medication.setName(dto.getName());
            medication.setWeight(dto.getWeight());
            medication.setCode(dto.getCode());
            medication.setImage(dto.getImage());
            medication.setDrone(drone);
            return medication;
        }).collect(Collectors.toSet());
    }


    private MedicationItemDTO convertToMedicationItemDTO(Medication medication) {
        MedicationItemDTO dto = new MedicationItemDTO();
        dto.setName(medication.getName());
        dto.setWeight(medication.getWeight());
        dto.setCode(medication.getCode());
        dto.setImage(medication.getImage());
        return dto;
    }


    private DroneBasicInfoDTO convertToDroneBasicInfoDTO(Drone drone) {
        DroneBasicInfoDTO dto = new DroneBasicInfoDTO();
        dto.setId(drone.getId());
        dto.setSerialNumber(drone.getSerialNumber());
        dto.setBatteryCapacity(drone.getBatteryCapacity());
        return dto;
    }


    private double getCurrentLoadWeight(Drone drone) {
        return drone.getMedications().stream().mapToDouble(Medication::getWeight).sum();
    }
}
