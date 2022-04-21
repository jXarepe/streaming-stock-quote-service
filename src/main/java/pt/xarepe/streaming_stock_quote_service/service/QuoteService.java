package pt.xarepe.streaming_stock_quote_service.service;

import pt.xarepe.streaming_stock_quote_service.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface QuoteService {

    Flux<Quote> fetchQuoteStream(Duration period);
}
