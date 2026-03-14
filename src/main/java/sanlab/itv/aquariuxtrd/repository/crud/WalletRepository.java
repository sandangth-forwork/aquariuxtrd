package sanlab.itv.aquariuxtrd.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import sanlab.itv.aquariuxtrd.model.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findFirstByOrderByCreatedAt();
}
