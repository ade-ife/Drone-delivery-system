package Musala.Soft.Assessment.Service;

import Musala.Soft.Assessment.Entity.AuditLog;
import Musala.Soft.Assessment.Entity.Drone;
import Musala.Soft.Assessment.Repository.AuditLogRepository;
import Musala.Soft.Assessment.Repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;


@Service
public class DroneMonitoringService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    private static final String LOG_FILE_PATH = "drone_battery_logs.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Scheduled(fixedRate = 60000)
    public void monitorDroneBatteryLevels() {
        List<Drone> drones = droneRepository.findAll();
        drones.forEach(this::logBatteryLevel);
    }

//    private void logBatteryLevel(Drone drone) {
//        AuditLog log = new AuditLog(drone.getId(), drone.getBatteryCapacity(), LocalDateTime.now());
//        auditLogRepository.save(log);
//    }

    private void logBatteryLevel(Drone drone) {
        String logEntry = String.format("Time: %s, Drone ID: %d, Battery Level: %d%%",
                LocalDateTime.now().format(formatter),
                drone.getId(),
                drone.getBatteryCapacity());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
