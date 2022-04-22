package pt.xarepe.streaming_stock_quote_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class StreamingStockQuoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingStockQuoteServiceApplication.class, args);
	}

}
