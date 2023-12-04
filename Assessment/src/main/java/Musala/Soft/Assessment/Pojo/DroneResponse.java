package Musala.Soft.Assessment.Pojo;

import Musala.Soft.Assessment.Dto.DroneInfoDTO;
import Musala.Soft.Assessment.Entity.Drone;
import lombok.Data;

import java.util.List;

@Data
public class DroneResponse extends ResponseObject{

    private DroneInfoDTO drone;
}
