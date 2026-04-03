package com.winner.client.global.pagination;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageSortType {
  CREATED_AT_ASC("createdAt", "생성일 오름차순"),
  CREATED_AT_DESC("createdAt", "생성일 내림차순"),
  UPDATED_AT_ASC("updatedAt", "수정일 오름차순"),
  UPDATED_AT_DESC("updatedAt", "수정일 내림차순");

  private final String property;
  private final String description;

  public static boolean isValidSort(String sort) {
    return Arrays.stream(PageSortType.values())
        .anyMatch(e -> e.name().equals(sort));
  }
}
