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
import sanlab.itv.aquariuxtrd.model.AuditLog;
import sanlab.itv.aquariuxtrd.service.TradeService;

import java.util.List;

@Tag(name = "Audit Logs", description = "Trade history and audit trail")
@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final TradeService tradeService;

    @Operation(
        summary = "Get all historical logs",
        description = "Returns the complete trade history and audit trail for the authenticated user."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Audit logs retrieved successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = AuditLog.class))
        )
    )
    @GetMapping
    public ResponseEntity<List<AuditLog>> getAll() {
        var result = tradeService.getHistory();
        return ResponseEntity.ok(result);
    }

}
