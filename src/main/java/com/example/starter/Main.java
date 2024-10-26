package com.example.starter;

import com.example.starter.verticle.ApiVerticle;
import io.vertx.core.Vertx;

public class Main {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(ApiVerticle.class.getName())
      .onFailure(throwable -> {
        throwable.printStackTrace();
        System.exit(-1);
      });
  }
}
