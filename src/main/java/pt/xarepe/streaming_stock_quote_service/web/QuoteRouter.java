package pt.xarepe.streaming_stock_quote_service.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QuoteRouter {

    public static final String QUOTES_PATH = "/quotes";
    public static final String QUOTES_HISTORY_PATH = "/quotes/history";

    @Bean
    public RouterFunction<ServerResponse> quoteRoutes(QuoteHandler handler){
        return route()
                .GET(QUOTES_PATH, accept(MediaType.APPLICATION_JSON), handler::fetchQuotes)
                .GET(QUOTES_PATH, accept(MediaType.APPLICATION_NDJSON), handler::streamQuotes)
                .GET(QUOTES_HISTORY_PATH, accept(MediaType.APPLICATION_JSON), handler::fetchQuotesHistory)
                .build();
    }
}
