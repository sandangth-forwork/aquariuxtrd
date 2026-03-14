package sanlab.itv.aquariuxtrd.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import sanlab.itv.aquariuxtrd.model.Asset;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {

    List<Asset> findAllByUserId(UUID userId);

    Optional<Asset> findFirstByUserIdAndSymbol(UUID userId, String symbol);

}
