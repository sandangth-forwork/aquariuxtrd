package sanlab.itv.aquariuxtrd.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sanlab.itv.aquariuxtrd.model.AggregatedPrice;
import sanlab.itv.aquariuxtrd.repository.crud.AggregatedPriceRepository;
import sanlab.itv.aquariuxtrd.repository.restclient.BinanceClientRepository;
import sanlab.itv.aquariuxtrd.repository.restclient.HoubiClientRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AggregatedPriceService {

    private final BinanceClientRepository binanceClientRepository;
    private final HoubiClientRepository houbiClientRepository;
    private final AggregatedPriceRepository aggregatedPriceRepository;
    private final Set<String> supportedCryptos;

    AggregatedPriceService(BinanceClientRepository binanceClientRepository,
                           HoubiClientRepository houbiClientRepository,
                           AggregatedPriceRepository aggregatedPriceRepository,
                           @Value("${app.supported-cryptos:}") String[] supportedCryptos) {
        this.binanceClientRepository = binanceClientRepository;
        this.houbiClientRepository = houbiClientRepository;
        this.aggregatedPriceRepository = aggregatedPriceRepository;
        this.supportedCryptos = Arrays.stream(supportedCryptos).collect(Collectors.toSet());
    }

    public void aggregate() {
        Map<String, MutablePair<BigDecimal, BigDecimal>> cache = new HashMap<>();
        var binancePriceLst = binanceClientRepository.get();
        var houbiPriceLst = houbiClientRepository.get();

        binancePriceLst.stream()
            .filter(inner -> supportedCryptos.contains(StringUtils.upperCase(inner.symbol())))
            .forEach(inner ->
                updateBestPriceIntoCache(cache,
                    inner.symbol(),
                    Double.parseDouble(inner.bidPrice()),
                    Double.parseDouble(inner.askPrice())
                ));

        houbiPriceLst.stream()
            .filter(inner -> supportedCryptos.contains(StringUtils.upperCase(inner.symbol())))
            .forEach(inner ->
                updateBestPriceIntoCache(cache,
                    inner.symbol(),
                    inner.bid(),
                    inner.ask()
                ));

        aggregatedPriceRepository.saveAll(
            cache.entrySet().stream().map(inner ->
                AggregatedPrice.builder()
                    .symbol(inner.getKey())
                    .bestBid(inner.getValue().getLeft())
                    .bestAsk(inner.getValue().getRight())
                    .build()
        ).toList());
    }

    private static void updateBestPriceIntoCache(Map<String, MutablePair<BigDecimal, BigDecimal>> cache,
                                    String symbol, Double bidPrice, Double askPrice) {
        BigDecimal bidPriceDec = BigDecimal.valueOf(bidPrice);
        BigDecimal askPriceDec = BigDecimal.valueOf(askPrice);
        String symbolUpper = StringUtils.upperCase(symbol);
        cache.computeIfPresent(symbolUpper, (_, currentValue) -> {
            currentValue.setLeft(currentValue.getLeft().max(bidPriceDec));
            currentValue.setValue(currentValue.getRight().min(askPriceDec));
            return currentValue;
        });
        cache.computeIfAbsent(symbolUpper, _ -> MutablePair.of(bidPriceDec, askPriceDec));
    }

}
