package com.example.starter.api.service;

import com.example.starter.api.model.UserModel;
import com.example.starter.api.repository.UserRepository;
import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;

import java.util.List;

public class UserService {


  private final Pool pool;
  private final UserRepository userRepository;

  public UserService(Pool pool, UserRepository userRepository) {
    this.pool = pool;
    this.userRepository = userRepository;
  }

  public Future<List<UserModel>> fetchUsers() {
    return pool.withTransaction(userRepository::fetchUsers);
  }

}
