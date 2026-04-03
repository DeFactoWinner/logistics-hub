package com.winner.client.global.variable;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.auth")
public class AuthProperties {

  private final List<String> whiteList;
  private final List<String> statusAllowList;
  private final List<AccessControl> accessControl;

  public AuthProperties(List<String> whitelist, List<String> statusAllowList,
      List<AccessControl> accessControl) {
    this.whiteList = (whitelist != null) ? whitelist : List.of();
    this.statusAllowList = statusAllowList != null ? statusAllowList : List.of();
    this.accessControl = accessControl != null ? accessControl : List.of();
  }

  public record AccessControl(String path, List<String> roles) {

  }
}