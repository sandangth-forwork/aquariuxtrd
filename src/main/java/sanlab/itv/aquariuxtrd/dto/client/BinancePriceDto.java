package sanlab.itv.aquariuxtrd.dto.client;

public record BinancePriceDto(String symbol,
                              String bidPrice,
                              String bidQty,
                              String askPrice,
                              String askQty) {}
