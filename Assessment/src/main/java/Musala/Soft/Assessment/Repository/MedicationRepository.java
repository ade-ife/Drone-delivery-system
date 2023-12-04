package Musala.Soft.Assessment.Repository;

import Musala.Soft.Assessment.Entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
}
