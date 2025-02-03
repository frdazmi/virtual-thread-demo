package com.imza.porto.virtualthread.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class VirtualThreadConfig {

  @Value("${spring.task.execution.pool.traditional-pool-size}")
  private int traditionalPoolSize;

  @Bean
  public Executor virtualThreadExecutor() {
    return Executors.newVirtualThreadPerTaskExecutor();
  }

  @Bean
  public ExecutorService traditionalThreadExecutorService() {
    return Executors.newFixedThreadPool(traditionalPoolSize);
  }

  @Bean
  @Primary
  public ExecutorService virtualThreadExecutorService() {
    return (ExecutorService) virtualThreadExecutor();
  }
}
