package sanlab.itv.aquariuxtrd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sanlab.itv.aquariuxtrd.dto.request.TradeRequestDto;
import sanlab.itv.aquariuxtrd.dto.response.WalletResponseDto;
import sanlab.itv.aquariuxtrd.service.TradeService;

@Tag(name = "Trades", description = "Trade execution and wallet management")
@RestController
@RequestMapping("/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @Operation(
        summary = "Execute a trade",
        description = "Places a buy or sell order for a given symbol and quantity."
    )
    @ApiResponse(responseCode = "202", description = "Trade accepted and queued for execution")
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request: unsupported cryptos, insufficient wallet or asset",
        content = @Content(schema = @Schema(hidden = true))
    )
    @ApiResponse(responseCode = "503", description = "Failed to initialize user's wallet")
    @PostMapping
    public ResponseEntity<Void> trade(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Trade order details",
            required = true,
            content = @Content(schema = @Schema(implementation = TradeRequestDto.class))
        )
        @RequestBody TradeRequestDto req) {
        tradeService.executeTrade(req);
        return ResponseEntity.accepted().build();
    }

    @Operation(
        summary = "Get wallet",
        description = "Returns the authenticated user's wallet including balance and held assets."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Wallet retrieved successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = WalletResponseDto.class)
        )
    )
    @ApiResponse(
        responseCode = "503",
        description = "Failed to initialize wallet for current user",
        content = @Content(schema = @Schema(hidden = true))
    )
    @GetMapping
    public ResponseEntity<WalletResponseDto> getWallet() {
        var result = tradeService.getWallet();
        return ResponseEntity.ok(result);
    }

}
