package sanlab.itv.aquariuxtrd.dto;

import java.util.List;

public record HoubiResponseDto(List<HoubiPriceDto> data, String status, Long ts) {}
