package de.shevchuk.stockclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;

//@Log4j2
public class WebClientStockClient {

    private WebClient webClient;

    public WebClientStockClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<StockPrice> pricesFor(String symbol) {
//        return Flux.fromArray(new de.shevchuk.stockclient.StockPrice[0]);
        return webClient.get()
                .uri("http://localhost:8080/stocks/{symbol}", symbol)
                .retrieve()
                .bodyToFlux(StockPrice.class)
                .retry(3)
                .doOnError(IOException.class, e -> System.out.println(e.getMessage()));
    }
}
