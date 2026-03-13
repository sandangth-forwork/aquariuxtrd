package sanlab.itv.aquariuxtrd.dto;

public record BinancePriceDto(String symbol,
                              String bidPrice,
                              String bidQty,
                              String askPrice,
                              String askQty) {}
