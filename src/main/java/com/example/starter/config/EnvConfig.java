package com.example.starter.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {

  private static final Dotenv load;

  static {
    load = Dotenv.load();
  }

  public static String get(String key) {
    return load.get(key);
  }

}
