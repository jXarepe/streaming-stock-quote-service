package pt.xarepe.streaming_stock_quote_service.service;

import org.springframework.stereotype.Service;
import pt.xarepe.streaming_stock_quote_service.model.Quote;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple2;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

@Service
public class QuoteServiceImpl implements QuoteService {
    private final MathContext mathContext = new MathContext(2);
    private final List<Quote> quotes = new ArrayList<>();
    private final Random random = new Random();

    @PostConstruct
    public void fillQuotes(){
        quotes.add(new Quote("TAP", 22.22));
        quotes.add(new Quote("EDP", 59.33));
        quotes.add(new Quote("Microsoft", 155.65));
        quotes.add(new Quote("GALP", 102.57));
        quotes.add(new Quote("NETFL", 0.55));
        quotes.add(new Quote("AIRC", 420.69));
    }

    @Override
    public Flux<Quote> fetchQuoteStream(Duration period) {
        return Flux.generate(() ->0,
                (BiFunction<Integer, SynchronousSink<Quote>, Integer>) (index, sink) ->{
            Quote quoteUpdate = updateQuote(this.quotes.get(index));
            sink.next(quoteUpdate);
            return ++index % quotes.size();
                }).zipWith(Flux.interval(period))
                .map(Tuple2::getT1)
                .map(quote -> {
                    quote.setInstant(Instant.now());
                    return quote;
                }).log("pt.xarepe.streaming_stock_quote_service.service");
    }

    private Quote updateQuote(Quote quote){
        BigDecimal newPrice = quote.getPrice().multiply(BigDecimal.valueOf( 0.05 * random.nextDouble()).setScale(2, RoundingMode.HALF_EVEN));
        return new Quote(quote.getTicker(), quote.getPrice().add(newPrice));
    }
}
