package com.cloudnative.k8s.userservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/resilience")
public class ResilienceController {

    @GetMapping("/cb")
    @CircuitBreaker(name = "paymentCircuitBreaker", fallbackMethod = "circuitBreakerFallback")
    public String circuitBreaker() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("Payment service down");
        }

        return "Circuit Breaker endpoint is working!";
    }

    public String circuitBreakerFallback(Throwable t) {
        return "Circuit Breaker fallback: Service is currently unavailable. Please try again later.";
    }

    @GetMapping("/retry")
    @Retry(name = "authServiceRetry", fallbackMethod = "retryFallback")
    public String getAuthenticationURL() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("Failed to get authentication URL");
        }

        return "https://auth.example.com/login";
    }

    public String retryFallback(Throwable t) {
        return "Retry fallback: Unable to retrieve authentication URL at this time.";
    }

    @GetMapping("/timeout")
    @TimeLimiter(name = "stockPriceServiceTimeLimiter", fallbackMethod = "timeoutFallback")
    public CompletableFuture<String> timeout() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (Math.random() > 0.5) {
                    Thread.sleep(5000); // Simulate a long processing time
                }
                return "Timeout endpoint response after delay";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<String> timeoutFallback(Throwable t) {
        return CompletableFuture.completedFuture("Timeout fallback: The request took too long to process. Please try again later.");
    }

    @GetMapping("/ratelimiter")
    @RateLimiter(name = "dataFetchRateLimiter", fallbackMethod = "rateLimiterFallback")
    public String rateLimiter() {
        return "Rate Limiter endpoint is working!";
    }

    public String rateLimiterFallback(Throwable t) {
        return "Rate Limiter fallback: Too many requests. Please slow down.";
    }

    @GetMapping("/bulkheads/semaphore")
    @Bulkhead(name = "imageProcessingBulkhead", fallbackMethod = "bulkheadFallback")
    public String bulkhead() {
        return "Bulkhead endpoint is working!";
    }

    public String bulkheadFallback(Throwable t) {
        return "Bulkhead fallback: Service is currently overloaded. Please try again later.";
    }

    @GetMapping("/bulkheads/threadpool")
    @Bulkhead(name = "reportGenerationBulkhead", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "threadPoolBulkheadFallback")
    public CompletableFuture<String> threadPoolBulkhead() {
        return CompletableFuture.supplyAsync(() -> {
            simulateHeavyWork();
            return "Thread Pool Bulkhead endpoint is working!";
        });
    }
    public CompletableFuture<String> threadPoolBulkheadFallback(Throwable t) {
        return CompletableFuture.completedFuture("Thread Pool Bulkhead fallback: Service is currently overloaded. Please try again later.");
    }

    private void simulateHeavyWork() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}
    }
}
