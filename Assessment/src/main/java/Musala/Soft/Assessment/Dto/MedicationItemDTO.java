package Musala.Soft.Assessment.Dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class MedicationItemDTO {
    @Pattern(regexp = "[a-zA-Z0-9_-]+", message = "Name must only contain letters, numbers, '-' and '_'")
    private String name;

    private double weight;

    @Pattern(regexp = "[A-Z0-9_]+", message = "Code must only contain upper case letters, numbers, and underscores")
    private String code;

    private String image;

}

