package sanlab.itv.aquariuxtrd.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import sanlab.itv.aquariuxtrd.model.AuditLog;

import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

    List<AuditLog> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

}
