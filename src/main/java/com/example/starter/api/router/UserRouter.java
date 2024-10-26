package com.example.starter.api.router;

import com.example.starter.api.handler.UserHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class UserRouter {


  private final Vertx vertx;
  private final UserHandler userHandler;

  public UserRouter(Vertx vertx, UserHandler userHandler) {
    this.vertx = vertx;
    this.userHandler = userHandler;
  }


  public void setSuperRoute(Route route) {
    route.subRouter(buildRouter());
  }


  private Router buildRouter() {
    Router router = Router.router(vertx);
    router.get("/users").handler(userHandler::allUsers);
    return router;
  }


}
