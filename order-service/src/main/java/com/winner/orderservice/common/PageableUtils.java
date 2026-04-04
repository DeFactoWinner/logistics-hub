package com.winner.orderservice.common;

public final class PageableUtils {
  private PageableUtils() {}

  public static int normalizeSize(int requested) {
    return (requested == 10 || requested == 30 || requested == 50) ? requested : 10;
  }
}

