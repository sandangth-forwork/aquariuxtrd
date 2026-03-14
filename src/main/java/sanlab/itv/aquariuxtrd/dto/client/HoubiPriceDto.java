package sanlab.itv.aquariuxtrd.dto.client;

public record HoubiPriceDto(
    String symbol,
    Double open,
    Double high,
    Double low,
    Double close,
    Double amount,
    Double vol,
    Double count,
    Double bid,
    Double bidSize,
    Double ask,
    Double askSize
) {}
