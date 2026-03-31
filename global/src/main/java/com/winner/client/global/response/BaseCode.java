package com.winner.client.global.response;

import org.springframework.http.HttpStatus;

public interface BaseCode {
  HttpStatus getStatus();
  String getCode();
  String getMessage();
}
