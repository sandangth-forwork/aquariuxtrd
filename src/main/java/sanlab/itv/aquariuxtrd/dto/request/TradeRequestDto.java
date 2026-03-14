package sanlab.itv.aquariuxtrd.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record TradeRequestDto(
    @Schema(
        description = "Trading pair symbol",
        example = "BTCUSDT",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String symbol,
    @Schema(
        description = "Trade direction",
        example = "BUY",
        allowableValues = {"BUY", "SELL"},
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String side,
    @Schema(
        description = "Quantity of the asset to trade",
        example = "0.01",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    BigDecimal quantity
) {}
