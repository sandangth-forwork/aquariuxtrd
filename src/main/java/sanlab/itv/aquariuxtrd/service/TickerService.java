package sanlab.itv.aquariuxtrd.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sanlab.itv.aquariuxtrd.model.AggregatedPrice;
import sanlab.itv.aquariuxtrd.repository.crud.AggregatedPriceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TickerService {

    private final AggregatedPriceRepository priceRepository;
    private final String[] supportedCryptos;

    public TickerService(AggregatedPriceRepository priceRepository,
                         @Value("${app.supported-cryptos:}") String[] supportCryptos) {
        this.priceRepository = priceRepository;
        this.supportedCryptos = supportCryptos;
    }


    public List<AggregatedPrice> latest() {
        List<AggregatedPrice> result = new ArrayList<>();
        for (String symbol : supportedCryptos) {
            priceRepository.findFirstBySymbolOrderByCreatedAtDesc(symbol).ifPresent(result::add);
        }
        return result;
    }

}
