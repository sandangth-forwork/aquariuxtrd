package sanlab.itv.aquariuxtrd.repository.restclient.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import sanlab.itv.aquariuxtrd.dto.client.BinancePriceDto;
import sanlab.itv.aquariuxtrd.repository.restclient.BinanceClientRepository;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BinanceClientRepositoryImpl implements BinanceClientRepository {

    private final RestClient binanceClient;

    @Override
    public List<BinancePriceDto> get() {
        try {
            return binanceClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<List<BinancePriceDto>>() {});
        } catch (RestClientException _) {
            return Collections.emptyList();
        }
    }
}
