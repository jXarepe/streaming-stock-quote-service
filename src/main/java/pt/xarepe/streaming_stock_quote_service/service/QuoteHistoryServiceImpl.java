package pt.xarepe.streaming_stock_quote_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pt.xarepe.streaming_stock_quote_service.domain.QuoteHistory;
import pt.xarepe.streaming_stock_quote_service.model.Quote;
import pt.xarepe.streaming_stock_quote_service.repository.QuoteHistoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuoteHistoryServiceImpl implements QuoteHistoryService {

    private final QuoteHistoryRepository repository;

    @Override
    public Mono<QuoteHistory> saveQuoteToMongo(Quote quote) {
        return repository.save(QuoteHistory.builder()
                .ticker(quote.getTicker())
                .price(quote.getPrice())
                .instant(quote.getInstant())
                .build());
    }

    @Override
    public Flux<QuoteHistory> getAll() {
        return repository.findAll();
    }
}
