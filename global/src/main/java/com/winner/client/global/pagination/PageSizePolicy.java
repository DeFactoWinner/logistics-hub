package com.winner.client.global.pagination;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum PageSizePolicy {
  SMALL(10), MEDIUM(30), LARGE(50);

  private final int size;
  private static final Set<Integer> ALLOWED = Arrays.stream(values())
      .map(p -> p.size)
      .collect(Collectors.toSet());

  PageSizePolicy(int size) { this.size = size; }

  public static int normalize(int requested) {
    return ALLOWED.contains(requested) ? requested : SMALL.size;
  }
}