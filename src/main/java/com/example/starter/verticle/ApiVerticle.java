package com.example.starter.verticle;

import com.example.starter.api.handler.UserHandler;
import com.example.starter.api.repository.UserRepository;
import com.example.starter.api.router.UserRouter;
import com.example.starter.api.service.UserService;
import com.example.starter.config.DbConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.sqlclient.Pool;

public class ApiVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    Router mainRouter = Router.router(vertx);

    Pool pool = DbConfig.buildPool(vertx);

    UserRouter userRouter = new UserRouter(vertx, new UserHandler(new UserService(pool, new UserRepository())));


    userRouter.setSuperRoute(mainRouter.route("/api/v1/*"));
    vertx.createHttpServer().requestHandler(mainRouter).listen(8888).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
