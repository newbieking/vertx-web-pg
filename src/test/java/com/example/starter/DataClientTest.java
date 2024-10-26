package com.example.starter;

import com.example.starter.api.repository.UserRepository;
import com.example.starter.config.DbConfig;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.SqlResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class DataClientTest {

  Pool pool;

  @BeforeEach
  public void setUp() {
    Pool pool = DbConfig.buildPool(Vertx.vertx());
  }

  @Test
  public void test() throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    pool.getConnection(ar -> {
      if (ar.succeeded()) {

        SqlConnection connection = ar.result();
        connection.query("select * from \"user\"")
          .collecting(Collectors.toMap(row -> row.getInteger("id"), row -> row.getString("name")))
          .execute()
          .onComplete(arMap -> {
            if (arMap.succeeded()) {
              SqlResult<Map<Integer, String>> result = arMap.result();
              Map<Integer, String> map = result.value();
              System.out.println(map.toString());
            } else {
              System.out.println("Error: " + arMap.cause());
            }
            countDownLatch.countDown();
          });
      }
    });


    countDownLatch.await();
  }


  @Test
  public void testFetcher() throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    UserRepository dataFetcher = new UserRepository();
    pool.withTransaction(connection -> dataFetcher.fetchUsers(connection)
      .onSuccess(userModels -> {
        System.out.println(userModels.toString());
      })
      .onComplete(ar -> countDownLatch.countDown()));

    System.out.println("plain text");
    countDownLatch.await();
  }

}
