package sanlab.itv.aquariuxtrd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sanlab.itv.aquariuxtrd.model.AggregatedPrice;
import sanlab.itv.aquariuxtrd.service.TickerService;

import java.util.List;

@Tag(name = "Tickers", description = "Market ticker and aggregated price data")
@RestController
@RequestMapping("/tickers")
@RequiredArgsConstructor
public class TickerController {

    private final TickerService tickerService;

    @Operation(
        summary = "Get latest aggregated prices",
        description = "Returns the most recent aggregated price for all available trading symbols."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved latest prices",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = AggregatedPrice.class))
        )
    )
    @GetMapping
    public ResponseEntity<List<AggregatedPrice>> getLatest() {
        var result = tickerService.latest();
        return ResponseEntity.ok(result);
    }

}
