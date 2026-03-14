package sanlab.itv.aquariuxtrd.repository.restclient;

import sanlab.itv.aquariuxtrd.dto.client.HoubiPriceDto;

import java.util.List;

public interface HoubiClientRepository {

    List<HoubiPriceDto> get();

}
