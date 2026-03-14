package sanlab.itv.aquariuxtrd.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class WalletResponseDto {

    @Schema(description = "Unique identifier of the wallet owner", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID userId;

    @Schema(description = "Available fiat balance in USDT", example = "50000.00")
    private BigDecimal balance;

    @Schema(description = "List of currently held crypto assets")
    private List<AssetDto> assets;

    private Instant updatedAt;

    @Schema(description = "An individual asset holding in the wallet")
    public record AssetDto(
        @Schema(description = "Asset symbol", example = "BTCUSDT")
        String symbol,
        @Schema(description = "Quantity of the asset held", example = "0.05")
        BigDecimal quantity) {}
}
