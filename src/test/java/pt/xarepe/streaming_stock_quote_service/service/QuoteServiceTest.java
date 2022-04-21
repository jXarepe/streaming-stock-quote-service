package pt.xarepe.streaming_stock_quote_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.xarepe.streaming_stock_quote_service.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class QuoteServiceTest {

    private QuoteService service;

    @BeforeEach
    void setUp() {
        service = new QuoteServiceImpl();
        ((QuoteServiceImpl) service).fillQuotes();
    }

    @Test
    void fetchQuoteStream() throws InterruptedException {
        Flux<Quote> fluxQuote = service.fetchQuoteStream(Duration.ofMillis(100L));

        Consumer<Quote> quoteConsumer = System.out::println;
        Consumer<Throwable> throwableConsumer = e -> System.out.println(e.getMessage());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Runnable done = countDownLatch::countDown;

        fluxQuote.take(30L).subscribe(quoteConsumer, throwableConsumer, done);

        countDownLatch.await();
    }
}