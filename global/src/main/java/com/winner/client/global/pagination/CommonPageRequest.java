package com.winner.client.global.pagination;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class CommonPageRequest {
  public static final int DEFAULT_PAGE = 0;
  public static final int DEFAULT_SIZE = PageSizePolicy.SMALL.getSize();
  public static final String DEFAULT_SORT = PageSortType.CREATED_AT_ASC.name();

  private final int page;
  private final int size;
  private final String sort;

  private CommonPageRequest(int page, int size, String sort) {
    this.page = Math.max(0, page);
    this.size = PageSizePolicy.normalize(size);  // 정책 위임
    this.sort = PageSortType.isValidSort(sort) ? sort : DEFAULT_SORT;
  }

  public static CommonPageRequest of(
      int page, int size, String sort) {
    return new CommonPageRequest(page, size, sort);
  }

  public Pageable toPageable() {
    return PageRequest.of(this.page, this.size, Sort.by(this.sort));
  }
}