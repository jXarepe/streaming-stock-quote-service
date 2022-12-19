package pt.xarepe.streaming_stock_quote_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import pt.xarepe.streaming_stock_quote_service.config.RabbitmqConfig;
import pt.xarepe.streaming_stock_quote_service.model.Quote;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
@RequiredArgsConstructor
public class QuoteMessageSender {
    private final ObjectMapper objectMapper;
    private final Sender sender;

    @SneakyThrows
    public Mono<Void> sendQouteMessage(Quote quote) {
        byte[] jsonBytes = objectMapper.writeValueAsBytes(quote);
        return sender.send(Mono.just(new OutboundMessage("", RabbitmqConfig.QUEUE, jsonBytes)));
    }
}
