package sanlab.itv.aquariuxtrd.repository.restclient;

import sanlab.itv.aquariuxtrd.dto.BinancePriceDto;

import java.util.List;

public interface BinanceClientRepository {

    List<BinancePriceDto> get();

}
