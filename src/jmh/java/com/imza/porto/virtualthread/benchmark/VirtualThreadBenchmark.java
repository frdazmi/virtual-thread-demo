package com.imza.porto.virtualthread.benchmark;


import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class VirtualThreadBenchmark {

  private static final ExecutorService fixedThreadPool = Executors.newCachedThreadPool();
  private static final ExecutorService virtualThreadPool = Executors.newVirtualThreadPerTaskExecutor();
  private static final AtomicInteger counter = new AtomicInteger(0);

  @Benchmark
  public void benchmarkTraditionalThreadPool() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(10_000);
    IntStream.range(0, 10_000).forEach(i ->
        fixedThreadPool.submit(() -> {
          try {
            Thread.sleep(10); // Simulated task
            counter.incrementAndGet();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          } finally {
            latch.countDown();
          }
        })
    );
    latch.await();
  }

  @Benchmark
  public void benchmarkVirtualThreadPool() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(10_000);
    IntStream.range(0, 10_000).forEach(i ->
        virtualThreadPool.submit(() -> {
          try {
            Thread.sleep(10); // Simulated task
            counter.incrementAndGet();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          } finally {
            latch.countDown();
          }
        })
    );
    latch.await();
  }
}
