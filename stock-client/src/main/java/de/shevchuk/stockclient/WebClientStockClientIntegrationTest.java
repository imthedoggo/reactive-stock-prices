package de.shevchuk.stockclient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class WebClientStockClientIntegrationTest {
    private WebClient webClient = WebClient.builder().build();
    @Test
    void shouldRetrieveStockPricesFromService() {
        //given
        WebClientStockClient webClientStockClient = new WebClientStockClient(webClient);

        //when
        Flux<StockPrice> prices = webClientStockClient.pricesFor("DEMO");

        //then
        Assertions.assertNotNull(prices);
        Flux<StockPrice> fivePrices = prices.take(5);
        Assertions.assertEquals(5, fivePrices.count().block());
        Assertions.assertEquals("DEMO", fivePrices.blockFirst().getSymbol());
//        Assertions.assertTrue(prices.take(5).count().block()>0);
    }
}
