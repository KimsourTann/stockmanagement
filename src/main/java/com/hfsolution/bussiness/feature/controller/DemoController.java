package com.hfsolution.bussiness.feature.controller;

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
import com.hfsolution.bussiness.app.dto.SearchRequestDTO;
import com.hfsolution.bussiness.app.services.SearchFilter;
import com.hfsolution.bussiness.feature.user.entity.User;
import com.hfsolution.bussiness.feature.user.repository.UserRepository;

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
  public List<User> filter(@RequestBody SearchRequestDTO request) {
    // Specification<User> users = new Specification<User>() {
    //   @Override
    //   public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb){
    //       return cb.equal(root.get(request.getColumn()), request.getValue());
    //   }
    // };
    Specification <User> users = searchFilter.getSearchSpecification(request.getSearchRequest(),request.getGlobalOperator());
    return repository.findAll(users);
    //   for (User user : repository.findAll()) {
    //     System.out.println(user.getEmail());
    //   }
    // return null;
  }

}