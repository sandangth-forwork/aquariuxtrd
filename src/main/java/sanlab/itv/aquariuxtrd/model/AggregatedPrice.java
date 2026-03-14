package sanlab.itv.aquariuxtrd.model;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "aggregated_price")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@IdClass(AggregatedPrice.AggregatedPriceId.class)
public class AggregatedPrice {

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AggregatedPriceId implements Serializable {

        @Column(name = "symbol", nullable = false)
        private String symbol;

        @Column(name = "created_at", nullable = false)
        private Instant createdAt;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AggregatedPriceId)) return false;
            AggregatedPriceId that = (AggregatedPriceId) o;
            return Objects.equals(symbol, that.symbol) &&
                Objects.equals(createdAt, that.createdAt);
        }

        @Override
        public int hashCode() {
            return Objects.hash(symbol, createdAt);
        }
    }

    @Id
    private String symbol;

    @Column(name = "bid", precision = 20, scale = 6)
    private BigDecimal bid;

    @Column(name = "ask", precision = 20, scale = 6)
    private BigDecimal ask;

    @Id
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AggregatedPrice)) return false;
        AggregatedPrice that = (AggregatedPrice) o;
        return StringUtils.isNotBlank(symbol) && createdAt != null &&
            symbol.equals(that.getSymbol()) &&
            Objects.equals(createdAt, that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
