package sanlab.itv.aquariuxtrd.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import sanlab.itv.aquariuxtrd.model.AggregatedPrice;

import java.util.Optional;
import java.util.UUID;

public interface AggregatedPriceRepository extends JpaRepository<AggregatedPrice, AggregatedPrice.AggregatedPriceId> {

    Optional<AggregatedPrice> findFirstBySymbolOrderByCreatedAtDesc(String symbol);

}
