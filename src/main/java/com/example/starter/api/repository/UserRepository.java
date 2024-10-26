package com.example.starter.api.repository;


import com.example.starter.api.model.UserModel;
import io.vertx.core.Future;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.SqlResult;

import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {


  public UserRepository() {
  }

  public Future<List<UserModel>> fetchUsers(SqlConnection connection) {
    return connection.query("select * from \"user\"")
      .collecting(Collectors.mapping(row -> {
        UserModel userModel = new UserModel();
        userModel.setId(row.get(Integer.class, "id"));
        userModel.setName(row.get(String.class, "name"));
        return userModel;
      }, Collectors.toList()))
      .execute()
      .map(SqlResult::value);
  }

}
