package Musala.Soft.Assessment.Entity;



import Musala.Soft.Assessment.Pojo.ModelType;
import Musala.Soft.Assessment.Pojo.StateType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private ModelType model;

    @Max(500)
    private double weightLimit;

    @Min(0) @Max(100)
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private StateType state;


    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Medication> medications = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drone)) return false;
        Drone drone = (Drone) o;
        return Objects.equals(serialNumber, drone.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", model=" + model +
                ", weightLimit=" + weightLimit +
                ", batteryCapacity=" + batteryCapacity +
                ", state=" + state +

                '}';
    }
}