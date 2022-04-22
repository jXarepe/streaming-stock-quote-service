package pt.xarepe.streaming_stock_quote_service.service;

import pt.xarepe.streaming_stock_quote_service.domain.QuoteHistory;
import pt.xarepe.streaming_stock_quote_service.model.Quote;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface QuoteHistoryService {
    Mono<QuoteHistory> saveQuoteToMongo(Quote quote);
    Flux<QuoteHistory> getAll();
}
