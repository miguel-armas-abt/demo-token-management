package com.demo.poc.entrypoint.management.params.clean;

import java.util.Map;

import com.demo.poc.commons.core.validations.ParamMapper;

import org.springframework.stereotype.Component;

@Component
public class CleanTokenHeaderMapper implements ParamMapper {

  @Override
  public Object map(Map<String, String> params) {
    CleanTokenHeader headers = new CleanTokenHeader();
    headers.setTraceId(params.get("trace-id"));
    headers.setChannelId(params.get("channel-id"));
    headers.setPlatform(params.get("platform"));
    return headers;
  }

  @Override
  public boolean supports(Class<?> paramClass) {
    return CleanTokenHeader.class.isAssignableFrom(paramClass);
  }
}
