package com.vannarith.bussiness.feature.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vannarith.bussiness.feature.service.SearchFilter;
import com.vannarith.bussiness.feature.user.SearchRequest;
import com.vannarith.bussiness.feature.user.User;
import com.vannarith.bussiness.feature.user.UserRepository;

@RestController
@RequestMapping("/api/v1/demo-controller")
@Hidden
@RequiredArgsConstructor
public class DemoController {

  final SearchFilter<User> searchFilter;

  final UserRepository repository;

  @GetMapping
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello from secured endpoint");
  }



  @PostMapping("/filter")
  public List<User> filter(@RequestBody SearchRequest request) {
    Specification <User> user = searchFilter.getSearchSpecification(request);
    return repository.findAll(user);
  }

}