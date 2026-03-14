package sanlab.itv.aquariuxtrd.repository.restclient.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import sanlab.itv.aquariuxtrd.dto.client.HoubiPriceDto;
import sanlab.itv.aquariuxtrd.dto.client.HoubiResponseDto;
import sanlab.itv.aquariuxtrd.repository.restclient.HoubiClientRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HoubiClientRepositoryImpl implements HoubiClientRepository {

    private final RestClient houbiClient;

    @Override
    public List<HoubiPriceDto> get() {
        try {
            var result = houbiClient.get()
                .retrieve()
                .body(HoubiResponseDto.class);
            return Optional.ofNullable(result).map(HoubiResponseDto::data).orElse(Collections.emptyList());
        } catch (RestClientException _) {
            return Collections.emptyList();
        }
    }
}
