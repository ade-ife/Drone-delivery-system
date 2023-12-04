package Musala.Soft.Assessment.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long droneId;
    private int batteryLevel;
    private LocalDateTime timestamp;

    public AuditLog() {

    }

    public AuditLog(Long droneId, int batteryLevel, LocalDateTime timestamp) {
        this.droneId = droneId;
        this.batteryLevel = batteryLevel;
        this.timestamp = timestamp;
    }


}
