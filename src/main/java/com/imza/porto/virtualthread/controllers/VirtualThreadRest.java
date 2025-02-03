package com.imza.porto.virtualthread.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/virtual-thread")
public class VirtualThreadRest {

  @Autowired
  private ExecutorService virtualThreadExecutor;

  @GetMapping("/process")
  public String processTask() {
    try {
      virtualThreadExecutor.submit(() -> {
        try {
          Thread.sleep(1000); // Simulate IO Task
          System.out.println("Task completed by: " + Thread.currentThread());
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }).get(3, TimeUnit.SECONDS);
      return "Task submitted to Virtual Thread";
    } catch (Exception e) {
      return "Task submission failed";
    }
  }
}
