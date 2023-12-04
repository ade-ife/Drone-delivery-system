package Musala.Soft.Assessment.Entity;


import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "[a-zA-Z0-9_-]+")
    private String name;

    private double weight;

    @Pattern(regexp = "[A-Z0-9_]+")
    private String code;

    private String image;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medication)) return false;
        Medication that = (Medication) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", code='" + code + '\'' +
                ", image='" + image + '\'' +

                '}';
    }

}