package org.airtribe.AsynApiApplicationBELC17.service;

import java.time.Duration;
import java.util.List;
import org.airtribe.AsynApiApplicationBELC17.dto.Dimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.accept.ApiVersionResolver;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class HelloWorldService {
  @Autowired
  private RestTemplate _restTemplate;

  @Autowired
  private WebClient _webClient;

  public String invokeHelloEndpoint() {
    System.out.println("Thread invoking hello2 endpoint: " + Thread.currentThread().getName());
    return _restTemplate.getForObject("http://localhost:8080/hello", String.class);
  }

  public Dimension fetchProductsSync() {
    Dimension result = _restTemplate.getForObject("https://dummyjson.com/products", Dimension.class);
    return result;
  }

  public Mono<List<Dimension>> fetchProductsAsync() {
    System.out.println("Thread invoking async dimensions endpoint: " + Thread.currentThread().getName());

    Mono<Dimension> monDimension = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(Dimension.class)
        .doOnSuccess(apiResult -> {
          System.out.println("Received response for async dimensions request: " + apiResult);;
          System.out.println("Thread handling the async dimensions request: " + Thread.currentThread().getName());
        }).flatMap(apiResult -> {
          // Simulate some additional processing after receiving the API response
          return Mono.fromCallable(() -> {
            for (int i = 0; i < 1000000; i++) {
              // Simulate some processing
              double temp = Math.sqrt(i) * Math.pow(i, 2);
            }
            return apiResult;
          });
        });

    for (int i = 0; i < 1000000; i++) {
      // Simulate some processing
      double temp = Math.sqrt(i) * Math.pow(i, 2);
    }

    return Mono.empty();

  }

  public Mono<List<Dimension>> fetchProductsParallelAll() {
    System.out.println("Thread invoking async dimensions endpoint: " + Thread.currentThread().getName());

    Mono<Dimension> mono1 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(Dimension.class);
    Mono<Dimension> mono2 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(Dimension.class);
    Mono<Dimension> mono3 = _webClient.get().uri("https://dummyjson.com/productsgvhghghghghg").retrieve().bodyToMono(Dimension.class);

    return Mono.zip(mono1, mono2, mono3)
        .map(tuple -> List.of(tuple.getT1(), tuple.getT2(), tuple.getT3())).doOnSuccess(apiResult -> {
          System.out.println("Thread handling the async dimensions parallel request: " + Thread.currentThread().getName());
        }).doOnError(error -> {
          System.out.println("Error occurred in parallel dimensions request: " + error.getMessage());
        });
  }

  public Mono<Dimension> fetchProductsParallelAny() {
    System.out.println("Thread invoking async dimensions endpoint: " + Thread.currentThread().getName());

    Mono<Dimension> mono1 =
        _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(Dimension.class);
    Mono<Dimension> mono2 =
        _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(Dimension.class);
    Mono<Dimension> mono3 =
        _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(Dimension.class);

    return Mono.first(mono1, mono2, mono3).doOnSuccess(apiResult -> {
      System.out.println(
          "Thread handling the async dimensions parallel any request: " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred in parallel any dimensions request: " + error.getMessage());
    });
  }

  public Dimension getProductsWebClientSync() {
    // TOMCAT
    System.out.println("Thread invoking async dimensions endpoint: " + Thread.currentThread().getName());

    Dimension dimension = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(Dimension.class).block();

    for (int i = 0; i < 1000000; i++) {
      // Simulate some processing
      double temp = Math.sqrt(i) * Math.pow(i, 2);
    }
    System.out.println("Thread invoking async dimensions endpoint: " + Thread.currentThread().getName());

    return dimension;
  }

  public List<Dimension> fetchProductsChainedSync() {
    System.out.println("Thread invoking chained sync dimensions endpoint: " + Thread.currentThread().getName());

    Dimension result1 = _restTemplate.getForObject("https://dummyjson.com/products", Dimension.class);

    Dimension result2 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(Dimension.class).block();

    Dimension result3 = _restTemplate.getForObject("https://dummyjson.com/products", Dimension.class);

    return List.of(result1, result2, result3);
  }

  public Mono<List<Dimension>> fetchProductsAsyncChained() {
    System.out.println("Thread invoking async dimensions endpoint: " + Thread.currentThread().getName());

    Mono<List<Dimension>> result = _webClient.get().uri("https://dummyjson.com/products")
        .retrieve().bodyToMono(Dimension.class).doOnSuccess(
            apiResult -> System.out.println("Received response for async chained dimensions request: " + apiResult)
        ).flatMap(apiResult1 -> {
          return _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(Dimension.class).
              doOnSuccess(secondResult -> System.out.println("Received second response for async chained dimensions request: " + secondResult))
              .flatMap(secondResult -> {
                return _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(Dimension.class)
                    .doOnSuccess(thirdResult -> System.out.println("Received third response for async chained dimensions request: " + thirdResult))
                    .map(thirdResult -> List.of(apiResult1, secondResult, thirdResult));
              });
        });

    for (int i = 0; i < 1000000; i++) {
      // Simulate some processing
      double temp = Math.sqrt(i) * Math.pow(i, 2);
    }

    return result;
  }

  public Flux<Dimension> getProductsFluxStream() {
    System.out.println("Thread invoking the flux stream " + Thread.currentThread().getName());

    return Flux.interval(Duration.ofSeconds(3)).take(20).flatMap(i -> _webClient.get().uri("https://dummyjson.com/products")
        .retrieve().bodyToMono(Dimension.class).doOnSuccess(apiResult -> {
          System.out.println("Received response for flux stream dimensions request: " + apiResult);
          System.out.println("Thread handling the flux stream dimensions request: " + Thread.currentThread().getName());
        }).doOnError(error -> {
          System.out.println("Error occurred in flux stream dimensions request: " + error.getMessage());
        }));
  }
}
