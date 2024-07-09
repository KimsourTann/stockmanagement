package com.vannarith.bussiness.feature.token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

// import com.vannarith.bussiness.feature.user.User;
import org.springframework.transaction.annotation.Transactional;


public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(Integer id);

  @Modifying
  @Transactional
  void deleteByUserId(Integer userId);

  Optional<Token> findByToken(String token);
}