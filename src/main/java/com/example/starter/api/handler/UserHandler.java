package com.example.starter.api.handler;

import com.example.starter.api.service.UserService;
import io.vertx.ext.web.RoutingContext;

public class UserHandler {

  UserService userService;

  public UserHandler(UserService userService) {
    this.userService = userService;
  }

  public void allUsers(RoutingContext ctx) {
    userService.fetchUsers()
      .onSuccess(ctx::json)
      .onFailure(ctx::fail);
  }


}
