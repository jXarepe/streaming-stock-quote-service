package pt.xarepe.streaming_stock_quote_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuoteRunner implements CommandLineRunner {
    private final QuoteHistoryService quoteHistoryService;
    private final QuoteService quoteService;

    @Override
    public void run(String... args) throws Exception {
        quoteService.fetchQuoteStream(Duration.ofMillis(100L))
                .take(12)
                .log("Got quote:")
                .flatMap(quoteHistoryService::saveQuoteToMongo)
                .subscribe(savedQuote -> {
                    log.info("saved quote: {}", savedQuote);
                }, throwable -> {
                    log.error(throwable.getMessage());
                },() -> log.info("DONE!!!!"));
    }
}
