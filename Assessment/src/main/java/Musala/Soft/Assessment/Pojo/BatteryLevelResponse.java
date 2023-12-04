package Musala.Soft.Assessment.Pojo;

import Musala.Soft.Assessment.Dto.BatteryLevelDTO;
import lombok.Data;

@Data
public class BatteryLevelResponse extends ResponseObject{

    private BatteryLevelDTO batteryLevel;
}
