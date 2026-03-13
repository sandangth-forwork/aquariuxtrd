package sanlab.itv.aquariuxtrd.repository.restclient;

import sanlab.itv.aquariuxtrd.dto.HoubiPriceDto;

import java.util.List;

public interface HoubiClientRepository {

    List<HoubiPriceDto> get();

}
