package Musala.Soft.Assessment.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Musala.Soft.Assessment.Dto.DroneRegistrationDTO;
import Musala.Soft.Assessment.Dto.LoadDroneDTO;
import Musala.Soft.Assessment.Dto.MedicationItemDTO;
import Musala.Soft.Assessment.Entity.Drone;
import Musala.Soft.Assessment.Entity.Medication;
import Musala.Soft.Assessment.Pojo.*;
import Musala.Soft.Assessment.Repository.DroneRepository;
import Musala.Soft.Assessment.Service.Impl.DroneServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class DroneServiceImplTest {

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DroneServiceImpl droneService;

    @Test
    public void testRegisterDrone_Success() {

        DroneRegistrationDTO registrationDTO = createDroneRegistrationDTO();
        Drone drone = createDroneFromDTO(registrationDTO);

        when(droneRepository.save(any(Drone.class))).thenReturn(drone);


        DroneResponse result = droneService.registerDrone(registrationDTO);


        assertEquals(EnumResponseCodes.SUCCESS.toString(), result.getResponseCode());
        assertEquals("SUCCESSFUL", result.getResponseDescription());
        assertEquals("DRONE REGISTERED SUCCESSFULLY", result.getDescription());
        assertNotNull(result.getDrone());
        assertEquals(registrationDTO.getSerialNumber(), result.getDrone().getSerialNumber());
        assertEquals(registrationDTO.getModel(), result.getDrone().getModel());
        assertEquals(registrationDTO.getWeightLimit(), result.getDrone().getWeightLimit());
        assertEquals(registrationDTO.getBatteryCapacity(), result.getDrone().getBatteryCapacity());
    }

    @Test
    public void testRegisterDrone_Exception() {

        DroneRegistrationDTO registrationDTO = createDroneRegistrationDTO();

        when(droneRepository.save(any(Drone.class))).thenThrow(new RuntimeException("Database Error"));


        Exception exception = assertThrows(RuntimeException.class, () -> {
            droneService.registerDrone(registrationDTO);
        });

        assertEquals("Database Error", exception.getMessage());
    }

    private DroneRegistrationDTO createDroneRegistrationDTO() {
        DroneRegistrationDTO dto = new DroneRegistrationDTO();
        dto.setSerialNumber("SN001");
        dto.setModel(ModelType.LIGHTWEIGHT);
        dto.setWeightLimit(300.0);
        dto.setBatteryCapacity(75);
        return dto;
    }

    private Drone createDroneFromDTO(DroneRegistrationDTO dto) {
        Drone drone = new Drone();
        drone.setSerialNumber(dto.getSerialNumber());
        drone.setModel(dto.getModel());
        drone.setWeightLimit(dto.getWeightLimit());
        drone.setBatteryCapacity(dto.getBatteryCapacity());
        drone.setState(StateType.IDLE);
        return drone;
    }


    @Test
    public void testCheckDroneBatteryLevel_Success() {

        Long droneId = 1L;
        Drone drone = new Drone();
        drone.setId(droneId);
        drone.setBatteryCapacity(50);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));


        BatteryLevelResponse response = droneService.checkDroneBatteryLevel(droneId);


        assertEquals(EnumResponseCodes.SUCCESS.toString(), response.getResponseCode());
        assertEquals("SUCCESSFUL", response.getResponseDescription());
        assertEquals("DRONE REGISTERED SUCCESSFULLY", response.getDescription());
        assertNotNull(response.getBatteryLevel());
        assertEquals(droneId, response.getBatteryLevel().getDroneId());
        assertEquals(50, response.getBatteryLevel().getBatteryLevel());
    }

    @Test
    public void testCheckDroneBatteryLevel_NotFound() {

        Long droneId = 2L;
        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());


        Exception exception = assertThrows(RuntimeException.class, () -> {
            droneService.checkDroneBatteryLevel(droneId);
        });

        assertEquals("Drone not found", exception.getMessage());
    }


    @Test
    public void testCheckLoadedMedications_Success() {

        Long droneId = 1L;
        Drone drone = createDroneWithMedications(droneId);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        MedicationItemResponse response = droneService.checkLoadedMedications(droneId);


        assertEquals(EnumResponseCodes.SUCCESS.toString(), response.getResponseCode());
        assertEquals("SUCCESSFUL", response.getResponseDescription());
        assertEquals("MEDICATION ITEMS FETCHED SUCCESSFULLY", response.getDescription());
        assertFalse(response.getItemDTOS().isEmpty());

    }

    @Test
    public void testCheckLoadedMedications_NotFound() {

        Long droneId = 2L;
        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());


        Exception exception = assertThrows(RuntimeException.class, () -> {
            droneService.checkLoadedMedications(droneId);
        });

        assertEquals("Drone not found", exception.getMessage());
    }

    private Drone createDroneWithMedications(Long droneId) {
        Drone drone = new Drone();
        drone.setId(droneId);


        Medication med1 = new Medication();
        med1.setName("Medication1");
        med1.setWeight(100);
        med1.setCode("MED100");
        med1.setImage("image_url_1");
        med1.setDrone(drone);

        Medication med2 = new Medication();
        med2.setName("Medication2");
        med2.setWeight(150);
        med2.setCode("MED150");
        med2.setImage("image_url_2");
        med2.setDrone(drone);

        Set<Medication> medications = Stream.of(med1, med2).collect(Collectors.toSet());
        drone.setMedications(medications);

        return drone;
    }

    @Test
    public void testGetAvailableDrones() {

        List<Drone> drones = createDronesList();
        when(droneRepository.findAll()).thenReturn(drones);


        AvailableDronesResponse response = droneService.getAvailableDrones();


        assertEquals(EnumResponseCodes.SUCCESS.toString(), response.getResponseCode());
        assertEquals("SUCCESSFUL", response.getResponseDescription());
        assertEquals("AVAILABLE DRONES FETCHED SUCCESSFULLY", response.getDescription());
        assertNotNull(response.getAvailableDrones());
        assertEquals(1, response.getAvailableDrones().getAvailableDrones().size());

    }

    private List<Drone> createDronesList() {
        Drone availableDrone = new Drone();
        availableDrone.setId(1L);
        availableDrone.setState(StateType.IDLE);
        availableDrone.setBatteryCapacity(50);

        Drone unavailableDrone = new Drone();
        unavailableDrone.setId(2L);
        unavailableDrone.setState(StateType.DELIVERING);
        unavailableDrone.setBatteryCapacity(20);

        return Stream.of(availableDrone, unavailableDrone).collect(Collectors.toList());
    }

    @Test
    public void testLoadDroneWithMedication_Success() {

        Long droneId = 1L;
        Drone drone = createIdleDrone(droneId, 50, 300.0);
        LoadDroneDTO loadDroneDTO = createLoadDroneDTO(200.0);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);


        DroneResponse response = droneService.loadDroneWithMedication(droneId, loadDroneDTO);


        assertEquals(EnumResponseCodes.SUCCESS.toString(), response.getResponseCode());
        assertEquals("SUCCESSFUL", response.getResponseDescription());
        assertEquals("DRONE LOADED SUCCESSFULLY", response.getDescription());
        assertNotNull(response.getDrone());
    }

    @Test
    public void testLoadDroneWithMedication_DroneNotFound() {

        Long droneId = 2L;
        LoadDroneDTO loadDroneDTO = createLoadDroneDTO(100.0);

        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());


        Exception exception = assertThrows(RuntimeException.class, () -> {
            droneService.loadDroneWithMedication(droneId, loadDroneDTO);
        });

        assertEquals("Drone not found", exception.getMessage());
    }

    private Drone createIdleDrone(Long droneId, int batteryCapacity, double weightLimit) {
        Drone drone = new Drone();
        drone.setId(droneId);
        drone.setBatteryCapacity(batteryCapacity);
        drone.setState(StateType.IDLE);
        drone.setWeightLimit(weightLimit);
        drone.setMedications(new HashSet<>());
        return drone;
    }

    private LoadDroneDTO createLoadDroneDTO(double totalWeight) {
        LoadDroneDTO dto = new LoadDroneDTO();
        Set<MedicationItemDTO> medications = new HashSet<>();
        MedicationItemDTO medItem = new MedicationItemDTO();
        medItem.setWeight(totalWeight);
        medications.add(medItem);
        dto.setMedications(medications);
        return dto;
    }

    @Test
    public void testLoadDroneWithMedication_BatteryTooLow() {

        Long droneId = 1L;
        Drone drone = createIdleDrone(droneId, 20, 300.0);
        LoadDroneDTO loadDroneDTO = createLoadDroneDTO(100.0);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));


        DroneResponse response = droneService.loadDroneWithMedication(droneId, loadDroneDTO);


        assertEquals(EnumResponseCodes.FAILED.toString(), response.getResponseCode());
        assertEquals("Drone cannot be loaded due to low battery or inappropriate state", response.getDescription());
    }

    @Test
    public void testLoadDroneWithMedication_ExceedsWeightLimit() {

        Long droneId = 1L;
        Drone drone = createIdleDrone(droneId, 50, 300.0);
        LoadDroneDTO loadDroneDTO = createLoadDroneDTO(350.0);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));


        DroneResponse response = droneService.loadDroneWithMedication(droneId, loadDroneDTO);


        assertEquals(EnumResponseCodes.FAILED.toString(), response.getResponseCode());
        assertEquals("Exceeds weight limit of the drone", response.getDescription());
    }





}


