package com.imza.porto.virtualthread.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DefaultRest {

  @GetMapping
  public String hello() {
    return "Hello, World!";
  }
}
