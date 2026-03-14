package sanlab.itv.aquariuxtrd.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sanlab.itv.aquariuxtrd.model.Wallet;
import sanlab.itv.aquariuxtrd.repository.crud.WalletRepository;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppEventListener {

    private final WalletRepository walletRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onAppReady() {
        log.info("==================== APP START ====================");
        initWallet();
    }

    @EventListener(ContextClosedEvent.class)
    public void onContextClosed() {
        destroyWallet();
        log.info("==================== APP STOP ====================");
    }

    private void initWallet() {
        walletRepository.save(Wallet.builder()
            .asset("My Asset")
            .balance(BigDecimal.valueOf(50_000D))
            .build());
    }

    private void destroyWallet() {
        walletRepository.deleteAll();
    }

}
