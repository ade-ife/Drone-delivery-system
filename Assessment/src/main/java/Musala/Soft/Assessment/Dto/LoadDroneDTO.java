package Musala.Soft.Assessment.Dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class LoadDroneDTO {
    @NotNull(message = "Drone ID is required")
    private Long droneId;

    @Valid
    private Set<MedicationItemDTO> medications;
}