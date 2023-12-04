package Musala.Soft.Assessment.Pojo;

import Musala.Soft.Assessment.Dto.MedicationItemDTO;
import lombok.Data;

import java.util.List;

@Data
public class MedicationItemResponse extends ResponseObject{

    private List<MedicationItemDTO> itemDTOS;
}
