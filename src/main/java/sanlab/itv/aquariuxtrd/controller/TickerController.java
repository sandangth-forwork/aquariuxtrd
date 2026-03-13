package sanlab.itv.aquariuxtrd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sanlab.itv.aquariuxtrd.model.AggregatedPrice;
import sanlab.itv.aquariuxtrd.service.TickerService;

import java.util.List;

@RestController
@RequestMapping("/tickers")
@RequiredArgsConstructor
public class TickerController {

    private final TickerService tickerService;

    @GetMapping
    public ResponseEntity<List<AggregatedPrice>> getLatest() {
        var result = tickerService.latest();
        return ResponseEntity.ok(result);
    }

}
