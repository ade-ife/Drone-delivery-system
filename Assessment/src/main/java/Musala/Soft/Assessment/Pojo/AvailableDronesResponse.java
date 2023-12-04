package Musala.Soft.Assessment.Pojo;

import Musala.Soft.Assessment.Dto.AvailableDronesDTO;
import lombok.Data;

@Data
public class AvailableDronesResponse extends ResponseObject{

    private AvailableDronesDTO availableDrones;
}
