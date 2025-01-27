package com.casestudy.ecommerce.repository;

import com.casestudy.ecommerce.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,Integer> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
