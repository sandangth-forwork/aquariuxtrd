package sanlab.itv.aquariuxtrd.collector;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sanlab.itv.aquariuxtrd.service.AggregatedPriceService;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class CryptoPriceCollector {

    private final AggregatedPriceService priceService;

    @Scheduled(fixedRate = 5_000)
    public void collect() {
        log.info("JOB START at %s".formatted(Instant.now()));
        priceService.aggregate();
        log.info("JOB END at %s".formatted(Instant.now()));
    }

}
