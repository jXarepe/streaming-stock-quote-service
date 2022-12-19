package pt.xarepe.streaming_stock_quote_service.service;

import com.rabbitmq.client.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuoteRunner implements CommandLineRunner {
    private final QuoteHistoryService quoteHistoryService;
    private final QuoteService quoteService;
    private final QuoteMessageSender quoteMessageSender;
    private final Flux<Delivery> deliveryFlux;

    @Override
    public void run(String... args) throws Exception {
        /*quoteService.fetchQuoteStream(Duration.ofMillis(100L))
                .take(12)
                .log("Got quote:")
                .flatMap(quoteHistoryService::saveQuoteToMongo)
                .subscribe(savedQuote -> {
                    log.info("saved quote: {}", savedQuote);
                }, throwable -> {
                    log.error(throwable.getMessage());
                },() -> log.info("DONE!!!!"));*/

        quoteService.fetchQuoteStream(Duration.ofMillis(100L))
                .take(12)
                .log("Got quote:")
                .flatMap(quoteMessageSender::sendQouteMessage)
                .subscribe(savedQuote -> {
                    log.info("send quote to rabbit: {}", savedQuote);
                }, throwable -> {
                    log.error(throwable.getMessage());
                },() -> log.info("DONE!!!!"));

        AtomicInteger receiverCount = new AtomicInteger();

        deliveryFlux.subscribe(msg -> {
            log.info("Received Message # {} - {}", receiverCount.incrementAndGet(), new String(msg.getBody()));
        });
    }
}
