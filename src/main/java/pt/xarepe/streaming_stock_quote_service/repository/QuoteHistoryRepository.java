package pt.xarepe.streaming_stock_quote_service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pt.xarepe.streaming_stock_quote_service.domain.QuoteHistory;

@Repository
public interface QuoteHistoryRepository extends ReactiveMongoRepository<QuoteHistory, Long> {
}
