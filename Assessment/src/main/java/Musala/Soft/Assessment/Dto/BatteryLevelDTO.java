package Musala.Soft.Assessment.Dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BatteryLevelDTO {

    @NotNull(message = "Drone ID is required")
    private Long droneId;
    private int batteryLevel;

}
