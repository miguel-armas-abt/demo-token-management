package com.demo.poc.commons.custom.properties.restclient;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeaderTemplate {

  private Map<String, String> provided;
  private Map<String, String> forwarded;

}
