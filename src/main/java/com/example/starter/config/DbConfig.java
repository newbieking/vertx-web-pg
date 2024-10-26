package com.example.starter.config;

import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;

public class DbConfig {

  public static Pool buildPool(Vertx vertx) {
    PgConnectOptions pgConnectOptions = new PgConnectOptions()
      .setHost(EnvConfig.get("DB_HOST"))
      .setPort(Integer.parseInt(EnvConfig.get("DB_PORT")))
      .setDatabase(EnvConfig.get("DB_DATABASE"))
      .setUser(EnvConfig.get("DB_USER"))
      .setPassword(EnvConfig.get("DB_PASSWORD"));

    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(5);

    return Pool.pool(vertx, pgConnectOptions, poolOptions);
  }
}
