package Musala.Soft.Assessment.Dto;

import Musala.Soft.Assessment.Pojo.ModelType;
import Musala.Soft.Assessment.Pojo.StateType;
import lombok.Data;

import java.util.Set;

@Data
public class DroneInfoDTO {
    private String serialNumber;
    private ModelType model;
    private double weightLimit;
    private int batteryCapacity;
    private StateType state;
    private Set<MedicationItemDTO> loadedMedications;


}
