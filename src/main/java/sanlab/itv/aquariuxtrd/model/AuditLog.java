package sanlab.itv.aquariuxtrd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_log")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    private String symbol;

    private String side;

    @Column(name = "price", precision = 20, scale = 6)
    private BigDecimal price;

    @Column(name = "quantity", precision = 20, scale = 6)
    private BigDecimal quantity;

    @Column(name = "total", precision = 20, scale = 6)
    private BigDecimal total;

    @LastModifiedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
