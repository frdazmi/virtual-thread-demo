package com.imza.porto.virtualthread.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/api/traditional-thread")
public class TraditionalThreadRest {

  @Autowired
  @Qualifier("traditionalThreadExecutorService")
  private ExecutorService traditionalThreadExecutor;

  @GetMapping("/process")
  public String processTask() {
    try {
      traditionalThreadExecutor.submit(() -> {
        try {
          Thread.sleep(1000); // Simulate IO Task
          System.out.println("Task completed by: " + Thread.currentThread());
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }).get();
      return "Task submitted to Traditional Thread";
    } catch (Exception e) {
      return "Task submission failed";
    }
  }
}
