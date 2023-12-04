package Musala.Soft.Assessment.Repository;

import Musala.Soft.Assessment.Entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
