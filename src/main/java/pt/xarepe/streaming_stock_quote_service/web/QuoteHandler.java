package pt.xarepe.streaming_stock_quote_service.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.xarepe.streaming_stock_quote_service.model.Quote;
import pt.xarepe.streaming_stock_quote_service.service.QuoteHistoryService;
import pt.xarepe.streaming_stock_quote_service.service.QuoteService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class QuoteHandler {

    private final QuoteService quoteService;
    private final QuoteHistoryService quoteHistoryService;

    public Mono<ServerResponse> fetchQuotes(ServerRequest request) {
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        return ok().contentType(MediaType.APPLICATION_JSON).body(quoteService.fetchQuoteStream(Duration.ofMillis(100L)).take(size), Quote.class);
    }

    public Mono<ServerResponse> streamQuotes(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_NDJSON).body(quoteService.fetchQuoteStream(Duration.ofMillis(100L)), Quote.class);
    }

    public Mono<ServerResponse> fetchQuotesHistory(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON).body(quoteHistoryService.getAll().map(
                quoteHistory -> Quote.builder()
                        .instant(quoteHistory.getInstant())
                        .price(quoteHistory.getPrice())
                        .ticker(quoteHistory.getTicker())
                        .build()), Quote.class);
    }
}
