package sanlab.itv.aquariuxtrd.repository.restclient;

import sanlab.itv.aquariuxtrd.dto.client.BinancePriceDto;

import java.util.List;

public interface BinanceClientRepository {

    List<BinancePriceDto> get();

}
