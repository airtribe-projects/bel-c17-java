package org.airtribe.AsynApiApplicationBELC17.controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.airtribe.AsynApiApplicationBELC17.dto.Dimension;
import org.airtribe.AsynApiApplicationBELC17.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class HelloworldController {

  @Autowired
  private HelloWorldService _helloWorldService;

  private List<Byte[]> byteLeak = new ArrayList<>();

  @GetMapping("/hello")
  public String helloWorld() {
    System.out.println("Thread handling /hello request: " + Thread.currentThread().getName());
    return "Hello, World!";
  }

  // Make API invocation from within API invocation
  @GetMapping("/hello2")
  public String helloWorld2() {
    System.out.println("Thread handling /hello2 request: " + Thread.currentThread().getName());
    return _helloWorldService.invokeHelloEndpoint();
  }

  @GetMapping("/allocateMemoryLeak")
  public String allocateMemoryLeak() {
    Byte[] chunk = new Byte[1024 * 1024 * 10]; // 10 MB
    byteLeak.add(chunk);
    System.out.println("Allocated 10MB");

    return "Memory leak allocation completed";
  }


  @GetMapping("/dimensions")
  public Dimension fetchProducts() {
    System.out.println("Thread handling /dimensions request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsSync();
  }

  @GetMapping("/dimensions/async")
  public Mono<Dimension> fetchProductsAsync() {
    System.out.println("Thread handling /dimensions/async request: " + Thread.currentThread().getName());
    //return _helloWorldService.fetchProductsAsync();
    return Mono.empty();
  }

  @GetMapping("/dimensions/sync/webclient")
  public Dimension fetchProductsSyncWebClient() {
    System.out.println("Thread handling /dimensions/sync/webclient request: " + Thread.currentThread().getName());
    return _helloWorldService.getProductsWebClientSync();
  }

  @GetMapping("/dimensions/parallelAll")
  public Mono<List<Dimension>> fetchProductsParallelAll() {
    System.out.println("Thread handling /dimensions/parallelAll request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsParallelAll();
  }

  @GetMapping("/dimensions/parallelAny")
  public Mono<Dimension> fetchProductsParallelAny() {
    System.out.println("Thread handling /dimensions/parallelAny request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsParallelAny();
  }

  @GetMapping("/dimensions/chainedSync")
  public List<Dimension> fetchProductsChainedSync() {
    System.out.println("Thread handling /dimensions/chainedSync request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsChainedSync();
  }

  @GetMapping("/dimensions/chainedAsync")
  public Mono<List<Dimension>> fetchProductsChainedAsync() {
    System.out.println("Thread handling /dimensions/chainedSync request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsAsyncChained();
  }

  @GetMapping(value = "/products/fluxStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Dimension> getProductsFluxStream() {
    System.out.println("Thread handling /products/fluxStream request: " + Thread.currentThread().getName());
    return _helloWorldService.getProductsFluxStream();
  }
}
