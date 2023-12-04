package Musala.Soft.Assessment.Dto;

import Musala.Soft.Assessment.Pojo.ModelType;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class DroneRegistrationDTO {
    @Size(max = 100, message = "Serial number must be 100 characters or less")
    private String serialNumber;

    @NotNull(message = "Model is required")
    private ModelType model;

    @Max(value = 500, message = "Weight limit must be 500 grams or less")
    private double weightLimit;

    @Min(value = 0, message = "Battery capacity must be at least 0%")
    @Max(value = 100, message = "Battery capacity must be no more than 100%")
    private int batteryCapacity;


}
