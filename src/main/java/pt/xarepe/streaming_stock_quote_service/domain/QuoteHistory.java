package pt.xarepe.streaming_stock_quote_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class QuoteHistory {
    @Id
    private String id;
    private String ticker;
    private BigDecimal price;
    private Instant instant;
}
